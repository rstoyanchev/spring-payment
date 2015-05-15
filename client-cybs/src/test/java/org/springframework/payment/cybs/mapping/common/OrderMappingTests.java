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

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.springframework.payment.common.order.Order;
import org.springframework.payment.gateway.GatewayRequest;
import org.springframework.payment.test.beans.CommonTestBeans;

public class OrderMappingTests {

	@Test
	public void testOrder() throws Exception {
		Order order = CommonTestBeans.order();
		GatewayRequest target = new GatewayRequest();
		new OrderMapper().map(order, target);
		assertOrder(order, target);
	}

	public static void assertOrder(Order order, GatewayRequest target) {
		Map<String, String> requestFields = target.getRequestFields();
		assertEquals(requestFields.get("billTo_firstName"), order.getAccount().getFirstName());
		assertEquals(requestFields.get("billTo_lastName"), order.getAccount().getLastName());
		assertEquals(requestFields.get("billTo_email"), order.getAccount().getEmail());
	}


}
