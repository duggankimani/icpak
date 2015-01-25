package com.icpak.dao.test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Test;

import com.google.inject.Inject;
import com.icpak.dao.testbase.AbstractDaoTest;
import com.icpak.rest.dao.helper.ApplicationDaoHelper;
import com.icpak.rest.dao.helper.MemberDaoHelper;
import com.icpak.rest.models.auth.Gender;
import com.icpak.rest.models.auth.User;
import com.icpak.rest.models.auth.UserData;
import com.icpak.rest.models.base.ResourceCollectionModel;
import com.icpak.rest.models.membership.Application;
import com.icpak.rest.models.membership.ApplicationStatus;
import com.icpak.rest.models.membership.ApplicationType;
import com.icpak.rest.models.membership.IndustrySector;
import com.icpak.rest.models.membership.Member;
import com.icpak.rest.models.membership.MemberType;
import com.icpak.rest.models.membership.MembershipStatus;

public class TestApplicationDao extends AbstractDaoTest{

	@Inject MemberDaoHelper memberHelper;
	@Inject ApplicationDaoHelper applicationHelper;
	
	String memberId;
	String applicationId1;
	String applicationId2;
	
	@Test
	public void testCrud(){
		createApplication();
		createMember();
		updateApplication();
		retrieveApplications();
		getById();
		delete();
		retrieveApplicationsAfterDelete();
	}
	
	public void createApplication(){
		Application application = new Application();
		application.setStatus(ApplicationStatus.DRAFT);
		application.setApplicationType(ApplicationType.GENERAL_MEMBER);
		application.setApprovalMinNo("0");
		application.setAuditDetails(null);
		application.setEmplSector(IndustrySector.BANKING);
		application.setFileNo("FILE44");
		application.setGazetteNoticeNo("Gazette#5663");
		application.setLicenceCollectedOrDispatched(null);
		application.setSubmissionDate(new Date());
		applicationHelper.createApplication(application);
		applicationId1 = application.getRefId();
		
		application = new Application();
		application.setStatus(ApplicationStatus.DRAFT);
		application.setApplicationType(ApplicationType.GENERAL_MEMBER);
		application.setApprovalMinNo("0");
		application.setAuditDetails(null);
		application.setEmplSector(IndustrySector.BANKING);
		application.setFileNo("FILE44");
		application.setGazetteNoticeNo("Gazette#5663");
		application.setLicenceCollectedOrDispatched(null);
		application.setSubmissionDate(new Date());
		applicationHelper.createApplication(application);
		applicationId2 = application.getRefId();
	}
	
	public void updateApplication(){
		
		Application application = new Application();
		application.setStatus(ApplicationStatus.DRAFT);
		application.setApplicationType(ApplicationType.GENERAL_MEMBER);
		application.setApprovalMinNo("324");
		application.setAuditDetails(null);
		application.setEmplSector(IndustrySector.BANKING);
		application.setFileNo("FILE44");
		application.setGazetteNoticeNo("Gazette#5663");
		application.setLicenceCollectedOrDispatched(null);
		application.setSubmissionDate(new Date());
		application.setMember(new Member(memberId));
		applicationHelper.updateApplication(applicationId2,application);
	}
	
	public Member createMember(){
		Member member = new Member();
		member.setMemberType(MemberType.MEMBER);
		member.setPin("AA23W44");
		member.setStatus(MembershipStatus.APPLICANT);
		member.setHasConvictions(false);
		//user
		User user = new User();
		user.setEmail("mimi@test.org");
		user.setUsername("Mimi");
		user.setPassword("passwd");
		member.setUser(user);
		//userdata
		UserData data = new UserData();
		data.setFirstName("Mimi");
		data.setLastName("Testing");
		data.setGender(Gender.FEMALE);
		data.setSalutation(new HashSet<String>(Arrays.asList("DR","Mrs","Hon")));
		member.setUserData(data);
		memberHelper.createMember(member);
		memberId = member.getRefId();
		return member;
	}
	
	public void retrieveApplications(){
		ResourceCollectionModel<Application> Applications =  applicationHelper.getAllApplications(0, 10,uriInfo);
		Assert.assertSame(Applications.getTotal(), 2);
	}
	
	public void getById(){
		Application role = applicationHelper.getApplicationById(applicationId1);
		Assert.assertNotNull(role);
		
		role = applicationHelper.getApplicationById(applicationId2);
		Assert.assertNotNull(role);
	}
	
	public void delete(){
		
		applicationHelper.deleteApplication(applicationId1);
		applicationHelper.deleteApplication(applicationId2);
		memberHelper.deleteMember(memberId);
	}
	
	public void retrieveApplicationsAfterDelete(){
		ResourceCollectionModel<Application> Applications =  applicationHelper.getAllApplications(0, 10, uriInfo);
		Assert.assertSame(Applications.getTotal(), 0);
	}

}
