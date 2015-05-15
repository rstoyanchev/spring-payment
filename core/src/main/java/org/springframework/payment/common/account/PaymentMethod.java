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

package org.springframework.payment.common.account;

import java.io.Serializable;
import javax.validation.Valid;

public class PaymentMethod implements Serializable {

	private static final long serialVersionUID = 1L;

	@Valid
	private final PaymentCard paymentCard;

	@Valid
	private final Address address;

	public PaymentMethod(PaymentCard paymentCard, Address address) {
		this.paymentCard = paymentCard;
		this.address = address;
	}

	public PaymentCard getPaymentCard() {
		return paymentCard;
	}

	public Address getAddress() {
		return address;
	}

}
