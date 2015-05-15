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

import javax.validation.constraints.NotNull;

public class PaymentCard implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private CardType cardType;

	@NotNull
	private String number;

	private String cardName;

	@NotNull
	private CardDate expiration;

	public PaymentCard(CardType cardType, String number, String cardName, CardDate expiration) {
		this.cardType = cardType;
		this.number = number;
		this.cardName = cardName;
		this.expiration = expiration;
	}

	public CardType getCardType() {
		return cardType;
	}

	public String getNumber() {
		return number;
	}

	public String getCardName() {
		return cardName;
	}

	public CardDate getExpiration() {
		return expiration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + expiration.getMonth();
		result = prime * result + expiration.getYear();
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((cardType == null) ? 0 : cardType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if ((object == null) || (getClass() != object.getClass())) {
			return false;
		}
		PaymentCard other = (PaymentCard) object;
		if ((cardType == null) && (other.cardType != null)) {
			return false;
		}
		if ((number == null) && (other.number != null)) {
			return false;
		}
		return ((!number.equals(other.number)) || (!cardType.equals(other.cardType))
				|| (expiration.getMonth() != other.expiration.getMonth()) || (expiration.getYear() != other.expiration
				.getYear()));
	}

}
