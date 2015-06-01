package com.icpak.rest.dao;

import java.util.List;

import com.icpak.rest.exceptions.ServiceException;
import com.icpak.rest.models.ErrorCodes;
import com.icpak.rest.models.membership.ApplicationFormHeader;
import com.icpak.rest.models.membership.ApplicationType;
import com.icpak.rest.models.membership.Category;

public class ApplicationFormDao extends BaseDao{

	 public void createApplication(ApplicationFormHeader application) {
	        save( application );
	    }

	    public List<ApplicationFormHeader> getAllApplications(Integer offSet, Integer limit) {
	    	return getResultList(getEntityManager().createQuery("select u from ApplicationFormHeader u"
	    			+ " where u.isActive=1")
	    			, offSet, limit);
	    }

	    public void updateApplication(ApplicationFormHeader application) {
	        createApplication(application);
	    }

		public int getApplicationCount() {
			return getApplicationCount(null);
		}

		public int getApplicationCount(String roleId) {
			
			Number number = null;
			if(roleId==null){
				number = getSingleResultOrNull(getEntityManager().createNativeQuery("select count(*) from `Application Form Header` "
						+ "where isactive=1"));
			}else{
				number = getSingleResultOrNull(getEntityManager().createNativeQuery("select count(*) from `Application Form Header` u "
						+ "where u.isactive=1"));
			}
			
			return number.intValue();
		}

		public ApplicationFormHeader findByApplicationId(String refId) {
			return findByApplicationId(refId, true);
		}
		public ApplicationFormHeader findByApplicationId(String refId, boolean throwExceptionIfNull) {
			ApplicationFormHeader application = getSingleResultOrNull(
					getEntityManager().createQuery("from ApplicationFormHeader u where u.refId=:refId")
					.setParameter("refId", refId));
			
			if(application==null && throwExceptionIfNull){
				throw new ServiceException(ErrorCodes.NOTFOUND,"ApplicationFormHeader", "'"+refId+"'");
			}
			
			return application;
		}

		public Category findApplicationCategory(ApplicationType type) {
			return getSingleResultOrNull(getEntityManager().createQuery("select u from Category u"
	    			+ " where u.isActive=1 and u.type=:type").setParameter("type", type));
		}

}
