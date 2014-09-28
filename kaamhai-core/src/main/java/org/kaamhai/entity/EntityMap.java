package org.kaamhai.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author mkurian
 *
 */
public class EntityMap {

	@SuppressWarnings("rawtypes")
	static Map<String, Class> entityMap;
	static{
		entityMap = new HashMap<>();
		entityMap.put("JobAd", JobAd.class);	
		entityMap.put("User", User.class);
	
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getType(String typeName){
		return entityMap.get(typeName);
	}
}
