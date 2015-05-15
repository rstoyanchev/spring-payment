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
 * Indicates some verification such as AV (address verification) or CV (card verification)
 * failed. The amount may actually have been approved by the issuing bank and it may be
 * possible to capture but the transaction it should be reviewed for possible fraud.
 * 
 * @author Rossen Stoyanchev
 */
@SuppressWarnings("serial")
public class VerificationServiceException extends InvalidCardException {

	public VerificationServiceException(String msg, PaymentTransaction paymentTransaction) {
		super(msg, paymentTransaction);
	}

}
