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

package org.springframework.payment.cybs.mapping.creditcard;

import org.springframework.mapping.Mapper;
import org.springframework.payment.common.order.Order;
import org.springframework.payment.core.PaymentServiceRequest;
import org.springframework.payment.creditcard.CreditCardAuthRequest;
import org.springframework.payment.cybs.mapping.PaymentServiceRequestMapper;
import org.springframework.payment.cybs.mapping.common.OrderMapper;
import org.springframework.payment.gateway.GatewayRequest;

/**
 * A CyberSource {@link Mapper} types that map from a {@link PaymentServiceRequest} 
 * to a {@link CreditCardAuthRequest}.
 * 
 * @author Rossen Stoyanchev
 */
public class CreditCardAuthRequestMapper extends PaymentServiceRequestMapper<CreditCardAuthRequest> {

	/**
	 * A default constructor that provides default values for 
	 * {@link #CreditCardAuthRequestMapper(Mapper, String)}.
	 * The default prefix used is "ccAuthService_".
	 */
	public CreditCardAuthRequestMapper() {
		this(new OrderMapper(), "ccAuthService_");
	}

	/**
	 * @param orderMapper the order mapper to use to map order information
	 * @param targetFieldPrefix a common prefix for use with the {@link #prefix(String)} convenience method
	 */
	public CreditCardAuthRequestMapper(Mapper<Order, GatewayRequest> orderMapper, String targetFieldPrefix) {
		super(CreditCardAuthRequest.class, targetFieldPrefix);
		addMapping("order", orderMapper);
		addConditionalMapping("total.bigDecimal", "purchaseTotals_grandTotalAmount", "total != null");
		addConditionalMapping("total.currency.currencyCode", "purchaseTotals_currency", "total != null");
	}

	@Override
	public GatewayRequest map(CreditCardAuthRequest source, GatewayRequest target) {
		target = super.map(source, target);
		target.getRequestFields().put(prefix("run"), "true");
		return target;
	}

}
