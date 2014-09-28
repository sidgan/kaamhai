package org.kaamhai.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.kaamhai.entity.JobAd;
/**
 * 
 * @author mkurian
 *
 */

@Path("/jobAds")
public interface IJobAdService {

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getById(@PathParam(value = "id") String id) throws Exception;

	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response update(@PathParam(value = "id") String id, JobAd jobAd,
			@Context HttpServletRequest request, @Context HttpHeaders headers)
			throws Exception;

	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response create(@Context HttpServletRequest request,
			@Context HttpHeaders headers, JobAd jobAd) throws Exception;

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response get() throws Exception;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response search(@QueryParam("city") String city, 
			@QueryParam("language")  String language,
			@QueryParam("location")  String location, 
			@QueryParam("category")  String category) 
			throws Exception;
}
