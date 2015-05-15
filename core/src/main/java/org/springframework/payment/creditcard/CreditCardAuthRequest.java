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

import org.springframework.payment.common.money.MonetaryAmount;
import org.springframework.payment.common.order.Order;
import org.springframework.payment.core.PaymentServiceRequest;

/**
 * Represents a credit card authorization request.  
 * 
 * @author Rossen Stoyanchev
 */
public class CreditCardAuthRequest extends PaymentServiceRequest {

	@Valid
	private Order order;

	private MonetaryAmount total;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CreditCardAuthRequest(Order order) {
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

	public MonetaryAmount getTotal() {
		return total;
	}

	public void setTotal(MonetaryAmount total) {
		this.total = total;
	}

}
