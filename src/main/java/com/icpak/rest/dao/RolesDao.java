package com.icpak.rest.dao;

import java.util.List;

import com.icpak.rest.models.base.Permission;
import com.icpak.rest.models.base.Role;
import com.icpak.rest.models.base.User;

public class RolesDao extends BaseDao {
	
	public Role getByRoleId(String roleId) {
		return getSingleResultOrNull(getEntityManager().createQuery(
				"from Role u where u.roleId=:roleId").setParameter("roleId",
				roleId));
	}

	public void createRole(Role role) {
		save(role);
	}

	public List<Role> getAllRoles(Integer offSet, Integer limit) {
		return getResultList(getEntityManager().createQuery("from Role where isActive=1 order by name"),
				offSet, limit);
	}

	public void updateRole(Role role) {
		createRole(role);
	}

	public int getRoleCount() {
		Number number = getSingleResultOrNull(getEntityManager()
				.createNativeQuery("select count(*) from role where isactive=1"));

		return number.intValue();
	}

	public void deletePermission(String roleId, Permission permission) {
		Role role = getByRoleId(roleId);
		assert role!=null;
		
		getEntityManager().createNativeQuery("delete from role_permission where role_Id=:role_Id and permissions=:permission")
		.setParameter("role_Id", role.getId())
		.setParameter("permission", permission.name())
		.executeUpdate();
		
		
	}

	public boolean checkAssigned(Role role, User user) {
		
		Number count = getSingleResultOrNull(getEntityManager().createNativeQuery("select count(*) from user_role"
				+ " where userid=:userId and roleid=:roleId")
				.setParameter("userId", user.getId())
				.setParameter("roleId", role.getId()));
		
		return count.intValue()==1;
	}

}
