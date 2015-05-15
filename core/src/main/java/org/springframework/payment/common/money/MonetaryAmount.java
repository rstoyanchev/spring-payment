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

package org.springframework.payment.common.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;

import org.springframework.util.Assert;

/**
 * A representation of money.
 * 
 * A value object. Immutable.
 */
public class MonetaryAmount implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal value;

	private Currency currency;

	/**
	 * Create a new monetary amount from the specified value.
	 * 
	 * @param value the value of the amount; for example, in $USD "10.00" would be ten 
	 * 			dollars, ".29" would be 29 cents
	 * @param currency the currency (see {@link Currency#getInstance(Locale)} and 
	 * 			{@link Currency#getInstance(String)}).
	 */
	public MonetaryAmount(BigDecimal value, Currency currency) {
		initValue(value, currency);
	}

	/**
	 * Create a new monetary amount from the specified value.
	 * 
	 * @param value the monetary amount as a double
	 * @param currency the currency (see {@link Currency#getInstance(Locale)} and 
	 * 			{@link Currency#getInstance(String)}).
	 */
	public MonetaryAmount(double value, Currency currency) {
		initValue(BigDecimal.valueOf(value), currency);
	}

	@SuppressWarnings("unused")
	private MonetaryAmount() {
	}

	/**
	 * Returns the zero (0.00) monetary amount.
	 */
	public static MonetaryAmount zero(Currency currency) {
		return new MonetaryAmount(0, currency);
	}

	/**
	 * Add to this monetary amount, returning the sum as a new monetary amount.
	 * 
	 * @param amount the amount to add
	 * @return the sum
	 */
	public MonetaryAmount add(MonetaryAmount amount) {
		Assert.isTrue(currency.equals(amount.getCurrency()),
				"Adding an amount of a different currency is not supported presently.");
		return new MonetaryAmount(value.add(amount.value), currency);
	}

	/**
	 * Subtract from this monetary amount, returning the difference as a new
	 * monetary amount.
	 * 
	 * @param amount the amount to subtract
	 * @return the difference
	 */
	public MonetaryAmount subtract(MonetaryAmount amount) {
		Assert.isTrue(currency.equals(amount.getCurrency()),
				"Adding an amount of a different currency is not supported presently.");
		return new MonetaryAmount(value.subtract(amount.value), currency);
	}

	/**
	 * Multiply this monetary amount, returning the product as a new monetary
	 * amount.
	 * 
	 * @param amount the amount to multiply
	 * @return the product
	 */
	public MonetaryAmount multiplyBy(BigDecimal amount) {
		return new MonetaryAmount(value.multiply(amount), currency);
	}

	/**
	 * Divide this monetary amount, returning the quotient as a decimal.
	 * 
	 * @param amount the amount to divide by
	 * @return the quotient
	 */
	public BigDecimal divide(MonetaryAmount amount) {
		return value.divide(amount.value);
	}

	/**
	 * Divide this monetary amount, returning the quotient as a new monetary
	 * amount.
	 * 
	 * @param amount the amount to divide by
	 * @return the quotient
	 */
	public MonetaryAmount divideBy(BigDecimal amount) {
		return new MonetaryAmount(value.divide(amount), currency);
	}

	/**
	 * Multiply this monetary amount by a percentage.
	 * 
	 * @param percentage the percentage
	 * @return the percentage amount
	 */
	public MonetaryAmount multiplyBy(Percentage percentage) {
		return new MonetaryAmount(value.multiply(percentage.asBigDecimal()), currency);
	}

	/**
	 * Returns true if this amount is greater than the amount.
	 * 
	 * @param amount the monetary amount
	 * @return true or false
	 */
	public boolean greaterThan(MonetaryAmount amount) {
		return value.compareTo(amount.value) > 0;
	}

	/**
	 * Get this amount as a double. Useful for when a double type is needed by
	 * an external API or system.
	 * 
	 * @return this amount as a double
	 */
	public double getDouble() {
		return value.doubleValue();
	}

	/**
	 * Get this amount as a big decimal. Useful for when a BigDecimal type is
	 * needed by an external API or system.
	 * 
	 * @return this amount as a big decimal
	 */
	public BigDecimal getBigDecimal() {
		return value;
	}

	/**
	 * Get the currency of this amount
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	public boolean equals(Object o) {
		if (!(o instanceof MonetaryAmount)) {
			return false;
		}
		return value.equals(((MonetaryAmount) o).value);
	}

	public int hashCode() {
		return value.hashCode();
	}

	public String toString() {
		return currency.getSymbol() + value.toString();
	}

	// internal helpers

	private void initValue(BigDecimal value, Currency currency) {
		this.currency = currency;
		this.value = value.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
	}

}
