package com.icpak.rest;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.icpak.rest.models.base.ResourceModel;
import com.icpak.rest.models.base.ResourceCollectionModel;

/**
 * Generic Class for preparation of responses back to caller:
 * <br>
 * Response Codes standardized here
 * @author duggan
 *
 */
public abstract class BaseResource<T extends ResourceModel> {

	public static int PAGE_LIMIT=3;
	
	public Response buildCollectionResponse(@SuppressWarnings("rawtypes") ResourceCollectionModel entities){
		return Response.ok().entity(entities).build();
	}
	
	public Response buildGetEntityResponse(String entityUri,ResourceModel entity){
		entity.setUri(entityUri);
		return Response.ok().entity(entity).build();
	}
	
	public Response buildCreateEntityResponse(String entityUri, ResourceModel entity){
		entity.setUri(entityUri);
		return Response.created(URI.create(entityUri)).entity(entity).build();
	}
	
	public Response buildUpdateEntityResponse(String entityUri, ResourceModel entity){
		entity.setUri(entityUri);
		return Response.ok().entity(entity).build();
	}
	
	public Response buildDeleteEntityResponse(){
		return Response.noContent().entity("Entity successfuly deleted").build();
	}
	
	public Integer getLimit(Integer limit) {
		return limit==null? PAGE_LIMIT: limit;
	}

	public Integer getOffset(Integer offset) {
		return offset==null? 0: offset;
	}

	
	public Response getAll(UriInfo uriInfo, Integer offset,Integer limit){return null;}
	
	public Response getById(UriInfo uriInfo,String entityRefId){return null;}
	
	public Response create(UriInfo uriInfo, T entity){return null;}
	
	public Response update(UriInfo uriInfo, String entityRefId, T entity){return null;}
	
	public Response delete(String entityRefId){return null;}
}
