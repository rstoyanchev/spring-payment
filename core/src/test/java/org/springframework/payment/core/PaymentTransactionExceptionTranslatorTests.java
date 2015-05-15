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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.payment.gateway.GatewayDecisionCodes;

public class PaymentTransactionExceptionTranslatorTests {

	private static String defaultCode = "100";
	
	private static final String[] defaultCodeArray = new String[] { defaultCode };

	private GatewayDecisionCodes codes;
	
	@Before
	public void setUp() {
		codes = new GatewayDecisionCodes();
	}

	@Test
	public void testSuccess() throws Exception {
		codes.setSuccessCodes(defaultCodeArray);
		PaymentTransactionExceptionTranslator translator = new PaymentTransactionExceptionTranslator(codes);
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setDecisionCode(defaultCode);
		PaymentTransactionException actual = translator.translate(transaction);
		assertNull(actual);
	}

	@Test
	public void testUncategorizedTransactionException() throws Exception {
		translateAndAssert(codes, defaultCode, UncategorizedTransactionException.class);
	}

	@Test
	public void testGatewayConfigurationException() throws Exception {
		codes.setGatewayConfigurationCodes(defaultCodeArray);
		translateAndAssert(codes, defaultCode, GatewayConfigurationException.class);
	}

	@Test
	public void testInvalidCardException() throws Exception {
		codes.setInvalidCardCodes(defaultCodeArray);
		translateAndAssert(codes, defaultCode, InvalidCardException.class);
	}

	@Test
	public void testInvalidPriorTransactionException() throws Exception {
		codes.setInvalidPriorTransactionCodes(defaultCodeArray);
		translateAndAssert(codes, defaultCode, InvalidPriorTransactionException.class);
	}

	@Test
	public void testLostOrStolenCardException() throws Exception {
		codes.setLostOrStolenCardCodes(defaultCodeArray);
		translateAndAssert(codes, defaultCode, LostOrStolenCardException.class);
	}

	@Test
	public void testMissingOrInvalidDataException() throws Exception {
		codes.setMissingOrInvalidDataCodes(defaultCodeArray);
		translateAndAssert(codes, defaultCode, MissingOrInvalidDataException.class);
	}

	@Test
	public void testPaymentProcessingException() throws Exception {
		codes.setPaymentProcessingCodes(defaultCodeArray);
		translateAndAssert(codes, defaultCode, PaymentProcessingException.class);
	}

	@Test
	public void testRequestNotProcessedException() throws Exception {
		codes.setRequestNotProcessedCodes(defaultCodeArray);
		translateAndAssert(codes, defaultCode, RequestNotProcessedException.class);
	}

	@Test
	public void testTransactionAmountException() throws Exception {
		codes.setTransactionAmountCodes(defaultCodeArray);
		translateAndAssert(codes, defaultCode, TransactionAmountException.class);
	}

	@Test
	public void testUnknownTransactionOutcomeException() throws Exception {
		codes.setUnknownTransactionOutcomeCodes(defaultCodeArray);
		translateAndAssert(codes, defaultCode, UnknownTransactionOutcomeException.class);
	}

	@Test
	public void testVerbalAuthorizationRequiredException() throws Exception {
		codes.setVerbalAuthorizationRequiredCodes(defaultCodeArray);
		translateAndAssert(codes, defaultCode, VerbalAuthorizationRequiredException.class);
	}

	@Test
	public void testVerificationServiceException() throws Exception {
		codes.setVerificationServiceCodes(defaultCodeArray);
		translateAndAssert(codes, defaultCode, VerificationServiceException.class);
	}
	
	public void translateAndAssert(GatewayDecisionCodes codes, String decisionCode, Class<?> expected) throws Exception {
		PaymentTransactionExceptionTranslator translator = new PaymentTransactionExceptionTranslator(codes);
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setDecisionCode(decisionCode);
		PaymentTransactionException actual = translator.translate(transaction);
		assertThat(actual, instanceOf(expected));
	}

}
