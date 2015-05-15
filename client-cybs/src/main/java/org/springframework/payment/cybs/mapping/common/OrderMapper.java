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

package org.springframework.payment.cybs.mapping.common;

import org.springframework.mapping.Mapper;
import org.springframework.payment.common.account.Account;
import org.springframework.payment.common.account.PaymentMethod;
import org.springframework.payment.common.order.Order;
import org.springframework.payment.gateway.GatewayRequest;
import org.springframework.payment.gateway.GatewayRequestMapper;

public class OrderMapper extends GatewayRequestMapper<Order> {

	public OrderMapper() {
		this(new PaymentMethodMapper(), new AccountMapper("billTo_"), "billTo_");
	}

	public OrderMapper(Mapper<PaymentMethod, GatewayRequest> paymentMethodMapper,
			Mapper<Account, GatewayRequest> accountMapper, String targetFieldPrefix) {
		super(Order.class, targetFieldPrefix);
		addMapping("paymentMethod", paymentMethodMapper);
		addMapping("account", accountMapper);
	}

}
