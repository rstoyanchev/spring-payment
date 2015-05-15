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

public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fullName;

	@NotNull
	private String line1;

	private String line2;

	@NotNull
	private String city;

	private State state;

	@NotNull
	private String postalCode;

	@NotNull
	private Country country;

	public Address(String line1, String city, String postalCode, Country country) {
		this.line1 = line1;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
	}

	public Address(String line1, String city, String postalCode, State state) {
		this.line1 = line1;
		this.city = city;
		this.postalCode = postalCode;
		this.state = state;
		this.country = state.getCountry();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public State getState() {
		return state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
