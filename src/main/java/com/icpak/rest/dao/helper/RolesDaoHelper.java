package com.icpak.rest.dao.helper;

import javax.ws.rs.core.UriInfo;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.icpak.rest.IDUtils;
import com.icpak.rest.dao.RolesDao;
import com.icpak.rest.dao.UsersDao;
import com.icpak.rest.exceptions.ServiceException;
import com.icpak.rest.models.ErrorCodes;
import com.icpak.rest.models.base.Permission;
import com.icpak.rest.models.base.ResourceCollectionModel;
import com.icpak.rest.models.base.Role;
import com.icpak.rest.models.base.RoleUser;
import com.icpak.rest.models.base.User;

@Transactional
public class RolesDaoHelper {

	@Inject RolesDao dao;
	@Inject UsersDaoHelper userDaoHelper;
	@Inject UsersDao userDao;
	
	public ResourceCollectionModel<Role> getAllRoles(UriInfo uriInfo, Integer offset,
			Integer limit) {

		int count = dao.getRoleCount();
		ResourceCollectionModel<Role> roles = new ResourceCollectionModel<>(offset,limit,count ,uriInfo);
		roles.setItems(dao.getAllRoles(offset, limit));
		
		return roles;
	}

	public Role getRoleById(String roleId) {

		Role role = dao.getByRoleId(roleId);
		if(role==null){
			throw new ServiceException(ErrorCodes.NOTFOUND, "Role", "'"+roleId+"'");
		}
		return role;
	}
	
	public Role getRoleById(String roleId, String...expand) {

		Role role = getRoleById(roleId);
		
		return role.clone(expand);
	}

	
	public void createRole(Role role) {
		assert role.getRoleId()==null;
		
		role.setRoleId(IDUtils.generateId());
		if(role.getPermissions()!=null)
			for(String permission:role.getPermissions()){
				//Validate the permissions provided
				//Permission Must exist to be assigned
				Permission perm = Permission.get(permission);
			}
		dao.save(role);
		
		assert role.getId()!=null;
	}

	public Role updateRole(String roleId, Role role) {
		assert role.getRoleId()!=null;
		
		Role poRole = getRoleById(roleId);
		
		poRole.setDescription(role.getDescription());
		poRole.setName(role.getName());
		
		if(role.getPermissions()!=null)
		for(String permission:role.getPermissions()){
			poRole.addPermission(Permission.get(permission));
		}
		
		poRole.setRoleId(roleId);
		
		dao.save(poRole);
		return poRole;
	}

	public void deleteRole(String roleId) {
		Role role = getRoleById(roleId);
		dao.delete(role);
	}

	public RoleUser assign(String roleId, String userId) {
		Role role = getRoleById(roleId);
		User user = userDaoHelper.getUser(userId); 
		user.addRole(role);
		userDao.save(user);
		
		RoleUser roleUser = new RoleUser(user.clone(), role.clone());
		return roleUser;
	}
	
	public RoleUser getRoleUser(String roleId, String userId) {
		Role role = getRoleById(roleId);
		User user = userDaoHelper.getUser(userId); 
		boolean isAssigned = dao.checkAssigned(role, user);
		
		if(!isAssigned){
			throw new ServiceException(ErrorCodes.ILLEGAL_ARGUMENT,"User "+user.getUsername()
					+" is not assigned role"+role.getName(),":Get Request");
		}
		
		RoleUser roleUser = new RoleUser(user.clone(), role.clone());
		return roleUser;
	}

	public void deleteAssignment(String roleId, String userId) {
		Role role = getRoleById(roleId);
		User user = userDaoHelper.getUser(userId);
		
		user.removeRole(role);
		userDao.save(user);
	}

	public Role setPermission(String roleId, String permissionName) {
		
		Role role = dao.getByRoleId(roleId);
		Permission permission = Permission.get(permissionName.toUpperCase());
		role.addPermission(permission);
		
		dao.save(role);
		
		return role;
	}

	public void deletePermission(String roleId, String permissionName) {
		//Role role = dao.getByRoleId(roleId);
		Permission permission = Permission.get(permissionName.toUpperCase());
		dao.deletePermission(roleId, permission);
	}	

}
