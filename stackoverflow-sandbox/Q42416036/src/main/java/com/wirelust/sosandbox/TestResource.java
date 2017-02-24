package com.wirelust.sosandbox;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wirelust.sosandbox.representations.TestType;

/**
 * Date: 23-Feb-2017
 *
 * @author T. Curran
 */
@Path("/test")
public class TestResource {

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getResponse() {
		final TestType testType = new TestType();
		testType.setHello("Here is some random string");

		final Response.ResponseBuilder responseBuilder = Response.ok(testType);
		return responseBuilder.build();
	}

}
