package com.icpak.rest.dao.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.icpak.rest.dao.MemberDao;
import com.icpak.rest.dao.UsersDao;
import com.icpak.rest.models.base.ResourceCollectionModel;
import com.icpak.rest.models.membership.Education;
import com.icpak.rest.models.membership.Member;

@Transactional
public class EducationDaoHelper {

	@Inject UsersDao userDao;
	@Inject MemberDao dao;
	
	public ResourceCollectionModel<Education> getAllEducationEntrys(UriInfo uriInfo, 
			String memberId,Integer offset,	Integer limit) {

		Member member = dao.findByMemberId(memberId);
		int size = member.getEducation().size();
		ResourceCollectionModel<Education> educationEntries = new ResourceCollectionModel<>(offset,limit,
				size,uriInfo);
		Collection<Education> list = member.getEducation();
		
		List<Education> clones = new ArrayList<>();
		for(Education eduEntry: list){
			Education clone = eduEntry.clone();
			clone.setUri(uriInfo.getAbsolutePath()+"/"+clone.getRefId());
			clone.setMemberId(memberId);
			clones.add(clone);
		}
		
		educationEntries.setItems(clones);
		return educationEntries;
	}

	public Education getEducationEntryById(String memberId,String eduEntryId) {

		Education eduEntry = dao.findByRefId(eduEntryId, Education.class);
		return eduEntry.clone();
	}
	
	public Education createEducationEntry(String memberId,Education eduEntry) {
		assert eduEntry.getRefId()==null;
		Member member = dao.findByMemberId(memberId);
		eduEntry.setMember(member);
		dao.save(eduEntry);		
		
		return eduEntry.clone();
	}

	public Education updateEducationEntry(String memberId,String eduEntryId, Education eduEntry) {
		assert eduEntry.getRefId()!=null;
		
		Education poEducationEntry = dao.findByRefId(eduEntryId, Education.class);
		poEducationEntry.copyFrom(eduEntry);
		
		dao.save(eduEntry);
		return poEducationEntry.clone();
	}

	public void deleteEducationEntry(String memberId, String eduEntryId) {
		Education eduEntry = dao.findByRefId(eduEntryId, Education.class);
		dao.delete(eduEntry);
	}

}
