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

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.payment.gateway.GatewayClient;
import org.springframework.util.Assert;

/**
 * A {@link FactoryBean} for configuring a {@link CybsGatewayClient}. The two required 
 * properties are the {@link #merchantId} you used to register with CyberSourec and the 
 * {@link CybsGatewayClientFactoryBean#keysDirectory} where you private key for the 
 * given merchantId resides. All other properties receive a default value. 
 * 
 * @author Rossen Stoyanchev
 */
public class CybsGatewayClientFactoryBean implements FactoryBean<GatewayClient>, InitializingBean, ResourceLoaderAware {

	public static final String KEY_MERCHANT_ID = "merchantID";
	public static final String KEY_KEYS_DIRECTORY = "keysDirectory";
	public static final String KEY_KEYS_FILENAME = "keysFilename";
	public static final String KEY_TARGET_API_VERSION = "targetAPIVersion";
	public static final String KEY_SEND_TO_PRODUCTION = "sendToProduction";
	public static final String KEY_USE_HTTP_CLIENT = "useHttpClient";
	public static final String KEY_TIMEOUT = "timeout";
	public static final String KEY_ENABLE_LOG = "enableLog";
	public static final String KEY_LOG_DIRECTORY = "logDirectory";
	public static final String KEY_LOG_MAXIMUM_SIZE = "logMaximumSize";
	public static final String KEY_PROXY_PORT = "proxyPort";
	public static final String KEY_PROXY_HOST = "proxyHost";
	public static final String PROXY_USER = "proxyUser";
	public static final String KEY_PROXY_PASSWORD = "proxyPassword";

	private static final int DEFAULT_TIMEOUT_MILLISECONDS = (60 * 1000);

	private String cyberSourceConfigLocation;

	private Properties cyberSourceProperties;

	private String merchantId;

	private String keysDirectory;

	private String keysFilename;

	private String targetApiVersion;

	private Boolean sendToProduction;

	private Boolean useHttpClient;

	private Integer timeout;

	private Boolean enableLog;

	private String proxyHost;

	private Integer proxyPort;

	private String proxyUser;

	private String proxyPassword;

	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	private GatewayClient gatewayClient;

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * A path to the directory containing CyberSource private key(s).
	 * The path can contain the usual Spring {@link ResourceLoader} prefixes 
	 * (e.g. classpath:, file:) as documented in {@link ResourceLoader#getResource(String)}.
	 */
	public void setKeysDirectory(String keysDirectory) {
		this.keysDirectory = keysDirectory;
	}

	public void setKeysFilename(String keysFilename) {
		this.keysFilename = keysFilename;
	}

	public void setTargetApiVersion(String targetApiVersion) {
		this.targetApiVersion = targetApiVersion;
	}

	public void setSendToProduction(Boolean sendToProduction) {
		this.sendToProduction = sendToProduction;
	}

	public void setUseHttpClient(Boolean useHttpClient) {
		this.useHttpClient = useHttpClient;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	/**
	 * <p>Note that the {@link CybsGatewayClient} uses {@link Slf4jToCybsLogger} to bridge 
	 * the CyberSource proprietary client Logger to the slf4j API with the logging category of 
	 * com.cybersource.ws.client.Client. </p>
	 * 
	 * <p>By default this property is set to true so that you'll only need to set the logging 
	 * level for the above category in your application logging (e.g. log4j) configuration.</p>  
	 */
	public void setEnableLog(Boolean enableLog) {
		this.enableLog = enableLog;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public void setCyberSourceProperties(Properties cyberSourceProperties) {
		this.cyberSourceProperties = cyberSourceProperties;
	}

	/**
	 * The location path to a properties file containing CyberSource specific client properties.
	 * The path can contain the usual Spring {@link ResourceLoader} prefixes 
	 * (e.g. classpath:, file:) as documented in {@link ResourceLoader#getResource(String)}.
	 */
	public void setCyberSourceConfigLocation(String cyberSourceConfigLocation) {
		this.cyberSourceConfigLocation = cyberSourceConfigLocation;
	}

	public void setGatewayClient(GatewayClient gatewayClient) {
		this.gatewayClient = gatewayClient;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void afterPropertiesSet() throws Exception {

		Properties clientProperties = new Properties();

		if (cyberSourceConfigLocation != null) {
			Resource resource = resourceLoader.getResource(cyberSourceConfigLocation);
			clientProperties.putAll(PropertiesLoaderUtils.loadProperties(resource));
		}

		if (cyberSourceProperties != null) {
			clientProperties.putAll(cyberSourceProperties);
		}

		addRequiredProperty(clientProperties, KEY_MERCHANT_ID, merchantId);

		addRequiredDirectoryProperty(clientProperties, KEY_KEYS_DIRECTORY, keysDirectory);

		addOptionalProperty(clientProperties, KEY_KEYS_FILENAME, keysFilename, null);

		addOptionalProperty(clientProperties, KEY_TARGET_API_VERSION, targetApiVersion, "1.28");

		addOptionalProperty(clientProperties, KEY_SEND_TO_PRODUCTION, sendToProduction, "false");

		addOptionalProperty(clientProperties, KEY_USE_HTTP_CLIENT, useHttpClient, "true");

		addOptionalProperty(clientProperties, KEY_TIMEOUT, timeout, String.valueOf(DEFAULT_TIMEOUT_MILLISECONDS));

		addOptionalProperty(clientProperties, KEY_ENABLE_LOG, enableLog, "true");

		addOptionalProperty(clientProperties, KEY_PROXY_HOST, proxyHost, null);

		addOptionalProperty(clientProperties, KEY_PROXY_PORT, proxyPort, null);

		addOptionalProperty(clientProperties, PROXY_USER, proxyUser, null);

		addOptionalProperty(clientProperties, KEY_PROXY_PASSWORD, proxyPassword, null);

		this.gatewayClient = new CybsGatewayClient(clientProperties);
	}

	public GatewayClient getObject() throws Exception {
		return this.gatewayClient;
	}

	public Class<? extends GatewayClient> getObjectType() {
		return GatewayClient.class;
	}

	public boolean isSingleton() {
		return true;
	}

	private void addRequiredProperty(Properties clientProperties, String key, String value) {
		if (value != null) {
			clientProperties.put(key, value);
		}
		Assert.isTrue(clientProperties.containsKey(key), key + " is a required property");
	}

	private void addRequiredDirectoryProperty(Properties clientProperties, String key, String path) throws IOException {
		if (path != null) {
			Resource resource = resourceLoader.getResource(path);
			File file = resource.getFile();
			if (!file.isDirectory()) {
				throw new IllegalArgumentException(key + " [" + resource + "] does not denote a directory");
			}
			clientProperties.put(key, resource.getFile().getCanonicalPath());
		}
		Assert.isTrue(clientProperties.containsKey(key), key + " is a required property");
	}

	private void addOptionalProperty(Properties clientProperties, String key, Object value, String defaultValue) {
		if (value != null) {
			clientProperties.put(key, String.valueOf(value));
		}
		if ((clientProperties.get(key) == null) && (defaultValue != null)) {
			clientProperties.put(key, defaultValue);
		}
	}

}
