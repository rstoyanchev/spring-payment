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
 * This exception means the request completed but had no effect. This may be because 
 * the same service request was processed before (e.g. duplicate transaction). 
 * It could also mean that it is too late to complete a certain request such as 
 * reversing a credit that has already been submitted to the payment processor. 
 * There may be other similar reasons why the a service request was effectively ignored.
 * 
 * @author Rossen Stoyanchev
 */
@SuppressWarnings("serial")
public class RequestNotProcessedException extends PaymentTransactionException {

	public RequestNotProcessedException(String msg, PaymentTransaction paymentTransaction) {
		super(msg, paymentTransaction);
	}

}
