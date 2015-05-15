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

package org.springframework.payment.cybs.mapping.creditcard;

import org.springframework.mapping.Mapper;
import org.springframework.payment.creditcard.CreditCardCaptureRequest;
import org.springframework.payment.cybs.mapping.PaymentServiceRequestMapper;
import org.springframework.payment.gateway.GatewayRequest;

/**
 * A base class for all CyberSource {@link Mapper} types that map from a 
 * {@link CreditCardCaptureRequest} to a {@link GatewayRequest}.
 * 
 * @author Rossen Stoyanchev
 */
public abstract class CreditCardCaptureRequestMapper<S extends CreditCardCaptureRequest> extends
		PaymentServiceRequestMapper<S> {

	/**
	 * @param sourceType the source type to map from
	 * @param targetFieldPrefix a common prefix for use with the {@link #prefix(String)} convenience method
	 */
	public CreditCardCaptureRequestMapper(Class<S> sourceType, String targetFieldPrefix) {
		super(sourceType, targetFieldPrefix);
		addConditionalMapping("total.bigDecimal", "purchaseTotals_grandTotalAmount", "total != null");
		addConditionalMapping("total.currency.currencyCode", "purchaseTotals_currency", "total != null");
	}

	@Override
	public GatewayRequest map(S source, GatewayRequest target) {
		target = super.map(source, target);
		target.getRequestFields().put(prefix("run"), "true");
		return target;
	}

}
