package com.icpak.dao.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.inject.Inject;
import com.icpak.dao.testbase.AbstractDaoTest;
import com.icpak.rest.dao.helper.PermissionsDaoHelper;
import com.icpak.rest.models.auth.Permission;
import com.icpak.rest.models.base.ResourceCollectionModel;

public class TestPermissionDao extends AbstractDaoTest{

	@Inject PermissionsDaoHelper helper;
	
	String permissionId1;
	String permissionId2;
	
	@Test
	public void testCrud(){
		createPermission();
		retrievePermissions();
		getById();
		delete();
		retrievePermissionsAfterDelete();
	}
	
	public void createPermission(){
		Permission permission = new Permission();
		permission.setName("CAN_CREATE_PERMISSION");
		permission.setDescription("Can Create Permission");
		helper.createPermission(permission);
		permissionId1 = permission.getRefId();
		
		permission = new Permission();
		permission.setName("CAN_CREATE_USER");
		permission.setDescription("Can Create User");
		helper.createPermission(permission);
		permissionId2 = permission.getRefId();
	}
	
	public void retrievePermissions(){
		ResourceCollectionModel<Permission> permissions =  helper.getAllPermissions(uriInfo, 0, 10);
		Assert.assertSame(permissions.getTotal(), 2);
	}
	
	public void getById(){
		Permission permission = helper.getPermissionById(permissionId1);
		Assert.assertNotNull(permission);
		
		permission = helper.getPermissionById(permissionId2);
		Assert.assertNotNull(permission);
	}
	
	public void delete(){
		
		helper.deletePermission(permissionId1);
		helper.deletePermission(permissionId2);
	}
	
	public void retrievePermissionsAfterDelete(){
		ResourceCollectionModel<Permission> permissions =  helper.getAllPermissions(uriInfo, 0, 10);
		System.err.println(permissions.getItems());
		Assert.assertSame(permissions.getTotal(), 0);
	}
	
}
