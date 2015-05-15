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

package org.springframework.payment.cybs.mapping;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.payment.core.PaymentTransaction;
import org.springframework.payment.gateway.GatewayResponse;

public class PaymentTransactionMappingTests {

	@Test
	public void testCreditCardAuthTransaction() throws Exception {
		Map<String, String> responseFields = new HashMap<String, String>();
		responseFields.put("decision", "ACCEPT");
		responseFields.put("requestID", "ccAuth-Tx-1");
		GatewayResponse source = new GatewayResponse(responseFields);
		PaymentTransaction target = new PaymentTransaction();

		new PaymentTransactionMapper().map(source, target);

		assertEquals(target.getDecision().toString(), "ACCEPT");
		assertEquals(target.getTransactionId(), "ccAuth-Tx-1");
	}

}
