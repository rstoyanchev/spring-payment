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

import static org.junit.Assert.assertEquals;

import java.util.Currency;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.payment.common.money.MonetaryAmount;
import org.springframework.payment.core.PaymentTransaction;
import org.springframework.payment.creditcard.CreditCardAuthCaptureRequest;
import org.springframework.payment.creditcard.CreditCardAuthRequest;
import org.springframework.payment.creditcard.CreditCardCaptureRequest;
import org.springframework.payment.creditcard.CreditCardFollowOnCaptureRequest;
import org.springframework.payment.gateway.GatewayRequest;
import org.springframework.payment.test.beans.CreditCardTestBeans;

public class CreditCardRequestMappingTests {

	Logger logger = LoggerFactory.getLogger(CreditCardRequestMappingTests.class);

	@Test
	public void testCreditCardAuthRequest() throws Exception {
		CreditCardAuthRequest source = CreditCardTestBeans.ccAuthRequest();
		GatewayRequest target = new GatewayRequest();
		new CreditCardAuthRequestMapper().map(source, target);
		logger.trace("ccAuthRequest: " + target.getRequestFields().toString());
		assertCreditCardAuthRequest(source, target);
	}

	@Test
	public void testCreditCardAuthCaptureRequest() throws Exception {
		CreditCardAuthCaptureRequest source = new CreditCardAuthCaptureRequest(CreditCardTestBeans.ccAuthRequest());
		GatewayRequest target = new GatewayRequest();
		new CreditCardAuthCaptureRequestMapper().map(source, target);
		logger.trace("ccAuthCaptureRequest: " + target.getRequestFields().toString());
		assertCreditCardAuthRequest(source.getCreditCardAuthRequest(), target);
		assertCreditCardCaptureRequest(source, target);
	}

	@Test
	public void testCreditCardFollowOnCaptureRequest() throws Exception {
		PaymentTransaction paymentTx = new PaymentTransaction();
		paymentTx.setPaymentServiceRequest(CreditCardTestBeans.ccAuthRequest());
		paymentTx.setTransactionId("ccAuth-Tx-1");
		CreditCardFollowOnCaptureRequest source = new CreditCardFollowOnCaptureRequest(paymentTx);
		source.setTotal(new MonetaryAmount(10, Currency.getInstance("USD")));
		GatewayRequest target = new GatewayRequest();
		new CreditCardFollowOnCaptureRequestMapper().map(source, target);
		logger.trace("Follow-on ccCaptureRequest: " + target.getRequestFields().toString());
		Map<String, String> requestFields = target.getRequestFields();
		assertCreditCardCaptureRequest(source, target);
		assertEquals(requestFields.get("ccCaptureService_authRequestID"), "ccAuth-Tx-1");
	}

	private void assertCreditCardAuthRequest(CreditCardAuthRequest source, GatewayRequest target) {
		Map<String, String> requestFields = target.getRequestFields();
		assertEquals(requestFields.get("ccAuthService_run"), "true");
		MonetaryAmount total = source.getTotal();
		assertEquals(requestFields.get("purchaseTotals_grandTotalAmount"), String.valueOf(total.getBigDecimal()));
		assertEquals(requestFields.get("purchaseTotals_currency"), total.getCurrency().getCurrencyCode());
		assertEquals(requestFields.get("purchaseTotals_currency"), total.getCurrency().getCurrencyCode());
		assertEquals(requestFields.get("billTo_firstName"), source.getOrder().getAccount().getFirstName());
	}

	private void assertCreditCardCaptureRequest(CreditCardCaptureRequest source, GatewayRequest target) {
		Map<String, String> requestFields = target.getRequestFields();
		assertEquals(requestFields.get("ccCaptureService_run"), "true");
		assertEquals(requestFields.get("merchantReferenceCode"), "ccAuth-1");
	}

}
