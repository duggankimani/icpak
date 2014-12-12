package com.icpak.rest.dao;

import java.util.List;

import org.apache.shiro.crypto.hash.Sha256Hash;

import com.icpak.rest.exceptions.ServiceException;
import com.icpak.rest.models.ErrorCodes;
import com.icpak.rest.models.base.Role;
import com.icpak.rest.models.base.User;

public class UsersDao extends BaseDao{

    public User findUser(String username) {
        assert username!=null;
        String query = "from User u where u.username = :username";
        return getSingleResultOrNull(getEntityManager().createQuery(query)
        		.setParameter("username", username));
    }

    public void createUser(User user) {
    	user.setPassword(new Sha256Hash(user.getPassword()).toHex());
        save( user );
    }

    public List<User> getAllUsers(Integer offSet, Integer limit, Role role) {
    	if(role==null){
    		return getResultList(getEntityManager().createQuery("from User where isActive=1 order by username"), offSet, limit);
    	}
      //  getEntityManager().createQuery("").setFirstResult(offSet).setMaxResults(maxResult)
        
//    	return getResultList(getEntityManager().createQuery("from User u"
//    			+ " where :role in (u.roles) "
//    			+ " and u.isActive=1"
//    			+ " order by username")
//    			.setParameter("role", role)
//    			, offSet, limit);
    	
    	return getResultList(getEntityManager().createQuery("select u from User u"
    			+ " inner join u.roles roles "
    			+ " where roles=:role "
    			+ " and u.isActive=1"
    			+ " order by username")
    			.setParameter("role", role)
    			, offSet, limit);
    	
    	
    }

    public void updateUser(User user) {
        createUser(user);
    }

	public int getUserCount() {
		return getUserCount(null);
	}
	

	public int getUserCount(String roleId) {
		
		Number number = null;
		if(roleId==null){
			number = getSingleResultOrNull(getEntityManager().createNativeQuery("select count(*) from user where isactive=1"));
		}else{
			number = getSingleResultOrNull(getEntityManager().createNativeQuery("select count(*) from user u "
					+ "inner join user_role ur on (ur.userid=u.id) "
					+ "inner join role r on (ur.roleid=r.id)"
					+ "where u.isactive=1 and u.isactive=1"));
		}
		
		return number.intValue();
	}

	public User findByUserId(String userId) {
		return findByUserId(userId, true);
	}
	public User findByUserId(String userId, boolean throwExceptionIfNull) {
		User user = getSingleResultOrNull(
				getEntityManager().createQuery("from User u where u.userId=:userId")
				.setParameter("userId", userId));
		
		if(user==null && throwExceptionIfNull){
			throw new ServiceException(ErrorCodes.NOTFOUND,"Member", "'"+userId+"'");
		}
		
		return user;
	}
}
