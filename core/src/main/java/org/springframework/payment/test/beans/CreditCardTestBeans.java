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

package org.springframework.payment.test.beans;

import java.util.Currency;

import org.springframework.payment.common.money.MonetaryAmount;
import org.springframework.payment.creditcard.CreditCardAuthRequest;

public abstract class CreditCardTestBeans {

	public static CreditCardAuthRequest ccAuthRequest() {
		CreditCardAuthRequest authRequest = new CreditCardAuthRequest(CommonTestBeans.order());
		authRequest.setTotal(new MonetaryAmount(10, Currency.getInstance("USD")));
		authRequest.setRequestId("ccAuth-1");
		return authRequest;
	}

}
