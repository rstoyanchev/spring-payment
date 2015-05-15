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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.payment.core.GatewayConfigurationException;
import org.springframework.payment.core.InvalidCardException;
import org.springframework.payment.core.InvalidPriorTransactionException;
import org.springframework.payment.core.LostOrStolenCardException;
import org.springframework.payment.core.MissingOrInvalidDataException;
import org.springframework.payment.core.PaymentProcessingException;
import org.springframework.payment.core.RequestNotProcessedException;
import org.springframework.payment.core.TransactionAmountException;
import org.springframework.payment.core.UnknownTransactionOutcomeException;
import org.springframework.payment.core.VerbalAuthorizationRequiredException;
import org.springframework.payment.core.VerificationServiceException;
import org.springframework.util.CollectionUtils;

/**
 * Stores information about decision codes returned from a specific payment gateway.
 * Provides human readable descriptions of the codes as well as lists of codes organized
 * by category.
 * 
 * @author Rossen Stoyanchev
 * 
 * @see GatewayDecisionCodes
 */
public class GatewayDecisionCodes {

	/** Use for codes that are unknown or for codes where no human-readable description is available */
	public static final String NO_DESCRIPTION_AVAILABLE = "No description available";

	private String[] gatewayConfigurationCodes = new String[0];
	
	private String[] invalidCardCodes = new String[0];
	
	private String[] invalidPriorTransactionCodes = new String[0];
	
	private String[] lostOrStolenCardCodes = new String[0];
	
	private String[] missingOrInvalidDataCodes = new String[0];
	
	private String[] paymentProcessingCodes = new String[0];
	
	private String[] requestNotProcessedCodes = new String[0];

	private String[] successCodes = new String[0];
	
	private String[] transactionAmountCodes = new String[0];
	
	private String[] unknownTransactionOutcomeCodes = new String[0];

	private String[] verbalAuthorizationRequiredCodes = new String[0];
	
	private String[] verificationServiceCodes = new String[0];
	
	private Map<String, String> codeDescriptions = new HashMap<String, String>();
	
	/**
	 * @return A human-readable description of the given code.
	 */
	public String getCodeDescription(String code) {
		String description = codeDescriptions.get(code);
		description = (description != null) ? description : NO_DESCRIPTION_AVAILABLE;
		return description;
	}
	
	/**
	 * The location of a property file containing human readable descriptions of the codes.
	 * The location must be a valid classpath location.
	 * 
	 * @param codeDescriptionsLocation the classpath location of the properties file.
	 */
	public void setCodeDescriptionsLocation(String codeDescriptionsLocation) throws IOException {
		Properties props = PropertiesLoaderUtils.loadAllProperties(codeDescriptionsLocation);
		CollectionUtils.mergePropertiesIntoMap(props, codeDescriptions);
	}

	/**
	 * Provide code descriptions through a {@link Properties} instance. 
	 * Alternatively you can also provide the location of a properties file via 
	 * {@link #setCodeDescriptionsLocation(String)}
	 */
	public void setCodeDescriptions(Properties properties) {
		CollectionUtils.mergePropertiesIntoMap(properties, codeDescriptions);
	}

	public String[] getGatewayConfigurationCodes() {
		return gatewayConfigurationCodes;
	}

	/**
	 * @param gatewayConfigurationCodes decision codes corresponding to {@link GatewayConfigurationException}.
	 */
	public void setGatewayConfigurationCodes(String[] gatewayConfigurationCodes) {
		this.gatewayConfigurationCodes = gatewayConfigurationCodes;
	}

	public String[] getInvalidPriorTransactionCodes() {
		return invalidPriorTransactionCodes;
	}

	/**
	 * @param invalidPriorTransactionCodes decision codes corresponding to {@link InvalidPriorTransactionException}.
	 */
	public void setInvalidPriorTransactionCodes(
			String[] invalidPriorTransactionCodes) {
		this.invalidPriorTransactionCodes = invalidPriorTransactionCodes;
	}

	public String[] getInvalidCardCodes() {
		return invalidCardCodes;
	}

	/**
	 * @param invalidCardCodes decision codes corresponding to {@link InvalidCardException}.
	 */
	public void setInvalidCardCodes(String[] invalidCardCodes) {
		this.invalidCardCodes = invalidCardCodes;
	}

	public String[] getLostOrStolenCardCodes() {
		return lostOrStolenCardCodes;
	}

	/**
	 * 
	 * @param lostOrStolenCardCodes decision codes corresponding to {@link LostOrStolenCardException}.
	 */
	public void setLostOrStolenCardCodes(String[] lostOrStolenCardCodes) {
		this.lostOrStolenCardCodes = lostOrStolenCardCodes;
	}

	public String[] getMissingOrInvalidDataCodes() {
		return missingOrInvalidDataCodes;
	}

	/**
	 * @param missingOrInvalidDataCodes decision codes corresponding to {@link MissingOrInvalidDataException}.
	 */
	public void setMissingOrInvalidDataCodes(String[] missingOrInvalidDataCodes) {
		this.missingOrInvalidDataCodes = missingOrInvalidDataCodes;
	}

	public String[] getPaymentProcessingCodes() {
		return paymentProcessingCodes;
	}

	/**
	 * @param paymentProcessingCodes decision codes corresponding to {@link PaymentProcessingException}.
	 */
	public void setPaymentProcessingCodes(String[] paymentProcessingCodes) {
		this.paymentProcessingCodes = paymentProcessingCodes;
	}

	public String[] getRequestNotProcessedCodes() {
		return requestNotProcessedCodes;
	}

	/**
	 * @param requestNotProcessedCodes decision codes corresponding to {@link RequestNotProcessedException}.
	 */
	public void setRequestNotProcessedCodes(String[] requestNotProcessedCodes) {
		this.requestNotProcessedCodes = requestNotProcessedCodes;
	}

	public String[] getSuccessCodes() {
		return successCodes;
	}

	/**
	 * @param successCodes decision codes that indicate success.
	 */
	public void setSuccessCodes(String[] successCodes) {
		this.successCodes = successCodes;
	}

	public String[] getTransactionAmountCodes() {
		return transactionAmountCodes;
	}

	/**
	 * @param transactionAmountCodes decision codes corresponding to {@link TransactionAmountException}.
	 */
	public void setTransactionAmountCodes(String[] transactionAmountCodes) {
		this.transactionAmountCodes = transactionAmountCodes;
	}

	public String[] getUnknownTransactionOutcomeCodes() {
		return unknownTransactionOutcomeCodes;
	}

	/**
	 * @param unknownTransactionOutcomeCodes decision codes corresponding to {@link UnknownTransactionOutcomeException}.
	 */
	public void setUnknownTransactionOutcomeCodes(
			String[] unknownTransactionOutcomeCodes) {
		this.unknownTransactionOutcomeCodes = unknownTransactionOutcomeCodes;
	}

	public String[] getVerbalAuthorizationRequiredCodes() {
		return verbalAuthorizationRequiredCodes;
	}

	/**
	 * @param verbalAuthorizationRequiredCodes decision codes corresponding to {@link VerbalAuthorizationRequiredException}.
	 */
	public void setVerbalAuthorizationRequiredCodes(
			String[] verbalAuthorizationRequiredCodes) {
		this.verbalAuthorizationRequiredCodes = verbalAuthorizationRequiredCodes;
	}

	public String[] getVerificationServiceCodes() {
		return verificationServiceCodes;
	}

	/**
	 * @param verificationNumbersCodes decision codes corresponding to {@link VerificationServiceException}.
	 */
	public void setVerificationServiceCodes(String[] verificationNumbersCodes) {
		this.verificationServiceCodes = verificationNumbersCodes;
	}

}
