package com.icpak.rest.dao.helper;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import com.google.inject.Inject;
import com.icpak.rest.IDUtils;
import com.icpak.rest.dao.PracticeDao;
import com.icpak.rest.exceptions.ServiceException;
import com.icpak.rest.models.ErrorCodes;
import com.icpak.rest.models.base.ExpandTokens;
import com.icpak.rest.models.base.ResourceCollectionModel;
import com.icpak.rest.models.membership.Practice;

public class PracticeDaoHelper {


	@Inject PracticeDao dao;
	
	public void createPractice(Practice practice){
		practice.setRefId(IDUtils.generateId());
		dao.createPractice(practice);
		assert practice.getId()!=null;
	}
	
	public void updatePractice(String practiceId, Practice practice){
		Practice po = dao.findByPracticeId(practiceId);
	
		dao.updatePractice(po);
	}
	
	public void deletePractice(String practiceId){
		Practice practice = dao.findByPracticeId(practiceId);
		dao.delete(practice);
	}
	public ResourceCollectionModel<Practice> getAllPractices(Integer offset, Integer limit,
			UriInfo uriInfo) {
		
		int total = dao.getPracticeCount();
		
		ResourceCollectionModel<Practice> collection = new ResourceCollectionModel<>(offset,limit,total, uriInfo);
		List<Practice> members = dao.getAllPractices(offset, limit);
		
		List<Practice> rtn = new ArrayList<>();
		for(Practice practice: members){
			practice.setUri(uriInfo.getAbsolutePath().toString()+"/"+practice.getRefId());
			rtn.add(practice.clone(ExpandTokens.DETAIL.toString()));
		}
		
		collection.setItems(rtn);
		return collection;
	}

	public Practice getPractice(String practiceId) {
		Practice practice = dao.findByPracticeId(practiceId);
		if(practice==null){
			throw new ServiceException(ErrorCodes.NOTFOUND,"'"+practiceId+"'");
		}
		
		return practice;
	}

}
