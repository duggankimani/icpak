package com.icpak.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.inject.Inject;
import com.icpak.rest.dao.helper.RolesDaoHelper;
import com.icpak.rest.dao.helper.UsersDaoHelper;
import com.icpak.rest.models.base.RoleUser;
import com.icpak.rest.models.base.User;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Roles-Users Sub-resource to be accessed as /roles/role3432/users or
 * /roles/role3424/users/user8900
 * 
 * @author duggan
 *
 */
@Api(value="", description="Roles assignees resource")
public class RoleAssigneesResource extends BaseResource<RoleUser> {

	@Inject
	UsersDaoHelper userHelper;
	@Inject
	RolesDaoHelper roleHelper;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get a list of all users assigned a role", 
	response = User.class, consumes = MediaType.APPLICATION_JSON)
	public Response getAll(@Context UriInfo uriInfo,
			@QueryParam("offset") Integer offset,
			@QueryParam("limit") Integer limit,
			@PathParam("roleId") String roleId) {

		assert roleId!=null;
		return buildCollectionResponse(userHelper.getAllUsers(offset, limit,
				uriInfo, roleId));
	}

	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Assign a role to a user")
	public Response get(
			@Context UriInfo uriInfo,
			@ApiParam(value = "Role Id assign", required = true) @PathParam("roleId") String roleId,
			@ApiParam(value = "User Id assign", required = true) @PathParam("userId") String userId) {

		RoleUser roleUser = roleHelper.getRoleUser(roleId, userId);
		
		roleUser.getRole().setUri(uriInfo.getBaseUri().toString()+"roles/"+roleId);
		roleUser.getUser().setUri(uriInfo.getBaseUri().toString()+"users/"+userId);
		String uri = uriInfo.getAbsolutePath().toString();

		return buildCreateEntityResponse(uri, roleUser);
	}
	
	@POST
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Assign a role to a user")
	public Response create(
			@Context UriInfo uriInfo,
			@ApiParam(value = "Role Id assign", required = true) @PathParam("roleId") String roleId,
			@ApiParam(value = "User Id assign", required = true) @PathParam("userId") String userId) {

		RoleUser roleUser = roleHelper.assign(roleId, userId);
		
		roleUser.getRole().setUri(uriInfo.getBaseUri().toString()+"roles/"+roleId);
		roleUser.getUser().setUri(uriInfo.getBaseUri().toString()+"users/"+userId);
		String uri = uriInfo.getAbsolutePath().toString();

		return buildCreateEntityResponse(uri, roleUser);
	}

	@DELETE
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete role-user assignment")
	public Response delete(
			@ApiParam(value = "Role Id delete", required = true) @PathParam("roleId") String roleId,
			@ApiParam(value = "User Id delete", required = true) @PathParam("userId") String userId) {

		roleHelper.deleteAssignment(roleId, userId);
		return buildDeleteEntityResponse();
	}

}
