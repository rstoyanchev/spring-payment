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

package org.springframework.payment.cybs.gateway;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.payment.core.PaymentTemplate;
import org.springframework.payment.core.PaymentTransaction;
import org.springframework.payment.core.PaymentTransaction.Decision;
import org.springframework.payment.creditcard.CreditCardAuthCaptureRequest;
import org.springframework.payment.creditcard.CreditCardAuthRequest;
import org.springframework.payment.creditcard.CreditCardFollowOnCaptureRequest;
import org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean;
import org.springframework.payment.cybs.mapping.creditcard.CreditCardMapperFactory;
import org.springframework.payment.gateway.GatewayClient;
import org.springframework.payment.test.beans.CreditCardTestBeans;

public class PaymentTemplateIntegrationTests {

	private PaymentTemplate paymentTemplate;

	@Before
	public void setUp() throws Exception {
		ClassPathResource resource = new ClassPathResource("cybersource.properties");

		CybsGatewayClientFactoryBean factory = new CybsGatewayClientFactoryBean();
		factory.setCyberSourceProperties(PropertiesLoaderUtils.loadProperties(resource));
		factory.afterPropertiesSet();
		GatewayClient gateway = factory.getObject();

		paymentTemplate = new PaymentTemplate(gateway);
		paymentTemplate.setRequestMappers(CreditCardMapperFactory.defaultRequestMappers());
		paymentTemplate.setResponseMappers(CreditCardMapperFactory.defaultResponseMappers());
	}

	@Test
	public void testCreditCardAuth() {
		CreditCardAuthRequest ccAuthRequest = CreditCardTestBeans.ccAuthRequest();
		ccAuthRequest.setRequestId("intTest-PT-creditCardAuth");
		PaymentTransaction transaction = paymentTemplate.execute(ccAuthRequest);
		assertEquals(Decision.valueOf("ACCEPT"), transaction.getDecision());
	}

	@Test
	public void testCreditCardAuthCaptureRequest() {
		CreditCardAuthRequest ccAuthRequest = CreditCardTestBeans.ccAuthRequest();
		ccAuthRequest.setRequestId("intTest-PT-creditCardAuthCaptureRequest");
		CreditCardAuthCaptureRequest request = new CreditCardAuthCaptureRequest(ccAuthRequest);
		PaymentTransaction ccCaptureTx = paymentTemplate.execute(request);
		assertEquals(Decision.valueOf("ACCEPT"), ccCaptureTx.getDecision());
	}

	@Test
	public void testCreditCardFollowOnCapture() {
		CreditCardAuthRequest ccAuthRequest = CreditCardTestBeans.ccAuthRequest();
		ccAuthRequest.setRequestId("intTest-PT-creditCardFollowOnCapture");
		PaymentTransaction ccAuthTx = paymentTemplate.execute(ccAuthRequest);
		assertEquals(Decision.valueOf("ACCEPT"), ccAuthTx.getDecision());
		CreditCardFollowOnCaptureRequest captureRequest = new CreditCardFollowOnCaptureRequest(ccAuthTx);
		captureRequest.setTotal(ccAuthRequest.getTotal());
		PaymentTransaction ccCaptureTx = paymentTemplate.execute(captureRequest);
		assertEquals(Decision.valueOf("ACCEPT"), ccCaptureTx.getDecision());
	}

}
