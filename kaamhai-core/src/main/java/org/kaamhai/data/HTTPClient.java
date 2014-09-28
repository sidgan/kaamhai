package org.kaamhai.data;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP client for ES requests
 * 
 * @author mkurian
 *
 */
public class HTTPClient {

	private static final Logger logger = LoggerFactory.getLogger(HTTPClient.class);
	public static final int HTTP_OK = 200;
	  
	
/**
 * make HTTP PUT request to the uri with putText
 * @param uri
 * @param putText
 * @return
 * @throws ClientProtocolException
 * @throws IOException
 */
	public static String putRequest(String uri, String putText) throws ClientProtocolException, IOException, Exception{
		HttpClient client = new DefaultHttpClient();
		
	    HttpPut httpPut = new HttpPut(uri);
	    httpPut.addHeader("Accept", "application/json");
	    httpPut.addHeader("Content-Type", "application/json");
	    StringEntity entity = new StringEntity(putText, "UTF-8");
	    entity.setContentType("application/json");
	    httpPut.setEntity(entity);
	    HttpResponse response = client.execute(httpPut);
	    
	    logger.info("PUT=", response.getStatusLine().getStatusCode());
	    int statusCode = response.getStatusLine().getStatusCode();
	    if( statusCode != HTTP_OK){
	    	logger.info("PUT=", response.getEntity().toString());
	    	throw new Exception("PUT failed "+ response.getEntity().toString());
	    }
	    return response.getEntity().toString();
	}
	
	
	
/**
 * make HTTP POST request to the uri with putText
 * @param uri
 * @param putText
 * @return
 * @throws ClientProtocolException
 * @throws IOException
 */
	public static String postRequest(String uri, String postText) throws ClientProtocolException, IOException, Exception{
		HttpClient client = new DefaultHttpClient();
		
	    HttpPost httpPost = new HttpPost(uri);
	    httpPost.addHeader("Accept", "application/json");
	    httpPost.addHeader("Content-Type", "application/json");
	    StringEntity entity = new StringEntity(postText, "UTF-8");
	    entity.setContentType("application/json");
	    httpPost.setEntity(entity);
	    HttpResponse response = client.execute(httpPost);
	    
	    int statusCode = response.getStatusLine().getStatusCode();
	    if( statusCode == 200 || statusCode == 201){
	    	logger.info("POST=", response.getStatusLine().getStatusCode());
	    }else{
	    	logger.info("POST=", response.getEntity().toString());
	    	throw new Exception("Post failed "+ response.getEntity().toString());
	    }
	    return  EntityUtils.toString(response.getEntity(), "UTF-8");
	}
	
	
	/***
	 * make HTTP GET request to the uri with args as params
	 * @param uri
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getRequest(String uri) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		
		HttpGet request = new HttpGet(uri);
		HttpResponse response = client.execute(request);
	 
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode != HTTP_OK){
			return null;
		}
		
		logger.info("GET=", response.getStatusLine().getStatusCode());
		StringBuffer result = null;
		BufferedReader rd =null;
		 try{
			rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		 
			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		 }catch(Exception ex){
			 throw ex;
		 }
		 finally{
			 if(rd != null){
				 rd.close();
			 }
		 }
		return result.toString();
	}
	
}
