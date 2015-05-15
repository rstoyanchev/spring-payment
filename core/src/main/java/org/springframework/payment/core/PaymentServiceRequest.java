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

package org.springframework.payment.core;

import org.springframework.payment.gateway.GatewayClient;

/**
 * A base type for all payment service requests. A <code>PaymentServiceRequest</code> is
 * submitted to a {@link GatewayClient}, which returns a {@link PaymentTransaction}.
 * 
 * @author Rossen Stoyanchev
 */
public class PaymentServiceRequest {

	private String requestId;

	public String getRequestId() {
		return requestId;
	}

	/** 
	 * Merchant-assigned order reference or tracking number to makes it easier to track resulting 
	 * transactions including follow-on service requests (void, credit, etc.) 
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
