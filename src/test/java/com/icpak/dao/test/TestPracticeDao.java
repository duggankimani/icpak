package com.icpak.dao.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Inject;
import com.icpak.dao.testbase.AbstractDaoTest;
import com.icpak.rest.dao.helper.MemberDaoHelper;
import com.icpak.rest.dao.helper.PracticeDaoHelper;
import com.icpak.rest.models.base.ResourceCollectionModel;
import com.icpak.rest.models.membership.Client;
import com.icpak.rest.models.membership.IndustrySector;
import com.icpak.rest.models.membership.Member;
import com.icpak.rest.models.membership.MemberType;
import com.icpak.rest.models.membership.MembershipStatus;
import com.icpak.rest.models.membership.Practice;
import com.icpak.rest.models.membership.PracticeStyle;

public class TestPracticeDao extends AbstractDaoTest{

	@Inject PracticeDaoHelper practiceHelper;
	@Inject MemberDaoHelper memberHelper;
	
	String memberId;
	
	String practiceId1;
	String practiceId2;
	
	@Ignore
	public void testCrud(){
		createPractice();
		updatePractice();
		retrievePractices();
		getById();
		delete();
		retrievePracticesAfterDelete();
	}
	
	public void createPractice(){
		Practice practice = new Practice();
		practice.setFullTime(true);
		practice.setName("Hello");
		practice.setOtherServices("kinyozi");
		practice.setOtherServicesProvided(true);
		practice.setPin("SD343");
		practice.setPracticeCategories(Arrays.asList("TAX", "AUDIT", "ACCOUNTING"));
		practice.setPracticeCommenceDate(new Date());
		practice.setPropTime("50%");
		practice.setRegistrationDate(new Date());
		practice.setType(PracticeStyle.PARTNERSHIP);
		practiceHelper.createPractice(practice);
		practiceId1 = practice.getRefId();
		
		practice = new Practice();
		practice.setFullTime(true);
		practice.setName("GGG");
		practice.setOtherServices("kinyozi");
		practice.setOtherServicesProvided(true);
		practice.setPin("32343");
		practice.setPracticeCategories(Arrays.asList("TAX", "AUDIT", "ACCOUNTING"));
		practice.setPracticeCommenceDate(new Date());
		practice.setPropTime("50%");
		practice.setRegistrationDate(new Date());
		practice.setType(PracticeStyle.SOLE);
		practiceHelper.createPractice(practice);
		practiceId2 = practice.getRefId();		
	}
	
	public void updatePractice(){
		Member member = new Member();
		member.setMemberType(MemberType.MEMBER);
		member.setPin("AA23W44");
		member.setStatus(MembershipStatus.APPLICANT);
		member.setHasConvictions(false);
		memberHelper.createMember(member);
		memberId = member.getRefId();
		
		Practice practice = new Practice();
		
		//Clients
		List<Client> list= new ArrayList<>(); 
		Client client = new Client();
		client.setCount(1);
		client.setSector(IndustrySector.BANKING);
		list.add(client);

		client = new Client();
		client.setCount(2);
		client.setSector(IndustrySector.PUBLIC);
		list.add(client);
		
		practice.setClients(list);
		practice.setFullTime(true);
		practice.setRefId(practiceId1);
		practice.setName("Hello");
		practice.setOtherServices("kinyozi");
		practice.setOtherServicesProvided(true);
		practice.setPin("SD343");
		practice.setPracticeCategories(Arrays.asList("TAX", "AUDIT", "ACCOUNTING"));
		practice.setPracticeCommenceDate(new Date());
		practice.setRegistrationDate(new Date());
		practice.setType(PracticeStyle.PARTNERSHIP);
		practiceHelper.updatePractice(practiceId1,practice);
		
	}
	
	public void retrievePractices(){
		ResourceCollectionModel<Practice> practices =  practiceHelper.getAllPractices(0, 10, uriInfo);
		Assert.assertSame(practices.getTotal(), 2);
	}
	
	public void getById(){
		Practice practice = practiceHelper.getPracticeById(practiceId1);
		Assert.assertNotNull(practice);
		
		practice = practiceHelper.getPracticeById(practiceId2);
		Assert.assertNotNull(practice);
	}
	
	public void delete(){
		practiceHelper.deletePractice(practiceId1);
		practiceHelper.deletePractice(practiceId2);
		memberHelper.deleteMember(memberId);
	}
	
	public void retrievePracticesAfterDelete(){
		ResourceCollectionModel<Practice> practices =  practiceHelper.getAllPractices(0, 10, uriInfo);
		Assert.assertSame(practices.getTotal(), 0);
	}

}
