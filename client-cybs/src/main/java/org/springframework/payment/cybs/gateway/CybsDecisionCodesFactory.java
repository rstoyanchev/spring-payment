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
package org.springframework.payment.cybs.gateway;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.payment.gateway.GatewayDecisionCodes;

/**
 * A factory for creating an instance of {@link GatewayDecisionCodes} for use with a
 * {@link CybsGatewayClient}. 
 * 
 * @author Rossen Stoyanchev
 */
public abstract class CybsDecisionCodesFactory {

	private static Logger logger = LoggerFactory.getLogger(CybsDecisionCodesFactory.class);

	/**
	 * Creates an instance of {@link GatewayDecisionCodes} with CybserSource decision codes.
	 */
	public static GatewayDecisionCodes defaultDecisionCodes() {
		return defaultDecisionCodes("org/springframework/payment/cybs/decision-code-descriptions.properties");
	}

	/**
	 * Creates an instance of {@link GatewayDecisionCodes} with CybserSource decision codes.
	 * 
	 * @param codeDescriptionsLocation the fully qualified classpath location of a properties file 
	 * 		containing	human readable gateway decision code descriptions (e.g. 100=Success). 
	 */
	public static GatewayDecisionCodes defaultDecisionCodes(String codeDescriptionsLocation) {
		GatewayDecisionCodes decisionCodes = new GatewayDecisionCodes();
		decisionCodes.setGatewayConfigurationCodes(new String[] { "232","234" });
		decisionCodes.setInvalidCardCodes(new String[] { "202","203","204","208","209","210","211","231","233","240" });
		decisionCodes.setInvalidPriorTransactionCodes(new String[] { "241","242" });
		decisionCodes.setLostOrStolenCardCodes(new String[] { "205","221" });
		decisionCodes.setMissingOrInvalidDataCodes(new String[] { "101","102" });
		decisionCodes.setPaymentProcessingCodes(new String[] { "150","207","236" });
		decisionCodes.setRequestNotProcessedCodes(new String[] { "237","238","243","246","247" });
		decisionCodes.setSuccessCodes(new String[] { "100" });
		decisionCodes.setTransactionAmountCodes(new String[] { "110","235","239" });
		decisionCodes.setUnknownTransactionOutcomeCodes(new String[] { "151","152","250" });
		decisionCodes.setVerbalAuthorizationRequiredCodes(new String[] { "201" });
		decisionCodes.setVerificationServiceCodes(new String[] { "200","230" });
		try {
			decisionCodes.setCodeDescriptionsLocation(codeDescriptionsLocation);
		} catch (IOException e) {
			logger.error("Failed to load decision code descriptions from {}. Root cause: ", codeDescriptionsLocation,
					e.getMessage());
		}
		return decisionCodes;
	}
}
