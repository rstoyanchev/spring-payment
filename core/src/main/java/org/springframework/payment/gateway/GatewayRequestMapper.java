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

import java.text.MessageFormat;

import org.springframework.core.convert.converter.Converter;
import org.springframework.mapping.Mapper;
import org.springframework.mapping.support.MappingMapperBuilder;
import org.springframework.util.StringUtils;

/**
 * A base class for all {@link Mapper} types that map from some source type to a 
 * {@link GatewayRequest}.
 * 
 * @author Rossen Stoyanchev
 */
public abstract class GatewayRequestMapper<S> extends MappingMapperBuilder<S, GatewayRequest> {

	private String targetFieldPrefix;

	/**
	 * @param sourceType the source type to map from
	 * @param targetFieldPrefix an optional argument for use with the {@link #prefix(String)} convenience method
	 */
	public GatewayRequestMapper(Class<S> sourceType, String targetFieldPrefix) {
		super(sourceType, GatewayRequest.class);
		this.targetFieldPrefix = StringUtils.hasLength(targetFieldPrefix) ? targetFieldPrefix : "";
		setTargetFieldDecorator(new Converter<String, String>() {
			public String convert(String source) {
				return MessageFormat.format("requestFields[''{0}'']", source);
			}
		});
		setAutoMappingEnabled(false);
	}

	/**
	 * Prefix the given field with a preset prefix.
	 * @param field the field
	 * @return the prefixed field
	 */
	protected String prefix(String field) {
		return targetFieldPrefix + field;
	}

}
