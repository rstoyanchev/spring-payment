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

package org.springframework.payment.creditcard;

import org.springframework.payment.core.PaymentTransaction;
import org.springframework.util.Assert;

/**
 * Represents a credit capture request that is made at some point after an authorization 
 * request has completed.  
 * 
 * @author Rossen Stoyanchev
 */
public class CreditCardFollowOnCaptureRequest extends CreditCardCaptureRequest {

	private PaymentTransaction creditCardAuthTransaction;

	public CreditCardFollowOnCaptureRequest(PaymentTransaction creditCardAuthTransaction) {
		Assert.notNull(creditCardAuthTransaction, "Follow-on capture required prior authorization transaction");
		this.creditCardAuthTransaction = creditCardAuthTransaction;
		setRequestId(creditCardAuthTransaction.getPaymentServiceRequest().getRequestId());
	}

	public String getCreditCardAuthTransactionId() {
		return creditCardAuthTransaction.getTransactionId();
	}

}
