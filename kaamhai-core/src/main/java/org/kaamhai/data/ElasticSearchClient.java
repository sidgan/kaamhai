package org.kaamhai.data;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.kaamhai.util.Constants;

public class ElasticSearchClient {
	 
	private static final String index = Constants.KAAMHAI_INDEX;

	private static final String server = Constants.ES_HOST;

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
		return HTTPClient.getRequest(server + "/"+ index +"/" + type.toLowerCase()+"/_search");
	}

	
	public static String search(String type, String searchSource)
			throws ClientProtocolException, IOException, Exception {
		return HTTPClient.postRequest(server + "/"+ index +"/" + type.toLowerCase()+"/_search", searchSource);
	}

}
