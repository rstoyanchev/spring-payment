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

import org.springframework.payment.gateway.GatewayDecisionCodes;

/**
 * <p>A base class for all payment transactions. A payment transaction represents the response
 * received from a payment gateway as a result of sending a {@link PaymentServiceRequest}.
 * The {@link #decision} and {@link #decisionCode} fields provide information about the 
 * decision made about the request. The {@link PaymentTemplate} will interpret the decision 
 * and translate it into a {@link PaymentTransactionException} if necessary.</p>
 * 
 * <p>A payment transaction can be used as input to subsequent payment service requests such 
 * capture an amount following prior authorization, voiding a credit, etc.</p>
 * 
 * @author Rossen Stoyanchev
 */
public class PaymentTransaction {

	private String transactionId;

	private Decision decision;

	private String decisionCode;
	
	private String decisionCodeDescription = GatewayDecisionCodes.NO_DESCRIPTION_AVAILABLE;

	private PaymentServiceRequest paymentServiceRequest;
	

	public Decision getDecision() {
		return decision;
	}

	public void setDecision(Decision decision) {
		this.decision = decision;
	}

	public String getDecisionCode() {
		return decisionCode;
	}

	public void setDecisionCode(String decisionCode) {
		this.decisionCode = decisionCode;
	}

	public String getDecisionCodeDescription() {
		return decisionCodeDescription;
	}

	public void setDecisionCodeDescription(String decisionCodeDescription) {
		this.decisionCodeDescription = decisionCodeDescription;
	}

	public void setDecisionCodeDescription(GatewayDecisionCodes decisionCodes) {
		this.decisionCodeDescription = decisionCodes.getCodeDescription(decisionCode);
	}

	/**
	 * An identifier for the transaction assigned by the payment gateway. It is required for
	 * sending follow-on requests (e.g. credit, void).   
	 * 
	 * @param transactionId
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public PaymentServiceRequest getPaymentServiceRequest() {
		return paymentServiceRequest;
	}

	public void setPaymentServiceRequest(PaymentServiceRequest paymentServiceRequest) {
		this.paymentServiceRequest = paymentServiceRequest;
	}
	
	public enum Decision {
		ACCEPT, REJECT, ERROR
	}

}
