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

import org.springframework.payment.gateway.GatewayClient;

/**
 * The base class for the hierarchy of exceptions raised based on analysis of the decision 
 * codes contained in the response from a {@link GatewayClient}. 
 * 
 * @author Rossen Stoyanchev
 */
@SuppressWarnings("serial")
public abstract class PaymentTransactionException extends RuntimeException {

	private PaymentTransaction paymentTransaction;

	public PaymentTransactionException(String message, PaymentTransaction paymentTransaction) {
		super(message);
		this.paymentTransaction = paymentTransaction;
	}

	public PaymentTransaction getPaymentTransaction() {
		return paymentTransaction;
	}

}
