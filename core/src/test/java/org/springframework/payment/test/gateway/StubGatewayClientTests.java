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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.payment.gateway.GatewayRequest;
import org.springframework.payment.gateway.GatewayResponse;
import org.springframework.payment.test.gateway.StubGatewayResponse;
import org.springframework.payment.test.gateway.StubGatewayClient;

public class StubGatewayClientTests {

	@Test
	public void testRequestResponse() throws Exception {
		Map<String, String> responseFields = new HashMap<String, String>();
		responseFields.put("ccAuthReply_reasonCode", "102");
		
		Map<String, String> requestFieldsToMatch = new HashMap<String, String>();
		requestFieldsToMatch.put("ccAuthService_run", "true");
		requestFieldsToMatch.put("merchantReferenceCode", "ccAuth-1");

		StubGatewayClient gateway = new StubGatewayClient();
		gateway.addResponse(new StubGatewayResponse(requestFieldsToMatch, responseFields));
		GatewayResponse actual = gateway.execute(new GatewayRequest(requestFieldsToMatch));
	
		assertThat(actual.getResponseFields(), equalTo(responseFields));
	}

	@Test
	public void testNoMatch() throws Exception {
		Map<String, String> responseFields = new HashMap<String, String>();
		responseFields.put("ccAuthReply_reasonCode", "102");

		Map<String, String> requestFieldsToMatch = new HashMap<String, String>();
		requestFieldsToMatch.put("ccAuthService_run", "true");
		requestFieldsToMatch.put("merchantReferenceCode", "ccAuth-1");

		Map<String, String> requestFields = new HashMap<String, String>();
		requestFields.put("ccAuthService_run", "true");
		GatewayRequest gatewayRequest = new GatewayRequest(requestFields);

		StubGatewayClient gateway = new StubGatewayClient();
		gateway.addResponse(new StubGatewayResponse(requestFieldsToMatch, responseFields));
		try {
			gateway.execute(gatewayRequest);
			fail("expected no match for the request");
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), containsString("could not find a matching response"));
		}

	}

}
