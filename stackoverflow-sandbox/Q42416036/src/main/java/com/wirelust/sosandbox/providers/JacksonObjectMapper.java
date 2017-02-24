package com.wirelust.sosandbox.providers;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;

public class JacksonObjectMapper extends ObjectMapper {

	public static JacksonObjectMapper get() {


		JacksonObjectMapper mapper = new JacksonObjectMapper();

		mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
		mapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);

		JacksonAnnotationIntrospector jacksonAnnotationIntrospector = new JacksonAnnotationIntrospector();
		mapper.setDeserializationConfig(mapper.getDeserializationConfig().withAnnotationIntrospector
				(jacksonAnnotationIntrospector));
		mapper.setSerializationConfig(mapper.getSerializationConfig().withAnnotationIntrospector
				(jacksonAnnotationIntrospector));

		return mapper;
	}

}