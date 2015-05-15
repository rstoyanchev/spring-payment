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

import org.springframework.core.NestedRuntimeException;
import org.springframework.payment.gateway.GatewayClient;

/**
 * A common exception raise upon catching an exception coming from an underlying {@link GatewayClient}
 * instance. Note that when a payment gateway returns a response it will contain a decision 
 * (accept, reject, error) along with additional decision codes. This exception indicates 
 * a lower level issue raised while sending or receiving a response from the payment 
 * gateway server. 
 * 
 * @author Rossen Stoyanchev
 */
@SuppressWarnings("serial")
public class GatewayClientException extends NestedRuntimeException {

	public GatewayClientException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
