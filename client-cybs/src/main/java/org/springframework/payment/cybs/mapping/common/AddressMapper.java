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

package org.springframework.payment.cybs.mapping.common;

import org.springframework.payment.common.account.Address;
import org.springframework.payment.gateway.GatewayRequestMapper;

public class AddressMapper extends GatewayRequestMapper<Address> {

	public AddressMapper(String targetFieldPrefix) {
		super(Address.class, targetFieldPrefix);
		addMapping("line1", prefix("street1"));
		addConditionalMapping("line2", prefix("street2"), "line2 != null");
		addMapping("city", prefix("city"));
		addConditionalMapping("state", prefix("state"), new StateToStringConverter(), "state != null");
		addMapping("postalCode", prefix("postalCode"));
		addMapping("country", prefix("country"), new CountryToStringConverter());
	}

}
