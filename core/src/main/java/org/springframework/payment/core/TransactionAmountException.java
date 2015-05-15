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

/**
 * Indicates an issue with the transaction amount. It may be due to a partially authorized 
 * amount, or because the requested amount exceeded the authorized amount, or that the 
 * requested amount needs to match the amount of a previous transaction. 
 * Corrective action may be possible by comparing the amounts in the request and in response, 
 * capturing only what has been authorized, or performing a new authorization and capturing 
 * upon success.
 * 
 * @author Rossen Stoyanchev
 */
@SuppressWarnings("serial")
public class TransactionAmountException extends PaymentTransactionException {

	public TransactionAmountException(String msg, PaymentTransaction paymentTransaction) {
		super(msg, paymentTransaction);
	}

}
