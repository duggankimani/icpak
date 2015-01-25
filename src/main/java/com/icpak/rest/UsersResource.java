package com.icpak.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import com.google.inject.Inject;
import com.icpak.rest.dao.helper.UsersDaoHelper;
import com.icpak.rest.models.auth.User;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * StormPath REST API Design Ideas ->
 * https://www.youtube.com/watch?v=hdSrT4yjS1g
 * 
 * <p>
 * <b>1 Big Gotcha</b> <br>
 * Resource Classes are managed by Jersey and not Guice. This is a problem if
 * you need to use @Transactional in such classes. i.e. Putting @Transactional
 * in this class (MemberResource) has no effect. Guice will not begin a
 * transaction for your request. <br>
 * A workaround is to delegate execution to a guice managed class, then inject
 * such classes to the resource class.
 * <p>
 * 
 * @author duggan
 *
 */
@RequiresAuthentication
@Path("users")
@Api(value = "users", description = "Handles CRUD on User data")
public class UsersResource extends BaseResource<User> {

	@Inject
	UsersDaoHelper helper;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get a list of all users", response = User.class, consumes = MediaType.APPLICATION_JSON)
	public Response getAll(@Context UriInfo uriInfo,
			@QueryParam("offset") Integer offset,
			@QueryParam("limit") Integer limit) {

		return buildCollectionResponse(helper.getAllUsers(offset, limit,
				uriInfo));
	}

	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get a user by userId", response = User.class, consumes = MediaType.APPLICATION_JSON)
	public Response getById(
			@Context UriInfo uriInfo,
			@ApiParam(value = "User Id of the user to fetch", required = true) @PathParam("userId") String userId) {

		User user = helper.getUser(userId);
		return buildGetEntityResponse(uriInfo.getAbsolutePath().toString(),
				user);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create a new user", response = User.class, consumes = MediaType.APPLICATION_JSON)
	public Response create(@Context UriInfo uriInfo, User user) {

		helper.add(user);
		String uri = uriInfo.getAbsolutePath().toString() + "/"
				+ user.getRefId();
		return buildCreateEntityResponse(uri, user);
	}

	@PUT
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update an existing user", response = User.class, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public Response update(
			@Context UriInfo uriInfo,
			@ApiParam(value = "User Id of the user to fetch", required = true) @PathParam("userId") String userId,
			User user) {

		helper.update(userId, user);
		String uri = uriInfo.getAbsolutePath().toString();
		return buildUpdateEntityResponse(uri, user);
	}

	@DELETE
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete an existing user")
	public Response delete(
			@ApiParam(value = "User Id of the user to fetch", required = true) @PathParam("userId") String userId) {

		helper.delete(userId);

		return buildDeleteEntityResponse();
	}
}
