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

import com.google.inject.Inject;
import com.icpak.rest.dao.helper.RolesDaoHelper;
import com.icpak.rest.models.base.Role;
import com.sun.jersey.api.core.InjectParam;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("roles")
@Api(value="roles", description="Handles CRUD for Roles")
public class RoleResource extends BaseResource<Role> {

	@Inject RolesDaoHelper helper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Retrieve all active Roles")
	public Response getAll(@Context UriInfo uriInfo, 
			@QueryParam("offset") Integer offset,
			@QueryParam("limit") Integer limit) {
		
		return buildCollectionResponse(helper.getAllRoles(uriInfo, offset, limit));
	}
	
	@Path("/{roleId}/users")
	public RoleAssigneesResource getAssigneesResource(@InjectParam RoleAssigneesResource resource){
		return resource;
	}
	
	@Path("/{roleId}/permissions")
	public RolePermissionsResource getPermissions(@InjectParam RolePermissionsResource resource){
		return resource;
	}

	@GET
	@Path("/{roleId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Get a role by roleId", response=Role.class, consumes=MediaType.APPLICATION_JSON)
	public Response getById(@Context UriInfo uriInfo, 
			@ApiParam(value="Role Id of the role to fetch", required=true) @PathParam("roleId") String roleId) {
		
		return buildGetEntityResponse(uriInfo.getAbsolutePath().toString(), helper.getRoleById(roleId));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Create a new role", response=Role.class, consumes=MediaType.APPLICATION_JSON)
	public Response create(@Context UriInfo uriInfo, Role role) {
		helper.createRole(role);
		String uri = uriInfo.getAbsolutePath().toString()+"/"+role.getRoleId();
		return buildCreateEntityResponse(uri,role);
	}

	@PUT
	@Path("/{roleId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Update an existing role",authorizations="Administrators", response=Role.class, 
	consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Response update(@Context UriInfo uriInfo, 
			@ApiParam(value="Role Id of the role to update", required=true) @PathParam("roleId") String roleId, 
			Role role) {
		
		role = helper.updateRole(roleId, role);
		return buildUpdateEntityResponse(uriInfo.getAbsolutePath().toString(), role);
	}

	@DELETE
	@Path("/{roleId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Delete an existing role")
	public Response delete(
			@ApiParam(value="Role Id of the role to delete", required=true) @PathParam("roleId") String roleId) {
		
		helper.deleteRole(roleId);
		return buildDeleteEntityResponse();
	}


}
