package com.icpak.rest.dao.helper;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.icpak.rest.IDUtils;
import com.icpak.rest.dao.MemberDao;
import com.icpak.rest.dao.RolesDao;
import com.icpak.rest.exceptions.ServiceException;
import com.icpak.rest.models.ErrorCodes;
import com.icpak.rest.models.auth.User;
import com.icpak.rest.models.auth.UserData;
import com.icpak.rest.models.base.ExpandTokens;
import com.icpak.rest.models.base.ResourceCollectionModel;
import com.icpak.rest.models.membership.Member;

@Transactional
public class MemberDaoHelper {
	
	@Inject MemberDao memberDao;
	@Inject RolesDao roleDao;
	
	public void createMember(Member member){
		if(member.getRefId()!=null){
			updateMember(member.getRefId(), member);
			return;
		}
		
		memberDao.createMember(member);
		assert member.getId()!=null;
	}
	
	public void updateMember(String memberId, Member member){
		Member po = memberDao.findByMemberId(memberId,true);
		po.setStatus(member.getStatus());
		po.setHasConvictions(member.getHasConvictions());
		
		User user = member.getUser();
		if(user.getRefId()==null && po.getUser()==null){
			user.setRefId(IDUtils.generateId());
		}
		po.setUser(user);
		
		UserData udata = member.getUserData();
		if(udata.getRefId()==null && po.getUserData()==null){
			udata.setRefId(IDUtils.generateId());
		}
		po.setUserData(udata);
		
		memberDao.updateMember(po);
	}
	
	public void deleteMember(String memberId){
		Member member = memberDao.findByMemberId(memberId);
		memberDao.delete(member);
	}
	public ResourceCollectionModel<Member> getAllMembers(Integer offset, Integer limit,
			UriInfo uriInfo) {
		int total = memberDao.getMemberCount();
		
		ResourceCollectionModel<Member> collection = new ResourceCollectionModel<>(offset,limit,total, uriInfo);
		List<Member> members = memberDao.getAllMembers(offset, limit);
		
		List<Member> rtn = new ArrayList<>();
		for(Member member: members){
			member.setUri(uriInfo.getAbsolutePath().toString()+"/"+member.getRefId());
			rtn.add(member.clone(ExpandTokens.DETAIL.toString()));
		}
		
		collection.setItems(rtn);
		return collection;
	}
	
	public Member getMemberById(String memberId) {
		Member member = memberDao.findByMemberId(memberId);
		if(member==null){
			throw new ServiceException(ErrorCodes.NOTFOUND,"'"+memberId+"'");
		}
		
		return member;
	}

	
}
