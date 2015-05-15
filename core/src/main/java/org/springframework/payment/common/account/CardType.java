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

package org.springframework.payment.common.account;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.util.Assert;

public class CardType implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final ConcurrentMap<String, CardType> cardTypes = new ConcurrentHashMap<String, CardType>();

	public static final CardType VISA = new CardType("Visa");
	public static final CardType MASTER_CARD = new CardType("MasterCard");
	public static final CardType AMERICAN_EXPRESS = new CardType("American Express");
	public static final CardType DISCOVER = new CardType("Discover");
	public static final CardType DINERS_CLUB = new CardType("Diners Club");
	public static final CardType CARTE_BLANCHE = new CardType("Carte Blanche");
	public static final CardType JCB = new CardType("JCB");

	private final String code;

	private final String type;

	public CardType(String type) {
		Assert.hasText(type);
		this.type = type;
		this.code = type.toLowerCase().replace(" ", "_");
		cardTypes.put(code, this);
	}

	public String getType() {
		return type;
	}

	public static CardType valueOf(String code) {
		code = (code != null) ? code.toLowerCase() : null;
		return cardTypes.get(code);
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof CardType)) {
			return false;
		}
		return type.equals(((CardType) object).getType());
	}

	@Override
	public String toString() {
		return type;
	}

}
