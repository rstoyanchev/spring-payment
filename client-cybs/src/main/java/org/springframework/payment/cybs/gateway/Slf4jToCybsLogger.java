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

import java.util.Properties;

import org.slf4j.Logger;

import com.cybersource.ws.client.ConfigException;

/**
 * <p>An SLF4J adapter for a CyberSource {@link Logger} translating calls to it to 
 * the SLF4J API. This allows using your application logging framework settings 
 * to configure CyberSource client output.</p>  
 * 
 * @author Rossen Stoyanchev
 */
public class Slf4jToCybsLogger implements com.cybersource.ws.client.Logger {

	private Logger delegate;
	private Properties properties;

	public Slf4jToCybsLogger(Logger log, Properties properties) {
		this.delegate = log;
		this.properties = properties;
	}

	public void log(String paramString1, String paramString2) {
		delegate.debug(paramString1 + " " + paramString2);
	}

	public void logTransactionStart() {
		delegate.debug("TRANSTART =======================================");
		delegate.debug(properties.toString());
	}

	public void prepare() throws ConfigException {
	}

}
