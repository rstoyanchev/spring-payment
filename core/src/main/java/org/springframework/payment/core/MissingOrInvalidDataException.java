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
 * Indicates the request is missing some data fields or the data field provided 
 * are invalid. The response from the payment gateway may include additional 
 * fields that provide a list of fields that are missing or invalid.
 * 
 * @author Rossen Stoyanchev
 */
@SuppressWarnings("serial")
public class MissingOrInvalidDataException extends
		PaymentTransactionException {

	public MissingOrInvalidDataException(String msg, PaymentTransaction paymentTransaction) {
		super(msg, paymentTransaction);
	}

}
