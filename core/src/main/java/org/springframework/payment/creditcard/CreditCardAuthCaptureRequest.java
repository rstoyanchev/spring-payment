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

import javax.validation.Valid;

import org.springframework.util.Assert;

/**
 * Represents a combined credit card authorization and capture request. The two can also
 * be made separately and at different times. 
 * 
 * @author Rossen Stoyanchev
 */
public class CreditCardAuthCaptureRequest extends CreditCardCaptureRequest {

	@Valid
	private CreditCardAuthRequest creditCardAuthRequest;

	public CreditCardAuthCaptureRequest(CreditCardAuthRequest creditCardAuthRequest) {
		Assert.notNull(creditCardAuthRequest, "A capture requires an authorization request");
		this.creditCardAuthRequest = creditCardAuthRequest;
		setTotal(creditCardAuthRequest.getTotal());
		setRequestId(creditCardAuthRequest.getRequestId());
	}

	public CreditCardAuthRequest getCreditCardAuthRequest() {
		return creditCardAuthRequest;
	}

}
