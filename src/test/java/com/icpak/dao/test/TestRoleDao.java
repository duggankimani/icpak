package com.icpak.dao.test;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Ignore;

import com.google.inject.Inject;
import com.icpak.dao.testbase.AbstractDaoTest;
import com.icpak.rest.dao.helper.PermissionsDaoHelper;
import com.icpak.rest.dao.helper.RolesDaoHelper;
import com.icpak.rest.models.auth.Permission;
import com.icpak.rest.models.auth.Role;
import com.icpak.rest.models.base.ResourceCollectionModel;

public class TestRoleDao extends AbstractDaoTest{

	@Inject RolesDaoHelper helper;
	@Inject PermissionsDaoHelper permissionHelper;
	
	String roleId1;
	String roleId2;
	
	@Ignore
	public void testCrud(){
		createRole();
		retrieveRoles();
		getById();
		assignPermissions();
		delete();
		retrieveRolesAfterDelete();
	}
	
	public void createRole(){
		Role role = new Role("ADMIN_ROLE");
		role.setDescription("Can Create Role");
		helper.createRole(role);
		roleId1 = role.getRefId();
		
		role = new Role("GUEST_ROLE");
		role.setDescription("Guest");
		helper.createRole(role);
		roleId2 = role.getRefId();
	}
	
	public void retrieveRoles(){
		ResourceCollectionModel<Role> Roles =  helper.getAllRoles(uriInfo, 0, 10);
		Assert.assertSame(Roles.getTotal(), 2);
	}
	
	public void getById(){
		Role role = helper.getRoleById(roleId1);
		Assert.assertNotNull(role);
		
		role = helper.getRoleById(roleId2);
		Assert.assertNotNull(role);
	}
	
	public void assignPermissions(){
		Permission permission = new Permission();
		permission.setName("CAN_CREATE_PERMISSION");
		permission.setDescription("Can Create Permission");
		permissionHelper.createPermission(permission);
		String perm1 = permission.getRefId();
		permissionHelper.setPermission(permission.getRefId(), Arrays.asList(roleId1,roleId2));
		
		permission = new Permission();
		permission.setName("CAN_CREATE_USER");
		permission.setDescription("Can Create User");
		permissionHelper.createPermission(permission);
		String perm2 = permission.getRefId();
		permissionHelper.setPermission(permission.getRefId(), Arrays.asList(roleId1,roleId2));
		
		Role role = helper.getRoleById(roleId1);
		Assert.assertNotNull(role.getPermissions());
		Assert.assertSame(role.getPermissions().size(), 2);
		
		role = helper.getRoleById(roleId2);
		Assert.assertNotNull(role.getPermissions());
		Assert.assertSame(role.getPermissions().size(), 2);
		
		permissionHelper.deletePermission(perm1);
		permissionHelper.deletePermission(perm2);
		
	}
	
	public void delete(){
		
		helper.deleteRole(roleId1);
		helper.deleteRole(roleId2);
	}
	
	public void retrieveRolesAfterDelete(){
		ResourceCollectionModel<Role> Roles =  helper.getAllRoles(uriInfo, 0, 10);
		Assert.assertSame(Roles.getTotal(), 0);
	}
}
