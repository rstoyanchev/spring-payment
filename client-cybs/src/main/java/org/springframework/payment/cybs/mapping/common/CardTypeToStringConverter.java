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
package org.springframework.payment.cybs.mapping.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.payment.common.account.CardType;

public class CardTypeToStringConverter implements Converter<CardType, String> {

	public String convert(CardType cardType) {
		if (CardType.VISA.equals(cardType)) {
			return "001";
		} else if (CardType.MASTER_CARD.equals(cardType)) {
			return "002";
		} else if (CardType.AMERICAN_EXPRESS.equals(cardType)) {
			return "003";
		} else if (CardType.DISCOVER.equals(cardType)) {
			return "004";
		} else if (CardType.DINERS_CLUB.equals(cardType)) {
			return "005";
		} else if (CardType.CARTE_BLANCHE.equals(cardType)) {
			return "006";
		} else if (CardType.JCB.equals(cardType)) {
			return "007";
		}
		return null;
	}

}