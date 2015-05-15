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
import java.util.Calendar;

public class CardDate implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int month;

	private final int year;

	public CardDate(int month, int year) {
		this.month = month;
		this.year = year;
		// TODO: validate month/year combination
	}

	/**
	 * A value between 1 and 12.
	 */
	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public String getMonthTwoDigit() {
		return (month < 10) ? "0" + String.valueOf(month) : String.valueOf(month);
	}

	/**
	 * Create a CardDate from the given inputs.
	 * 
	 * @param month a number between 1 and 12 expressed as two characters - 01, 02, etc.
	 * @param year a year
	 */
	public static CardDate valueOfTwoDigitMonth(String month, int year) {
		return new CardDate(Integer.parseInt(month), year);
	}

	/**
	 * Create a CardDate by obtaining the month and year from the given Calendar instance. 
	 */
	public static CardDate valueOf(Calendar calendar) {
		return new CardDate(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
	}

	@Override
	public String toString() {
		return "CardDate [month=" + getMonthTwoDigit() + ", year=" + year + "]";
	}

}
