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
import org.springframework.payment.creditcard.CreditCardFollowOnCaptureRequest;
import org.springframework.payment.gateway.GatewayRequest;

/**
 * A CyberSource {@link Mapper} types that map from a {@link CreditCardFollowOnCaptureRequest} 
 * to a {@link GatewayRequest}.
 * 
 * @author Rossen Stoyanchev
 */
public class CreditCardFollowOnCaptureRequestMapper extends
		CreditCardCaptureRequestMapper<CreditCardFollowOnCaptureRequest> {

	/**
	 * A default constructor that provides default values for 
	 * {@link #CreditCardFollowOnCaptureRequestMapper(String)}.
	 * The default prefix used is "ccCaptureService_".
	 */
	public CreditCardFollowOnCaptureRequestMapper() {
		this("ccCaptureService_");
	}

	public CreditCardFollowOnCaptureRequestMapper(String targetFieldPrefix) {
		super(CreditCardFollowOnCaptureRequest.class, targetFieldPrefix);
		addMapping("creditCardAuthTransactionId", prefix("authRequestID"));
	}

}
