package org.kaamhai.dao;

import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Search;

import java.util.List;

import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONObject;
import org.kaamhai.data.ESClient;
import org.kaamhai.data.ElasticSearchClient;
import org.kaamhai.entity.JobAd;
import org.kaamhai.util.JSONDeserializer;
import org.kaamhai.util.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author mkurian
 *
 */
public class JobAdDAO implements IJobAdDAO{
	
	private static Logger logger = LoggerFactory.getLogger(JobAdDAO.class);
	public JobAd getById(String id) throws Exception{
		
		JobAd ad = null;
		Get adGet = new Get.Builder("kaamhai", id).type("JobAd").build();
		JestResult result = ESClient.getInstance().execute(adGet);
		if(result.isSucceeded()) {
			ad = result.getSourceAsObject(JobAd.class); 
		}  else {
			logger.error("Exception occured while find ad with id {}", result.getJsonString());
			throw new Exception(result.getJsonString());
		}
		return ad;
	}
	
	public List<JobAd> get() throws Exception{
		List<JobAd> ads = null;
		SearchSourceBuilder bldr = new SearchSourceBuilder();
		bldr.size(1000); 
		Search search = new Search.Builder(bldr.toString()).addIndex("kaamhai").addType("JobAd").build();
		JestResult result = ESClient.getInstance().execute(search);
		if(result.isSucceeded()) {
			ads = result.getSourceAsObjectList(JobAd.class);
		}  else {
			logger.error("Exception occured ", result.getJsonString());
			throw new Exception(result.getJsonString());
		}
		return ads;
	}
	
	
	
	public JobAd create(JobAd jobAd) throws Exception{
		String response = ElasticSearchClient.create("JobAd", JSONSerializer.generateJSON(jobAd));
		JSONObject result = new JSONObject(response);
		String id = result.getString("_id");
		jobAd.setId(id);
		response = ElasticSearchClient.update("JobAd", JSONSerializer.generateJSON(jobAd), id);
		
		response = ElasticSearchClient.getById("JobAd", id);
		result = new JSONObject(response);
		JSONObject jobAdResult = result.getJSONObject("_source");
		
		JobAd jobAdspective = (JobAd) JSONDeserializer.generate("JobAd", jobAdResult.toString());
		return jobAdspective;
	}
	
	public JobAd update(JobAd jobAd, String id) throws Exception{
		String response = ElasticSearchClient.update("JobAd", JSONSerializer.generateJSON(jobAd), id);
		
		response = ElasticSearchClient.getById("JobAd", id);
		JSONObject result = new JSONObject(response);
		JSONObject jobAdResult = result.getJSONObject("_source");
		
		JobAd jobAdspective = (JobAd)JSONDeserializer.generate("JobAd", jobAdResult.toString());
		return jobAdspective;
	}

	@Override
	public List<JobAd> search(String location, String city, String language,
			String category) throws Exception {
		List<JobAd> ads = null;
		SearchSourceBuilder bldr = new SearchSourceBuilder();
		BoolFilterBuilder filterBuilder = FilterBuilders.boolFilter();
		if(location != null){
			filterBuilder.should(FilterBuilders.prefixFilter("location", location.toLowerCase()));
		}
		if(city != null){
			filterBuilder.must(FilterBuilders.prefixFilter("city", city.toLowerCase()));
		}
		if(language != null){
			filterBuilder.should(FilterBuilders.prefixFilter("language", language.toLowerCase()));
		}
		if(category != null){
			filterBuilder.must(FilterBuilders.prefixFilter("category", category.toLowerCase()));
		}
		
		filterBuilder.cache(true);
		bldr.postFilter(filterBuilder);
		bldr.size(1000); 
		
		Search search = new Search.Builder(bldr.toString()).addIndex("kaamhai").addType("JobAd").build();
		JestResult result = ESClient.getInstance().execute(search);
		if(result.isSucceeded()) {
			ads = result.getSourceAsObjectList(JobAd.class);
		}  else {
			logger.error("Exception occured ", result.getJsonString());
			throw new Exception(result.getJsonString());
		}
		return ads;
	}
}
