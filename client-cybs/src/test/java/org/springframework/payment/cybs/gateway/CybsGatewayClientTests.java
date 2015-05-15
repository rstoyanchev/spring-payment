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

package org.springframework.payment.cybs.gateway;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;
import org.springframework.payment.cybs.gateway.CybsGatewayClient;
import org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean;

public class CybsGatewayClientTests {

	@Test
	public void testMinimumSetOfProperties() throws Exception {
		Properties props = new Properties();
		props.put(CybsGatewayClientFactoryBean.KEY_MERCHANT_ID, "foo");
		CybsGatewayClient gateway = new CybsGatewayClient(props);
		assertNotNull(gateway.getDecisionCodes());
		assertEquals("Successful transaction.", gateway.getDecisionCodes().getCodeDescription("100"));
	}

}
