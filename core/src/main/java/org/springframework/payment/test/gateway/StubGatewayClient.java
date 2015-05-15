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

package org.springframework.payment.test.gateway;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.payment.gateway.GatewayDecisionCodes;
import org.springframework.payment.gateway.GatewayRequest;
import org.springframework.payment.gateway.GatewayResponse;
import org.springframework.payment.gateway.GatewayClient;

/**
 * An implementation of {@link GatewayClient} that can be configured with static responses.
 * The {@link #execute(GatewayRequest)} method tries to match the incoming request against
 * the responses it has and the first matching response is returned or an 
 * {@link IllegalArgumentException} is thrown.   
 * 
 * @author Rossen Stoyanchev
 */
public class StubGatewayClient implements GatewayClient {

	private static final Logger logger = LoggerFactory.getLogger(StubGatewayClient.class);

	private GatewayRequest gatewayRequest;

	private final List<StubGatewayResponse> stubResponses;

	private final GatewayDecisionCodes decisionCodes;

	public StubGatewayClient() {
		this(new GatewayDecisionCodes());
	}

	/**
	 * A constructor that accepts a set of decision codes to use.
	 * @param codes the decision codes
	 */
	public StubGatewayClient(GatewayDecisionCodes codes) {
		this.stubResponses = new ArrayList<StubGatewayResponse>();
		this.decisionCodes = codes;
	}

	/**
	 * The execution consists of comparing the incoming request against a set of stub 
	 * (static) responses. The first matching response found is returned.
	 * 
	 * @throws IllegalArgumentException if no matching response is found.
	 */
	public GatewayResponse execute(GatewayRequest gatewayRequest) throws Exception {
		logger.debug("Processing gateway request in off-line mode");
		this.gatewayRequest = gatewayRequest;
		for (StubGatewayResponse stubResponse : stubResponses) {
			if (stubResponse.matchesTo(gatewayRequest)) {
				return stubResponse.getGatewayResponse();
			}
		}
		throw new IllegalArgumentException("Stub payment gateway could not find a matching response for request "
				+ gatewayRequest.getRequestFields().toString());
	}

	/**
	 * Use this method to add one or more stub (static) responses.
	 * @param stubResponse the stub response to add
	 */
	public void addResponse(StubGatewayResponse stubResponse) {
		stubResponses.add(stubResponse);
	}

	public GatewayRequest getGatewayRequest() {
		return gatewayRequest;
	}

	public GatewayDecisionCodes getDecisionCodes() {
		return decisionCodes;
	}

}
