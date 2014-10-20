package org.kaamhai.dao;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.kaamhai.entity.JobAd;

public interface IJobAdDAO {

	public Object getById(String id) throws ClientProtocolException, IOException, Exception;
	
	public List<JobAd> get() throws ClientProtocolException, IOException, Exception;
	
	public List<JobAd> search(String gender, String location, String city, String language, String category) throws ClientProtocolException, IOException, Exception;
	
	public JobAd create(JobAd jobAd) throws ClientProtocolException, IOException, Exception;
	
	public JobAd update(JobAd jobAd, String id) throws ClientProtocolException, IOException, Exception;
}
