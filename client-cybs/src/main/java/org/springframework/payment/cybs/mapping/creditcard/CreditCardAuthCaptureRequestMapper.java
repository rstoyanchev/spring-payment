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
import org.springframework.payment.creditcard.CreditCardAuthCaptureRequest;
import org.springframework.payment.gateway.GatewayRequest;

/**
 * A CyberSource {@link Mapper} types that map from a {@link CreditCardAuthCaptureRequest} 
 * to a {@link GatewayRequest}.
 * 
 * @author Rossen Stoyanchev
 */
public class CreditCardAuthCaptureRequestMapper extends CreditCardCaptureRequestMapper<CreditCardAuthCaptureRequest> {

	/**
	 * A default constructor that provides default values for 
	 * {@link #CreditCardAuthCaptureRequestMapper(CreditCardAuthRequestMapper, String)}.
	 * The default prefix used is "ccCaptureService_".
	 */
	public CreditCardAuthCaptureRequestMapper() {
		this(new CreditCardAuthRequestMapper(), "ccCaptureService_");
	}

	/**
	 * @param ccAuthRequestMapper a mapper to use to map the credit card authorization request data.
	 * @param targetFieldPrefix a common prefix for use with the {@link #prefix(String)} convenience method
	 */
	public CreditCardAuthCaptureRequestMapper(CreditCardAuthRequestMapper ccAuthRequestMapper,
			String targetFieldPrefix) {
		super(CreditCardAuthCaptureRequest.class, targetFieldPrefix);
		addMapping("creditCardAuthRequest", ccAuthRequestMapper);
	}

}
