package com.icpak.dao.test;

import java.util.Arrays;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Test;

import com.google.inject.Inject;
import com.icpak.dao.testbase.AbstractDaoTest;
import com.icpak.rest.dao.helper.PermissionsDaoHelper;
import com.icpak.rest.dao.helper.RolesDaoHelper;
import com.icpak.rest.dao.helper.UsersDaoHelper;
import com.icpak.rest.models.auth.Gender;
import com.icpak.rest.models.auth.Permission;
import com.icpak.rest.models.auth.Role;
import com.icpak.rest.models.auth.User;
import com.icpak.rest.models.auth.UserData;
import com.icpak.rest.models.base.ResourceCollectionModel;

public class TestUserDao extends AbstractDaoTest{

	@Inject UsersDaoHelper userHelper;
	@Inject RolesDaoHelper roleHelper;
	@Inject PermissionsDaoHelper permissionHelper;
	
	String userId1;
	String userId2;
	
	@Test
	public void testCrud(){
		createUser();
		retrieveUsers();
		getById();
		assignRoles();
		delete();
		retrieveUsersAfterDelete();
	}
	
	public void createUser(){
		User user = new User();
		user.setEmail("mimi@test.org");
		user.setUsername("Mimi");
		user.setPassword("passwd");
		UserData data = new UserData();
		data.setFirstName("Mimi");
		data.setLastName("Testing");
		data.setGender(Gender.FEMALE);
		data.setSalutation(new HashSet<String>(Arrays.asList("DR","Mrs","Hon")));
		user.setUserData(data);
		userHelper.add(user);
		
		userId1 = user.getRefId();
		
		user = new User();
		user.setEmail("wewe@test.org");
		user.setUsername("Wewe");
		user.setPassword("passwd");
		data = new UserData();
		data.setFirstName("Mimi");
		data.setLastName("Testing");
		data.setGender(Gender.FEMALE);
		data.setSalutation(new HashSet<String>(Arrays.asList("DR","Mrs","Hon")));
		user.setUserData(data);
		userHelper.add(user);
		userId2 = user.getRefId();
		
	}
	
	public void retrieveUsers(){
		ResourceCollectionModel<User> Users =  userHelper.getAllUsers(0, 10,uriInfo);
		Assert.assertSame(Users.getTotal(), 2);
	}
	
	public void getById(){
		User role = userHelper.getUser(userId1);
		Assert.assertNotNull(role);
		
		role = userHelper.getUser(userId2);
		Assert.assertNotNull(role);
	}
	
	public void assignRoles(){
		String roleId1;
		String roleId2;
		Role role = new Role("ADMIN_ROLE");
		role.setDescription("Can Create Role");
		roleHelper.createRole(role);
		roleId1 = role.getRefId();
		
		role = new Role("GUEST_ROLE");
		role.setDescription("Guest");
		roleHelper.createRole(role);
		roleId2 = role.getRefId();
		
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
		
		Assert.assertNotNull(role.getPermissions());
		Assert.assertSame(role.getPermissions().size(), 2);
		
		Assert.assertNotNull(role.getPermissions());
		Assert.assertSame(role.getPermissions().size(), 2);
		
		//Assign user to role
		
		roleHelper.assign(roleId1, userId1);
		roleHelper.assign(roleId1, userId2);
		roleHelper.assign(roleId2, userId1);
		roleHelper.assign(roleId2, userId2);
	
		int count = userHelper.getUser(userId1).getRoles().size();
		int count2 = userHelper.getUser(userId2).getRoles().size();
		Assert.assertSame(count, 2);
		Assert.assertSame(count2, 2);
		
		//Clean up
		roleHelper.deleteRole(roleId1);
		roleHelper.deleteRole(roleId2);
		
		//Clean up
		permissionHelper.deletePermission(perm1);
		permissionHelper.deletePermission(perm2);
		
	}
	
	public void delete(){
		
		userHelper.delete(userId1);
		userHelper.delete(userId2);
	}
	
	public void retrieveUsersAfterDelete(){
		ResourceCollectionModel<User> Users =  userHelper.getAllUsers(0, 10, uriInfo);
		Assert.assertSame(Users.getTotal(), 0);
	}
}
