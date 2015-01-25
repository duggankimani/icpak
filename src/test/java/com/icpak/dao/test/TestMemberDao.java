package com.icpak.dao.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.google.inject.Inject;
import com.icpak.dao.testbase.AbstractDaoTest;
import com.icpak.rest.dao.helper.MemberDaoHelper;
import com.icpak.rest.models.auth.Gender;
import com.icpak.rest.models.auth.User;
import com.icpak.rest.models.auth.UserData;
import com.icpak.rest.models.base.ResourceCollectionModel;
import com.icpak.rest.models.membership.CriminalOffense;
import com.icpak.rest.models.membership.EduType;
import com.icpak.rest.models.membership.Education;
import com.icpak.rest.models.membership.Member;
import com.icpak.rest.models.membership.MemberType;
import com.icpak.rest.models.membership.MembershipStatus;
import com.icpak.rest.models.membership.Specialization;
import com.icpak.rest.models.membership.TrainingAndExperience;
import com.icpak.rest.models.membership.TrainingExperienceType;

public class TestMemberDao extends AbstractDaoTest{

	@Inject MemberDaoHelper helper;
	
	String memberId1;
	String memberId2;
	
	@Test
	public void testCrud(){
		createMember();
		retrieveMembers();
		//User & UserData
		updateMembers();
		//Education
		addEducation();
		//Specializations
		addSpecializations();
		//Experience
		addTrainingAndExperience();
		//Criminal Offense
		addCriminalOffenses();
		//
		
		//Applications
		createApplications();
		getById();
		delete();
		retrieveMembersAfterDelete();
	}
	
	private void addCriminalOffenses() {
		List<CriminalOffense> list = new ArrayList<>();
		Member member = helper.getMemberById(memberId1);
		
		CriminalOffense offense = new CriminalOffense();
		offense.setMember(member);
		offense.setDateConvicted(new Date());
		offense.setOffense("Some shit I did");
		offense.setPlaceConvicted("Maanzoni");
		offense.setSentenceImposed("A day under the lock");
		list.add(offense);
		
		offense = new CriminalOffense();
		offense.setMember(member);
		offense.setDateConvicted(new Date());
		offense.setOffense("Some shit I did");
		offense.setPlaceConvicted("Maanzoni");
		offense.setSentenceImposed("A day under the lock");
		list.add(offense);
		
		member.setOffenses(list);
		helper.updateMember(memberId1, member);
	}

	private void addTrainingAndExperience() {
		List<TrainingAndExperience> list = new ArrayList<>();
		Member member = helper.getMemberById(memberId1);
		
		TrainingAndExperience tne  = new TrainingAndExperience();
		tne.setDatePassed(new Date());
		tne.setNatureOfTasksPerformed("fmdsfkdsgnk lknlkfndslkfk lknelkfrklds flmk");
		tne.setOrganizationName("TNT Limited");
		tne.setResponsibilities("One or two things here and there");
		tne.setType(TrainingExperienceType.AUDITEXPERIENCE);
		list.add(tne);
		
		tne  = new TrainingAndExperience();
		tne.setDatePassed(new Date());
		tne.setNatureOfTasksPerformed("Ndio Hiyo");
		tne.setOrganizationName("Anada one Limited");
		tne.setResponsibilities("One or two things here and there");
		tne.setType(TrainingExperienceType.GENERALEXPERIENCE);
		list.add(tne);
		
		member.setTrainingAndExperience(list);
		helper.updateMember(memberId1, member);
	}

	private void addSpecializations() {
		List<Specialization> list = new ArrayList<>();
		Member member = helper.getMemberById(memberId1);
		Specialization spc = new Specialization();
		spc.setSpecialization("Audit");
		list.add(spc);
		
		spc = new Specialization();
		spc.setSpecialization("Tax");
		list.add(spc);
		
		member.setSpecializations(list);
		helper.updateMember(memberId1, member);
		int count = helper.getMemberById(memberId1).getSpecializations().size();
		
		Assert.assertSame(count, 2);
	}

	private void addEducation() {
		
		List<Education> edu = new ArrayList<>();
		Education education = new Education();
		education.setType(EduType.ACADEMIA);
		Member member = helper.getMemberById(memberId1);
		education.setExaminingBody("ICPAK");
		education.setAward("Degree");
		education.setClassOrDivision("First Class");
		education.setInstitution("JKUAT");
		edu.add(education);
		
		education = new Education();
		education.setExaminingBody("ICPAK");
		education.setAward("Degree");
		education.setClassOrDivision("First Class");
		education.setInstitution("JKUAT");
		edu.add(education);
		member.setEducation(edu);
		education.setType(EduType.ACADEMIA);
		
		helper.updateMember(memberId1, member);
		int count = helper.getMemberById(memberId1).getEducation().size();
		Assert.assertSame(count, 2);
	}
	
	

	public void createMember(){
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
		helper.createMember(member);
		memberId1 = member.getRefId();
		
		//MEMBER 2
		member = new Member();
		member.setMemberType(MemberType.MEMBER);
		member.setPin("AA23W44");
		member.setStatus(MembershipStatus.APPLICANT);
		member.setHasConvictions(false);
		//user
		user = new User();
		user.setEmail("wewe@test.org");
		user.setUsername("Wewe");
		user.setPassword("passwd");
		member.setUser(user);
		//userdata
		data = new UserData();
		data.setFirstName("Mimi");
		data.setLastName("Testing");
		data.setGender(Gender.FEMALE);
		data.setSalutation(new HashSet<String>(Arrays.asList("DR","Mrs","Hon")));
		member.setUserData(data);
		helper.createMember(member);
		memberId2  = member.getRefId();
	}
	
	public void updateMembers(){
		Member member = new Member();
		member.setMemberType(MemberType.MEMBER);
		member.setPin("AA23IIW44");
		member.setStatus(MembershipStatus.APPLICANT);
		member.setHasConvictions(false);
		//user
		User user = new User();
		user.setEmail("mimi1@test.org");
		user.setUsername("Mimi1");
		user.setPassword("passwd");
		member.setUser(user);
		//userdata
		UserData data = new UserData();
		data.setFirstName("Mimi1");
		data.setLastName("Testing1");
		data.setGender(Gender.MALE);
		data.setSalutation(new HashSet<String>(Arrays.asList("DR","Mrs","Hon")));
		member.setUserData(data);
		helper.updateMember(memberId1, member);
		
		//MEMBER 2
		member = new Member();
		member.setMemberType(MemberType.MEMBER);
		member.setPin("AA23W44");
		member.setStatus(MembershipStatus.APPLICANT);
		member.setHasConvictions(false);
		//user
		user = new User();
		user.setEmail("wewe1@test.org");
		user.setUsername("Wewe1");
		user.setPassword("passwd1");
		member.setUser(user);
		//userdata
		data = new UserData();
		data.setFirstName("Mimi2");
		data.setLastName("Testing2");
		data.setGender(Gender.MALE);
		data.setSalutation(new HashSet<String>(Arrays.asList("DR","Mrs","Hon")));
		member.setUserData(data);
		helper.updateMember(memberId2, member);
		
	}
	
	public void createApplications(){
		
	}
	
	public void retrieveMembers(){
		ResourceCollectionModel<Member> members =  helper.getAllMembers(0, 10, uriInfo);
		Assert.assertSame(members.getTotal(), 2);
	}
	
	public void getById(){
		Member member = helper.getMemberById(memberId1);
		Assert.assertNotNull(member);
		
		member = helper.getMemberById(memberId2);
		Assert.assertNotNull(member);
	}
	
	public void delete(){
		
		helper.deleteMember(memberId1);
		helper.deleteMember(memberId2);
	}
	
	public void retrieveMembersAfterDelete(){
		ResourceCollectionModel<Member> members =  helper.getAllMembers(0, 10, uriInfo);
		Assert.assertSame(members.getTotal(), 0);
	}

}
