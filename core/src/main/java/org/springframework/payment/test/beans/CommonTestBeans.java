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

import org.springframework.payment.common.account.Account;
import org.springframework.payment.common.account.Address;
import org.springframework.payment.common.account.CardDate;
import org.springframework.payment.common.account.CardType;
import org.springframework.payment.common.account.PaymentCard;
import org.springframework.payment.common.account.PaymentMethod;
import org.springframework.payment.common.account.State;
import org.springframework.payment.common.order.Order;

public abstract class CommonTestBeans {

	public static Order order() {
		return new Order(account(), paymentMethod());
	}

	public static PaymentMethod paymentMethod() {
		return new PaymentMethod(paymentCard(), address());
	}

	public static Account account() {
		return new Account("John", "Doe", "jdoe@mail.com");
	}

	public static PaymentCard paymentCard() {
		return new PaymentCard(CardType.VISA, "4111111111111111", "John Doe", new CardDate(1, 2015));
	}

	public static Address address() {
		return new Address("1295 Charleston Road", "Mountain View", "94043", State.CALIFORNIA);
	}

}
