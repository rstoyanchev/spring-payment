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

package org.springframework.payment.common.order;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.payment.common.account.Account;
import org.springframework.payment.common.account.PaymentMethod;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Valid
	private Account account;

	@Valid
	private PaymentMethod paymentMethod;

	public Order(Account account, PaymentMethod paymentMethod) {
		this.account = account;
		this.paymentMethod = paymentMethod;
	}

	public Account getAccount() {
		return account;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

}
