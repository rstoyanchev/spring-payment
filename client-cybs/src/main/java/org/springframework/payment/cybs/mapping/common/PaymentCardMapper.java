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

import org.springframework.payment.common.account.PaymentCard;
import org.springframework.payment.gateway.GatewayRequestMapper;

public class PaymentCardMapper extends GatewayRequestMapper<PaymentCard> {

	public PaymentCardMapper() {
		this("card_");
	}

	public PaymentCardMapper(String targetFieldPrefix) {
		super(PaymentCard.class, targetFieldPrefix);
		addMapping("number", prefix("accountNumber"));
		addMapping("expiration.monthTwoDigit", prefix("expirationMonth"));
		addMapping("expiration.year", prefix("expirationYear"));
		addMapping("cardType", prefix("cardType"), new CardTypeToStringConverter());
	}

}
