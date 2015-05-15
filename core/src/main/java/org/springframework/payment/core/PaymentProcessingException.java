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
 * Indicates processing failed due to a general system error or because of the unavailability 
 * of some processing node along the way such as the issuing bank. The general implication of 
 * this exception is that the request may succeed if retried without any further changes.
 * 
 * @author Rossen Stoyanchev
 */
@SuppressWarnings("serial")
public class PaymentProcessingException extends PaymentTransactionException {

	public PaymentProcessingException(String msg, PaymentTransaction paymentTransaction) {
		super(msg, paymentTransaction);
	}

}
