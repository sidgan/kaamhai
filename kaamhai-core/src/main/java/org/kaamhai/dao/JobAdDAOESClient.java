package org.kaamhai.dao;

import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Search;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kaamhai.data.ESClient;
import org.kaamhai.data.ElasticSearchClient;
import org.kaamhai.entity.JobAd;
import org.kaamhai.util.Constants;
import org.kaamhai.util.JSONDeserializer;
import org.kaamhai.util.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author mkurian
 *
 */
public class JobAdDAOESClient implements IJobAdDAO{
	
	private static Logger logger = LoggerFactory.getLogger(JobAdDAOESClient.class);
	public JobAd getById(String id) throws Exception{
		JobAd ad = null;
		Get adGet = new Get.Builder(Constants.KAAMHAI_INDEX, id).type(Constants.JOBAD_TYPE).build();
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
		Search search = new Search.Builder(bldr.toString()).addIndex(Constants.KAAMHAI_INDEX).addType(Constants.JOBAD_TYPE).build();
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
		String response = ElasticSearchClient.create(Constants.JOBAD_TYPE, JSONSerializer.generateJSON(jobAd));
		JSONObject result = new JSONObject(response);
		String id = result.getString("_id");
		jobAd.setId(id);
		response = ElasticSearchClient.update(Constants.JOBAD_TYPE, JSONSerializer.generateJSON(jobAd), id);
		
		response = ElasticSearchClient.getById(Constants.JOBAD_TYPE, id);
		result = new JSONObject(response);
		JSONObject jobAdResult = result.getJSONObject("_source");
		
		JobAd jobAdspective = (JobAd) JSONDeserializer.generate("JobAd", jobAdResult.toString());
		return jobAdspective;
	}
	
	public JobAd update(JobAd jobAd, String id) throws Exception{
		String response = ElasticSearchClient.update(Constants.JOBAD_TYPE, JSONSerializer.generateJSON(jobAd), id);
		
		response = ElasticSearchClient.getById(Constants.JOBAD_TYPE, id);
		JSONObject result = new JSONObject(response);
		JSONObject jobAdResult = result.getJSONObject("_source");
		
		JobAd jobAdspective = (JobAd)JSONDeserializer.generate("JobAd", jobAdResult.toString());
		return jobAdspective;
	}

	@Override
	public List<JobAd> search(String gender, String location, String city, String language,
			String category) throws Exception {
		List<JobAd> ads = new ArrayList<>();
		SearchSourceBuilder bldr = new SearchSourceBuilder();
		BoolFilterBuilder filterBuilder = FilterBuilders.boolFilter();
		if(gender != null){
			filterBuilder.should(FilterBuilders.prefixFilter("gender", gender.toLowerCase()));
		}
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
		
//		Search search = new Search.Builder(bldr.toString()).addIndex(Constants.KAAMHAI_INDEX).addType(Constants.JOBAD_TYPE).build();
//		JestResult result = ESClient.getInstance().execute(search);
//		if(result.isSucceeded()) {
//			ads = result.getSourceAsObjectList(JobAd.class);
//		}  else {
//			logger.error("Exception occurred ", result.getJsonString());
//			throw new Exception(result.getJsonString());
//		}
		
		String response = ElasticSearchClient.search(Constants.JOBAD_TYPE, bldr.toString());
		JSONObject result = new JSONObject(response);
		JSONObject jobAdResult = result.getJSONObject("hits");
		JSONArray hits = jobAdResult.getJSONArray("hits");
		for(int i = 0 ; i < hits.length() ; i++){
			JSONObject hit = (JSONObject) hits.get(i);
			String source = hit.getJSONObject("_source").toString();
			
			JobAd jobAd = (JobAd)JSONDeserializer.generate("JobAd", source.toString());
			ads.add(jobAd);
		}
		
		return ads;
	}
}
