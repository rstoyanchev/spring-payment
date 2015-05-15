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

import java.util.Map;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.payment.cybs.gateway.CybsGatewayClient;
import org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean;
import org.springframework.payment.gateway.GatewayClient;
import org.springframework.payment.gateway.GatewayRequest;
import org.springframework.payment.gateway.GatewayResponse;

public class CybsGatewayClientIntegrationTests {

	private static GatewayClient gateway;

	@BeforeClass
	public static void setUp() throws Exception {
		CybsGatewayClientFactoryBean factory = new CybsGatewayClientFactoryBean();
		ClassPathResource resource = new ClassPathResource("cybersource.properties");
		factory.setCyberSourceProperties(PropertiesLoaderUtils.loadProperties(resource));
		factory.afterPropertiesSet();
		gateway = factory.getObject();
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void testExecuteWithPropertiesFile() throws Exception {
		Resource resource = new ClassPathResource("ccAuthRequest.properties", CybsGatewayClient.class);
		Map map = PropertiesLoaderUtils.loadProperties(resource);
		GatewayRequest request = new GatewayRequest(map);

		GatewayResponse response = gateway.execute(request);

		Properties expected = PropertiesLoaderUtils.loadProperties(new ClassPathResource("ccAuthResponse.properties",
				CybsGatewayClient.class));
		for (Object key : expected.keySet()) {
			assertEquals(expected.get(key), response.getResponseFields().get(key));
		}
	}

}
