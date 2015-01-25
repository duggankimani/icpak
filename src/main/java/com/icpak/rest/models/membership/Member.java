package com.icpak.rest.models.membership;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.icpak.rest.models.auth.User;
import com.icpak.rest.models.auth.UserData;
import com.icpak.rest.models.base.PO;
import com.wordnik.swagger.annotations.ApiModel;


@ApiModel(description="Membership details of a user")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Member.class})

@Entity
@Table(name="member")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class Member extends PO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private MembershipStatus status;
	private int hasConvictions=0;
	private String pin;
	private MemberType memberType; // Member, AssociateMember, Practicing Member
	
	@OneToOne(mappedBy="member",cascade=CascadeType.ALL)
	private UserData userData;
	
	@OneToOne(mappedBy="member",cascade=CascadeType.ALL)
	private User user;//A member is a system user
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Collection<Education> education = new HashSet<>();
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Collection<Specialization> specializations = new HashSet<>();
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Collection<TrainingAndExperience> trainingAndExperience = new HashSet<>();
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Collection<CriminalOffense> offenses = new HashSet<>();
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Collection<Partner> partners = new HashSet<>();
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Collection<Partner> partnerships = new HashSet<>();
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Collection<Application> applications = new HashSet<>();
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Collection<Practice> firms = new HashSet<>();
	
	public Member() {
	}

	public Member(String memberId) {
		this.refId = memberId;
	}

	public MembershipStatus getStatus() {
		return status;
	}

	public void setStatus(MembershipStatus status) {
		this.status = status;
	}

	public boolean getHasConvictions() {
		return hasConvictions==0;
	}

	public void setHasConvictions(boolean hasConvictions) {
		this.hasConvictions = hasConvictions? 1: 0;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public MemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		if(this.userData!=null && userData!=null){
			this.userData.copy(userData);
		}else{
			this.userData = userData;
		}
		
//		this.userData.setMember(this);
//		if(user!=null){
//			user.setUserData(userData);
//		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		if(this.getUser()!=null){
			this.user.copy(user);
		}else{
			this.user = user;
		}
		
//		this.user.setMember(this);
//		if(userData!=null){
//			user.setUserData(userData);
//		}
	}

	public Collection<Application> getApplications() {
		return applications;
	}

	public void setApplications(Collection<Application> applications) {
		this.applications = applications;
	}

	public Collection<Practice> getFirms() {
		return firms;
	}

	public void setFirms(Collection<Practice> firms) {
		this.firms = firms;
	}

	public Collection<Education> getEducation() {
		return education;
	}

	public void setEducation(Collection<Education> education) {
		for(Education edu: education){
			edu.setMember(this);
			this.education.add(edu);
		}
	}

	public Collection<Specialization> getSpecializations() {
		return specializations;
	}

	public void setSpecializations(Collection<Specialization> specializations) {
		this.specializations = specializations;
	}

	public Collection<TrainingAndExperience> getTrainingAndExperience() {
		return trainingAndExperience;
	}

	public void setTrainingAndExperience(
			Collection<TrainingAndExperience> trainingAndExperience) {
		this.trainingAndExperience = trainingAndExperience;
	}

	public Collection<CriminalOffense> getOffenses() {
		return offenses;
	}

	public void setOffenses(Collection<CriminalOffense> offenses) {
		this.offenses = offenses;
	}

	public Collection<Partner> getPartners() {
		return partners;
	}

	public void setPartners(Collection<Partner> partners) {
		this.partners = partners;
	}

	public Collection<Partner> getPartnerships() {
		return partnerships;
	}

	public void setPartnerships(Collection<Partner> partnerships) {
		this.partnerships = partnerships;
	}

	public Member clone(String ... expand) {
		return this;
	}
}
