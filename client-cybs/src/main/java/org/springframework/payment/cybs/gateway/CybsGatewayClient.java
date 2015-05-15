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

import static org.springframework.payment.cybs.gateway.CybsGatewayClientFactoryBean.KEY_MERCHANT_ID;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.LoggerFactory;
import org.springframework.payment.gateway.GatewayClient;
import org.springframework.payment.gateway.GatewayDecisionCodes;
import org.springframework.payment.gateway.GatewayRequest;
import org.springframework.payment.gateway.GatewayResponse;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.cybersource.ws.client.Client;
import com.cybersource.ws.client.ClientException;
import com.cybersource.ws.client.FaultException;
import com.cybersource.ws.client.Logger;

/**
 * <p>An implementation of {@link GatewayClient} that executes request against a CyberSource 
 * payment gateway. This implementation submits name-value pairs through the CyberSource 
 * {@link Client} class.</p> 
 * 
 * <p>It also adapts the CyberSource {@link Logger} class to SLF4J using the "com.cybersource.ws.client.Client"
 * category thus allowing you to use your application logging framework settings to configure CyberSource client
 * output.</p>  
 * 
 * @author Rossen Stoyanchev
 */
public class CybsGatewayClient implements GatewayClient {

	private final Properties clientProperties;

	private final Slf4jToCybsLogger cybsLogger;

	private GatewayDecisionCodes decisionCodes;

	public CybsGatewayClient(Properties clientProperties) {
		this(clientProperties, CybsDecisionCodesFactory.defaultDecisionCodes());
	}

	public CybsGatewayClient(Properties clientProperties, GatewayDecisionCodes codes) {
		Assert.notNull(clientProperties.getProperty(KEY_MERCHANT_ID, null),
				"merchantID client property is a required for creating a CybsGatewayClient");
		this.clientProperties = clientProperties;
		this.decisionCodes = codes;
		this.cybsLogger = new Slf4jToCybsLogger(LoggerFactory.getLogger(Client.class), clientProperties);
	}

	public GatewayResponse execute(GatewayRequest request) throws FaultException, ClientException {
		Map<String, String> requestFields = request.getRequestFields();
		requestFields.put(KEY_MERCHANT_ID, getMerchantId());

		@SuppressWarnings("unchecked")
		Map<String, String> responseFields = Client.runTransaction(requestFields, clientProperties, cybsLogger, false,
				true);

		return new GatewayResponse(responseFields);
	}

	public Map<String, String> getClientProperties() {
		Map<String, String> clientPropertiesMap = new HashMap<String, String>();
		CollectionUtils.mergePropertiesIntoMap(clientProperties, clientPropertiesMap);
		return clientPropertiesMap;
	}

	private String getMerchantId() {
		return clientProperties.getProperty(KEY_MERCHANT_ID, null);
	}

	public GatewayDecisionCodes getDecisionCodes() {
		return decisionCodes;
	}

}
