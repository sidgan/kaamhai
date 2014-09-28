package org.kaamhai.bizlogic;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.client.ClientProtocolException;
import org.kaamhai.dao.IJobAdDAO;
import org.kaamhai.dao.JobAdDAO;
import org.kaamhai.entity.JobAd;
import org.kaamhai.service.IJobAdService;

/**
 * 
 * @author mkurian
 *
 */
public class JobAdManager implements IJobAdService {
	IJobAdDAO dao;

	public JobAdManager() {
		dao = new JobAdDAO();
	}

	public Response getById(String id) throws Exception {
		return Response.status(Status.OK).entity(dao.getById(id)).build();
	}

	public Response get() throws Exception {
		List<JobAd> jobAds = dao.get();
		return Response.status(Status.OK).entity(jobAds).build();
	}

	public Response search(String city, String location, String language,
			String category) throws ClientProtocolException, IOException,
			Exception {
		List<JobAd> jobAds = dao.search(location, city, language, category);
		return Response.status(Status.OK).entity(jobAds).build();
	}

	public Response create(HttpServletRequest request,
			HttpHeaders headers, JobAd ad) throws Exception {
		ad = dao.create(ad);
		return Response.status(Status.OK).entity(ad).build();
	}

	public Response update(String id, JobAd ad,
			HttpServletRequest request, HttpHeaders headers) throws Exception {
		ad = dao.update(ad, id);
		return Response.status(Status.OK).entity(ad).build();
	}
}
