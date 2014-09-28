package org.kaamhai.util;


import java.io.IOException;

import org.kaamhai.entity.EntityMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONDeserializer {
	private static final Logger logger = LoggerFactory.getLogger(JSONDeserializer.class);
	static ObjectMapper mapper = null;

	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	@SuppressWarnings("unchecked")
	public static Object generate(String type, String json) {
		Object obj = null;
		
		try {
			obj = mapper.readValue(json, EntityMap.getType(type));
		}  catch (IOException e) {
			
			logger.error("JSONDeserializer ", e);
		}
		return obj;
	}
	
}
