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

/**
 * 
 */
package org.springframework.payment.test.gateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourceArrayPropertyEditor;
import org.springframework.payment.gateway.GatewayRequest;
import org.springframework.payment.gateway.GatewayResponse;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * Holds a static response that can be added to a {@link StubGatewayClient}.
 * Contains both the static response data as well as a set of criteria to
 * use to match this response to incoming requests.    
 * 
 * @author Rossen Stoyanchev
 */
public class StubGatewayResponse {

	private final Logger logger = LoggerFactory.getLogger(StubGatewayResponse.class);

	private Map<String, String> requestFieldsToMatch = new HashMap<String, String>();

	private Map<String, String> responseFields = new HashMap<String, String>();

	/**
	 * Create stub responses without any request fields to match on - i.e. matches to all 
	 * incoming request.
	 * 
	 * @param responseFields The response fields to return if a matching request if found.
	 */
	public StubGatewayResponse(Map<String, String> responseFields) {
		this.responseFields = responseFields;
	}

	/**
	 * Create a stub response with a set of key-value pairs to use to match to a 
	 * request to be matched.
	 * 
	 * @param requestFieldsToMatch Key-value pairs to use to match this response to a request.
	 * @param responseFields The response fields to return if a matching request if found.
	 */
	public StubGatewayResponse(Map<String, String> requestFieldsToMatch, Map<String, String> responseFields) {
		this.requestFieldsToMatch = requestFieldsToMatch;
		this.responseFields = responseFields;
	}

	/**
	 * Create a {@link StubGatewayResponse} with a set of key-value pairs to use to match to a request to be matched.
	 * 
	 * @param requestFieldsToMatch a String containing properties (see {@link PropertiesEditor}).
	 * @param responsePropertiesResource a String indicating the location of a single resource (see {@link ResourceArrayPropertyEditor}).
	 * 
	 * @throws IOException if the responsePropertiesResource could not be loaded
	 */
	public StubGatewayResponse(String requestFieldsToMatch, String responsePropertiesResource) throws IOException {
		PropertiesEditor propsEditor = new PropertiesEditor();
		propsEditor.setAsText(requestFieldsToMatch);
		CollectionUtils.mergePropertiesIntoMap((Properties) propsEditor.getValue(), this.requestFieldsToMatch);

		ResourceArrayPropertyEditor resourceEditor = new ResourceArrayPropertyEditor();
		resourceEditor.setAsText(responsePropertiesResource);
		Resource[] resource = (Resource[]) resourceEditor.getValue();
		Assert.isTrue(resource.length == 1, "stub response not found, or more than one matches found for pattern: "
				+ responsePropertiesResource);
		CollectionUtils.mergePropertiesIntoMap(PropertiesLoaderUtils.loadProperties(resource[0]), this.responseFields);
	}

	public GatewayResponse getGatewayResponse() {
		return new GatewayResponse(responseFields);
	}

	/**
	 * @param gatewayRequest a gateway request
	 * @return true if this response can be returned for the given request 
	 */
	public boolean matchesTo(GatewayRequest gatewayRequest) {
		logger.debug("Trying to match request to keys {}", requestFieldsToMatch.toString());
		Iterator<String> itr = requestFieldsToMatch.keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next().trim();
			String actual = gatewayRequest.getRequestFields().get(key);
			if (actual == null) {
				logger.trace("No value for key {}", key);
				return false;
			}
			String expected = requestFieldsToMatch.get(key).trim();
			if (!actual.equals(expected)) {
				logger.debug("Match failed on key '{}' with expected value '{}'", key, expected);
				return false;
			}
		}
		return true;
	}

}