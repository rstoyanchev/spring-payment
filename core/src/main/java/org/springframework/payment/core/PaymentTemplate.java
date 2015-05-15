/*
 * Copyright 2004-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.payment.core;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.mapping.Mapper;
import org.springframework.mapping.support.DefaultMappingTargetFactory;
import org.springframework.mapping.support.MappingConverter;
import org.springframework.payment.gateway.GatewayRequest;
import org.springframework.payment.gateway.GatewayResponse;
import org.springframework.payment.gateway.GatewayClient;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * The central abstraction for submitting a payment service request. Requires an instance
 * of {@link GatewayClient} to delegate requests to. Invokes type conversion, validation
 * and exception translation to incoming requests for any gateway.
 * 
 * @author Rossen Stoyanchev
 */
public class PaymentTemplate implements PaymentOperations {

	private final Logger logger = LoggerFactory.getLogger(PaymentTemplate.class);

	private final GatewayClient gatewayClient;

	private Validator validator;

	private final FormattingConversionService conversionService;

	private final PaymentTransactionExceptionTranslator exceptionTranslator;

	public PaymentTemplate(GatewayClient gatewayClient) {
		this.gatewayClient = gatewayClient;

		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		validatorFactoryBean.afterPropertiesSet();
		this.validator = validatorFactoryBean;

		FormattingConversionServiceFactoryBean conversionServiceFactoryBean = new FormattingConversionServiceFactoryBean();
		conversionServiceFactoryBean.afterPropertiesSet();
		conversionService = conversionServiceFactoryBean.getObject();

		exceptionTranslator = new PaymentTransactionExceptionTranslator(gatewayClient.getDecisionCodes());
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public <S> void setRequestMappers(Mapper<S, GatewayRequest>[] mappers) {
		for (int i = 0; i < mappers.length; i++) {
			conversionService.addConverter(new MappingConverter(mappers[i], DefaultMappingTargetFactory.getInstance()));
		}
	}

	public <T> void setResponseMappers(Mapper<GatewayResponse, T>[] mappers) {
		for (int i = 0; i < mappers.length; i++) {
			conversionService.addConverter(new MappingConverter(mappers[i], DefaultMappingTargetFactory.getInstance()));
		}
	}

	/**
	 * @see PaymentOperations#execute(PaymentServiceRequest)
	 */
	public PaymentTransaction execute(PaymentServiceRequest paymentRequest) {
		Set<ConstraintViolation<PaymentServiceRequest>> violations = validator.validate(paymentRequest);
		if (!violations.isEmpty()) {
			throw new PaymentValidationException(paymentRequest, violations);
		}

		GatewayRequest gatewayRequest = conversionService.convert(paymentRequest, GatewayRequest.class);
		logger.debug("Gateway request fields: {}", gatewayRequest.getRequestFields());

		GatewayResponse gatewayResponse;
		try {
			gatewayResponse = gatewayClient.execute(gatewayRequest);
		} catch (Exception e) {
			throw new GatewayClientException("Gateway client error", e);
		}
		logger.debug("Gateway response fields: {}", gatewayResponse.getResponseFields());

		PaymentTransaction paymentTransaction = conversionService.convert(gatewayResponse, PaymentTransaction.class);
		paymentTransaction.setPaymentServiceRequest(paymentRequest);
		paymentTransaction.setDecisionCodeDescription(gatewayClient.getDecisionCodes());

		PaymentTransactionException exception = exceptionTranslator.translate(paymentTransaction);
		if (exception != null) {
			throw exception;
		}

		return paymentTransaction;
	}

}
