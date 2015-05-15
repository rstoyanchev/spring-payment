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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.validation.ConstraintViolation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mapping.Mapper;
import org.springframework.payment.common.account.Account;
import org.springframework.payment.common.account.PaymentMethod;
import org.springframework.payment.common.order.Order;
import org.springframework.payment.core.PaymentTransaction.Decision;
import org.springframework.payment.creditcard.CreditCardAuthRequest;
import org.springframework.payment.gateway.GatewayDecisionCodes;
import org.springframework.payment.gateway.GatewayRequest;
import org.springframework.payment.gateway.GatewayResponse;
import org.springframework.payment.test.beans.CommonTestBeans;
import org.springframework.payment.test.beans.CreditCardTestBeans;
import org.springframework.payment.test.gateway.StubGatewayClient;
import org.springframework.payment.test.gateway.StubGatewayResponse;

public class PaymentTemplateTests {

	private PaymentTemplate paymentTemplate;

	private StubGatewayClient stubGateway;

	private Map<String, String> responseFields;

	private PaymentTransactionMapper paymentTransactionMapper;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		responseFields = new HashMap<String, String>();
		stubGateway = new StubGatewayClient(initializeDecisionCodes());
		stubGateway.addResponse(new StubGatewayResponse(responseFields));
		paymentTransactionMapper = new PaymentTransactionMapper();
		paymentTemplate = new PaymentTemplate(stubGateway);
		paymentTemplate.setRequestMappers(new Mapper[]{new CreditCardAuthRequestMapper()});
		paymentTemplate.setResponseMappers(new Mapper[] {paymentTransactionMapper});
	}

	private GatewayDecisionCodes initializeDecisionCodes() {
		Properties props = new Properties();
		props.put("100", "Success");
		GatewayDecisionCodes codes = new GatewayDecisionCodes();
		codes.setCodeDescriptions(props);
		codes.setSuccessCodes(new String[] { "100" });
		return codes;
	}

	@Test
	public void testPaymentTransactionFieldsAreSet() throws Exception {
		CreditCardAuthRequest authRequest = CreditCardTestBeans.ccAuthRequest();
		paymentTransactionMapper.setDecision(Decision.ACCEPT);
		paymentTransactionMapper.setDecisionCode("100");
		PaymentTransaction transaction = paymentTemplate.execute(authRequest);
		assertThat(transaction.getDecision(), equalTo(Decision.ACCEPT));
		assertThat(transaction.getDecisionCodeDescription(), equalTo("Success"));
		assertThat(transaction.getPaymentServiceRequest(), notNullValue());
	}

	@Test
	public void testExceptionTranslation() {
		CreditCardAuthRequest authRequest = CreditCardTestBeans.ccAuthRequest();
		paymentTransactionMapper.setDecision(Decision.REJECT);
		paymentTransactionMapper.setDecisionCode("200");
		try {
			paymentTemplate.execute(authRequest);
			fail("Expected a non-success code to result in an exception");
		} catch (UncategorizedTransactionException e) {
			// expected
		}
	}

	@Test
	public void testValidateAccount() {
		Account account = new Account(null, "Doe", "jdoe@mail.com");
		Order order = new Order(account, new PaymentMethod(CommonTestBeans.paymentCard(), CommonTestBeans.address()));
		CreditCardAuthRequest authRequest = new CreditCardAuthRequest(order);
		paymentTransactionMapper.setDecision(Decision.ACCEPT);
		paymentTransactionMapper.setDecisionCode("100");
		try {
			paymentTemplate.execute(authRequest);
		} catch (PaymentValidationException e) {
			ConstraintViolation<PaymentServiceRequest> violation = e.getViolations().iterator().next();
			assertEquals("order.account.firstName", violation.getPropertyPath().toString());
			assertEquals("{javax.validation.constraints.NotNull.message}", violation.getMessageTemplate());
		}
	}

	private class CreditCardAuthRequestMapper implements Mapper<CreditCardAuthRequest, GatewayRequest> {

		public GatewayRequest map(CreditCardAuthRequest ccAuthRequest, GatewayRequest gatewayRequest) {
			return gatewayRequest;
		}
	
	}

	private class PaymentTransactionMapper implements Mapper<GatewayResponse, PaymentTransaction> {
		
		private Decision decision;
		private String decisionCode;
		
		public void setDecision(Decision decision) {
			this.decision = decision;
		}

		public void setDecisionCode(String decisionCode) {
			this.decisionCode = decisionCode;
		}

		public PaymentTransaction map(GatewayResponse gatewayResponse, PaymentTransaction paymentTransaction) {
			paymentTransaction.setDecision(decision);
			paymentTransaction.setDecisionCode(decisionCode);
			return paymentTransaction;
		}
	
	}
}
