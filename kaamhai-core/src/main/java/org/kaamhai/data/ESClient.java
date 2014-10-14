package org.kaamhai.data;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Returns a singleton client for handling all requests
 * 
 * @author mkurian
 *
 */
public class ESClient {
	
	private static Logger logger = LoggerFactory.getLogger(ESClient.class);
	
	/**
	 * Get ES Client 
	 * 
	 * @return
	 * @throws Exception
	 */
	public static JestClient getInstance() throws Exception{
		if(client == null){
			init();
		}
		return client;
	}

	static private JestClientFactory _factory = null;
	static private JestClient client = null;
	
	/**
	 *	Create the singleton Client
	 * @throws Exception
	 */
	static private void init() throws Exception {
		try {
			HttpClientConfig clientConfig = new HttpClientConfig.Builder("htpp://172.16.122.85:9200").multiThreaded(true).discoveryEnabled(true).
					 discoveryFrequency(1L, TimeUnit.SECONDS)
					 .connTimeout(60000).readTimeout(200000)
					 .build();
			logger.info( "Connection timeout:{}" ,clientConfig.getConnTimeout());
			logger.info( "Read timeout:{}" ,clientConfig.getReadTimeout());
			 // Construct a new Jest client according to configuration via factory
			_factory = new JestClientFactory();
			_factory.setHttpClientConfig(clientConfig);
			client = _factory.getObject();
		} catch (Exception e) {
			logger.error( "Error Initializing Elastic Search ", e);
			throw new Exception(e);
		}
	}

	
}
