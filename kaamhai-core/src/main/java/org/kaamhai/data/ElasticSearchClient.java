package org.kaamhai.data;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class ElasticSearchClient {
	 
	private static final String index = "kaamhai";

	private static final String server = "http://localhost:9200";

	public static String create(String type, String json)
			throws ClientProtocolException, IOException, Exception {
		return HTTPClient.postRequest(server + "/"+ index +"/" + type.toLowerCase(), json);
	}
	
	public static String update(String type, String json, String id)
			throws ClientProtocolException, IOException, Exception {
		return HTTPClient.postRequest(server + "/"+ index +"/" + type.toLowerCase() + "/" + id, json);
	}

	public static String getById(String type, String id)
			throws ClientProtocolException, IOException, Exception {
		return HTTPClient.getRequest(server + "/"+ index +"/" + type.toLowerCase() + "/" + id);
	}
	
	public static String get(String type)
			throws ClientProtocolException, IOException, Exception {
		return HTTPClient.getRequest(server + "/"+ index +"/" + type.toLowerCase()+"_search");
	}

}
