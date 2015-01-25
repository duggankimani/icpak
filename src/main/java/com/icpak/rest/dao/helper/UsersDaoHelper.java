package com.icpak.rest.dao.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.icpak.rest.BaseResource;
import com.icpak.rest.IDUtils;
import com.icpak.rest.dao.RolesDao;
import com.icpak.rest.dao.UsersDao;
import com.icpak.rest.exceptions.ServiceException;
import com.icpak.rest.models.ErrorCodes;
import com.icpak.rest.models.auth.Role;
import com.icpak.rest.models.auth.User;
import com.icpak.rest.models.base.ExpandTokens;
import com.icpak.rest.models.base.ResourceCollectionModel;
import com.icpak.rest.models.base.ResourceModel;

@Transactional
public class UsersDaoHelper {

	@Inject UsersDao dao;
	@Inject RolesDao roleDao;
	
	public void add(User user){
		user.setRefId(IDUtils.generateId());
		dao.createUser(user);
		assert user.getId()!=null;
	}
	
	public void update(String userId, User user){
		User po = dao.findByUserId(userId);
		
		po.setEmail(user.getEmail());
		po.setUsername(user.getUsername());
		po.setPassword(user.getPassword());
//		po.setFirstName(user.getFirstName());
//		po.setLastName(user.getLastName());
		
//		if(user.getMember()!=null){
//			Member poMember = po.getMember();
//			if(poMember==null){
//				poMember = new Member();
//			}
//			poMember.setStatus(user.getMember().getStatus());
//		}
		
		dao.updateUser(po);
		user.setPassword(po.getPassword());
	}
	
	public void delete(String userId){
		User user = dao.findByUserId(userId);
		dao.delete(user);
	}
	public ResourceCollectionModel<User> getAllUsers(Integer offset, Integer limit,
			UriInfo uriInfo) {
		
		return getAllUsers(offset, limit, uriInfo, null);
	}
	
	public ResourceCollectionModel<User> getAllUsers(Integer offSet, Integer limit, UriInfo uriInfo, String roleId){
		int total = dao.getUserCount(roleId);
		Role role = null;
		if(roleId!=null){
			role = roleDao.getByRoleId(roleId);
		}
		
		ResourceCollectionModel<User> collection = new ResourceCollectionModel<>(offSet,limit,total, uriInfo);
		List<User> members = dao.getAllUsers(offSet, limit, role);
		
		List<User> rtn = new ArrayList<>();
		for(User user: members){
			user.setUri(uriInfo.getAbsolutePath().toString()+"/"+user.getRefId());
			rtn.add(user.clone(ExpandTokens.DETAIL.toString()));
		}
		
		collection.setItems(rtn);
		return collection;
	}
	
	public ResourceModel getAllUsersByRoleId(Integer offSet, Integer limit, UriInfo uriInfo, String roleId,
			String...expand){
		if(offSet==null) offSet=0;
		if(limit==null) limit = BaseResource.PAGE_LIMIT;
		
		Role role = roleDao.getByRoleId(roleId);
		if(role==null){
			throw new ServiceException(ErrorCodes.NOTFOUND, "Role", "'"+roleId+"'");
		}
		
		//int total = dao.getUserCount(roleId);
		//ResourceCollectionModel<User> collection = new ResourceCollectionModel<>(offSet,limit,total, uriInfo);
		Role clone = role.clone(expand);
		clone.setUri(uriInfo.getBaseUri().toString()+"/roles/"+roleId);
		Collection<User> members = clone.getUsers();
		
		for(User user: members){
			user.setUri(uriInfo.getBaseUri()+"/users/"+user.getRefId());
		}
		//clone.setUsers(members);
		
		//collection.setItems(members);
		return clone;
	}

	public User getUser(String userId) {
		User user = dao.findByUserId(userId);
		if(user==null){
			throw new ServiceException(ErrorCodes.NOTFOUND,"'"+userId+"'");
		}
		
		return user;
	}

}
