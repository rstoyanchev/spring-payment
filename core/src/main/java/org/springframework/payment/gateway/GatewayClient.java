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

package org.springframework.payment.gateway;

/**
 * Represents a payment gateway client. Implementations of this type are 
 * responsible for the actual sending and receiving of requests to payment 
 * gateway servers. 
 * 
 * @author Rossen Stoyanchev
 */
public interface GatewayClient {

	/**
	 * A method for sending requests to a payment gateway. 
	 * The input ({@link GatewayRequest}) and the output {@link GatewayResponse} 
	 * to this method contain data that is formatted 
	 * and structured according to the expectations of the specific payment gateway.
	 * Concerns common to all payment gateways such as type conversion, validation, 
	 * or the interpretation of gateway decisions are handled in the layer above.    
	 * 
	 * @param request the gateway request
	 * @return the gateway response
	 * 
	 * @throws Exception an exception resulting from the execution of the request.
	 * 		In most cases this method returns normally and the {@link GatewayResponse} 
	 * 		contains information about the decision (accept, reject, error). 
	 * 		An exception raised from this method represents a lower-level error.  
	 */
	GatewayResponse execute(GatewayRequest request) throws Exception;

	/**
	 * @return A structure that contains information about the decisions codes of this
	 * 		particular payment gateway such as human readable decision code descriptions
	 * 		as well lists of decision codes organized according to common categories.
	 */
	GatewayDecisionCodes getDecisionCodes();

}
