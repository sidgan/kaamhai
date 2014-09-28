package org.kaamhai.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONSerializer {

	private static final Logger logger = LoggerFactory.getLogger(JSONSerializer.class);
	static ObjectMapper mapper = null;

	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	public static String generateJSON( Object object) {
		String json = null;
		
		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			logger.error("JSONProcessing: ", e);
		}
		return json;
	}
}
