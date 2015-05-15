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

import java.text.MessageFormat;
import java.util.Arrays;

import org.springframework.payment.gateway.GatewayDecisionCodes;

/**
 * Examines the decision codes in a {@link PaymentTransaction} and translates that into a 
 * sub-type of {@link PaymentTransactionException}. To make these decisions, this class
 * must be configured with an instance of {@link GatewayDecisionCodes}, which contains the
 * codes for a specific gateway organized by category.  
 * 
 * @author Rossen Stoyanchev
 */
public class PaymentTransactionExceptionTranslator {

	private GatewayDecisionCodes gatewayCodes;

	public PaymentTransactionExceptionTranslator(GatewayDecisionCodes codes) {
		this.gatewayCodes = codes;
	}

	public PaymentTransactionException translate(PaymentTransaction transaction) {
		String code = transaction.getDecisionCode();
		if (Arrays.binarySearch(gatewayCodes.getSuccessCodes(), code) >= 0) {
			return null;
		}

		String defaultMessage = defaultMessage(transaction);
		if (Arrays.binarySearch(gatewayCodes.getGatewayConfigurationCodes(), code) >= 0) {
			return new GatewayConfigurationException(defaultMessage, transaction);

		} else if (Arrays.binarySearch(gatewayCodes.getInvalidCardCodes(), code) >= 0) {
			return new InvalidCardException(defaultMessage, transaction);

		} else if (Arrays.binarySearch(gatewayCodes.getInvalidPriorTransactionCodes(), code) >= 0) {
			return new InvalidPriorTransactionException(defaultMessage, transaction);

		} else if (Arrays.binarySearch(gatewayCodes.getLostOrStolenCardCodes(), code) >= 0) {
			return new LostOrStolenCardException(defaultMessage, transaction);

		} else if (Arrays.binarySearch(gatewayCodes.getMissingOrInvalidDataCodes(), code) >= 0) {
			return new MissingOrInvalidDataException(defaultMessage, transaction);

		} else if (Arrays.binarySearch(gatewayCodes.getPaymentProcessingCodes(), code) >= 0) {
			return new PaymentProcessingException(defaultMessage, transaction);

		} else if (Arrays.binarySearch(gatewayCodes.getRequestNotProcessedCodes(), code) >= 0) {
			return new RequestNotProcessedException(defaultMessage, transaction);

		} else if (Arrays.binarySearch(gatewayCodes.getTransactionAmountCodes(), code) >= 0) {
			return new TransactionAmountException(defaultMessage, transaction);

		} else if (Arrays.binarySearch(gatewayCodes.getUnknownTransactionOutcomeCodes(), code) >= 0) {
			return new UnknownTransactionOutcomeException(defaultMessage, transaction);

		} else if (Arrays.binarySearch(gatewayCodes.getVerbalAuthorizationRequiredCodes(), code) >= 0) {
			return new VerbalAuthorizationRequiredException(defaultMessage, transaction);

		} else if (Arrays.binarySearch(gatewayCodes.getVerificationServiceCodes(), code) >= 0) {
			return new VerificationServiceException(defaultMessage, transaction);
		}

		return new UncategorizedTransactionException(defaultMessage, transaction);
	}

	private String defaultMessage(PaymentTransaction transaction) {
		String pattern = "The decision for transaction [id={0}] with request [id={1}] "
				+ "was {2}, decision code={3}, decision code description={4}";
		PaymentServiceRequest request = transaction.getPaymentServiceRequest();
		String requestId = (request != null) ? request.getRequestId() : "null";
		return MessageFormat.format(pattern,
				new Object[] { transaction.getTransactionId(), requestId, transaction.getDecision(),
						transaction.getDecisionCode(), transaction.getDecisionCodeDescription() });
	}

}
