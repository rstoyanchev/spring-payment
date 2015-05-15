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
import static org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean.KEY_ENABLE_LOG;
import static org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean.KEY_KEYS_DIRECTORY;
import static org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean.KEY_MERCHANT_ID;
import static org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean.KEY_SEND_TO_PRODUCTION;
import static org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean.KEY_TARGET_API_VERSION;
import static org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean.KEY_TIMEOUT;
import static org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean.KEY_USE_HTTP_CLIENT;

import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.springframework.payment.cybs.gateway.CybsGatewayClient;
import org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean;

public class CybsGatewayClientFactoryTests {

	@Test
	public void testMinimumSetOfProperties() throws Exception {
		CybsGatewayClientFactoryBean factoryBean = new CybsGatewayClientFactoryBean();
		Properties properties = new Properties();
		properties.put(KEY_MERCHANT_ID, "s2");
		properties.put(KEY_KEYS_DIRECTORY, "./keys");
		factoryBean.setCyberSourceProperties(properties);
		factoryBean.afterPropertiesSet();
		CybsGatewayClient gateway = (CybsGatewayClient) factoryBean.getObject();
		Map<String, String> actual = gateway.getClientProperties();
		assertEquals("s2", actual.get(KEY_MERCHANT_ID));
		assertEquals("./keys", actual.get(KEY_KEYS_DIRECTORY));
		assertEquals("1.28", actual.get(KEY_TARGET_API_VERSION));
		assertEquals("false", actual.get(KEY_SEND_TO_PRODUCTION));
		assertEquals("true", actual.get(KEY_USE_HTTP_CLIENT));
		assertEquals("60000", actual.get(KEY_TIMEOUT));
		assertEquals("true", actual.get(KEY_ENABLE_LOG));
	}

}
