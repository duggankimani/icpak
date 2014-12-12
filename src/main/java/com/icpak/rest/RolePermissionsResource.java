package com.icpak.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.inject.Inject;
import com.icpak.rest.dao.helper.RolesDaoHelper;
import com.icpak.rest.models.base.Role;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Api(value="", description="Roles permissions resource")
public class RolePermissionsResource extends BaseResource<Role>{

	@Inject RolesDaoHelper helper;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Utility service to assign a new permission to role", response=Role.class, 
	consumes=MediaType.APPLICATION_JSON)
	public Response get(@Context UriInfo uriInfo,
			@ApiParam(value = "Role Id to assign permission", required = true) @PathParam("roleId") String roleId){
		
		Role role = helper.getRoleById(roleId,"permissions");
		
		String entityUri= uriInfo.getBaseUri().toString()+"roles/"+role.getRoleId();
		return buildUpdateEntityResponse(entityUri, role);
	}
	
	@POST
	@Path("/{permissionName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Utility service to assign a new permission to role", response=Role.class, 
	consumes=MediaType.APPLICATION_JSON)
	public Response create(@Context UriInfo uriInfo,
			@ApiParam(value = "Role Id to assign permission", required = true) @PathParam("roleId") String roleId,
			@ApiParam(value = "Name of Permission to assign", required = true) @PathParam("permissionName") String permissionName){
		
		Role role = helper.setPermission(roleId, permissionName);
		
		String entityUri= uriInfo.getBaseUri().toString()+"/roles/"+role.getRoleId();
		return buildUpdateEntityResponse(entityUri, role);
	}
	
	@DELETE
	@Path("/{permissionName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Utility api to remove a permission from role")
	public Response delete(@ApiParam(value = "Role Id delete", required = true) @PathParam("roleId") String roleId,
			@ApiParam(value = "Name of Permission to delete", required = true) @PathParam("permissionName") String permissionName){
		
		helper.deletePermission(roleId, permissionName);
		
		return buildDeleteEntityResponse();
	}
}
