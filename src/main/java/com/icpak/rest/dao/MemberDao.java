package com.icpak.rest.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.icpak.rest.exceptions.ServiceException;
import com.icpak.rest.models.ErrorCodes;
import com.icpak.rest.models.membership.Education;
import com.icpak.rest.models.membership.Member;

/**
 * 
 * @author duggan
 *
 */
public class MemberDao extends BaseDao{

    public void createMember(Member member) {
        save( member );
    }

    public List<Member> getAllMembers(Integer offSet, Integer limit) {
    	return getResultList(getEntityManager().createQuery("select u from Member u"
    			+ " where u.isActive=1")
    			, offSet, limit);
    }

    public void updateMember(Member member) {
        createMember(member);
    }

	public int getMemberCount() {
		return getMemberCount(null);
	}

	public int getMemberCount(String roleId) {
		
		Number number = null;
		if(roleId==null){
			number = getSingleResultOrNull(getEntityManager().createNativeQuery("select count(*) from member "
					+ "where isactive=1"));
		}else{
			number = getSingleResultOrNull(getEntityManager().createNativeQuery("select count(*) from member u "
					+ "where u.isactive=1"));
		}
		
		return number.intValue();
	}

	public Member findByMemberId(String refId) {
		return findByMemberId(refId, true);
	}
	public Member findByMemberId(String refId, boolean throwExceptionIfNull) {
		Member member = getSingleResultOrNull(
				getEntityManager().createQuery("from Member u where u.refId=:refId")
				.setParameter("refId", refId));
		
		if(member==null && throwExceptionIfNull){
			throw new ServiceException(ErrorCodes.NOTFOUND,"Member", "'"+refId+"'");
		}
		
		return member;
	}

	public <T> T findByRefId(String refId, Class<?> clazz){
		return findByRefId(refId, clazz, new HashMap<String, Object>(), true);
	}
	
	public <T> T findByRefId(String refId, Class<?> clazz, Map<String, Object> params, boolean throwExceptionIfNull) {
		
		StringBuffer buff = new StringBuffer("from "+clazz.getName()+" c where c.refId=:refId");
		
		//Variables
		if(params!=null){
			for(String key: params.keySet()){
				buff.append(" and "+key+"=:"+key);
			}
		}
		Query query = getEntityManager().createQuery(buff.toString())
				.setParameter("refId", refId);
		//Params
		if(params!=null){
			for(String key: params.keySet()){
				query.setParameter(key, params.get(key));
			}
		}
		
		T rtn = getSingleResultOrNull(query);
		if(rtn==null && throwExceptionIfNull){
			throw new ServiceException(ErrorCodes.NOTFOUND,clazz.getName(), "'"+refId+"'");
		}
		
		return rtn;
	}

}
