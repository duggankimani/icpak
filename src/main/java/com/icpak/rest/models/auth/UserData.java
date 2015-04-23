package com.icpak.rest.models.auth;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.icpak.rest.models.base.PO;
import com.icpak.rest.models.membership.Member;
import com.wordnik.swagger.annotations.ApiModel;


@ApiModel(value="User Data Model", 
description="A UserData model represents data for any person")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
@Table(name="userdata")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class UserData extends PO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(length=255)
    private String firstName;
    
    @Column(length=255)
	private String lastName;
    
    @ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(name="user_salutation", 
    	joinColumns=@JoinColumn(name="userid",referencedColumnName="id", nullable=false))
    @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
    @Column(name="salutation")
    private Set<String> salutation = new HashSet<>(); //Mr, Miss, Dr etc
    
    private String title;// Not sure - will see
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Enumerated(EnumType.ORDINAL)
    private AgeGroup ageGroup;
    
    private Date dob;

    @JsonIgnore
    @XmlTransient
    @OneToOne
    @JoinColumn(name="userid")
    private User user;
    
    @JsonIgnore
    @XmlTransient
    @OneToOne
    @JoinColumn(name="memberid")
    private Member member;
    
    private Long delegateId; //Event delegate id
    
    private String nationality;
    
    private String residence;
    
    private boolean isOverseas;
    
    private String county;
    

    public UserData() {
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Set<String> getSalutation() {
		return salutation;
	}


	public void setSalutation(Set<String> salutation) {
		this.salutation = salutation;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Gender getGender() {
		return gender;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public AgeGroup getAgeGroup() {
		return ageGroup;
	}


	public void setAgeGroup(AgeGroup ageGroup) {
		this.ageGroup = ageGroup;
	}


	public Date getDob() {
		return dob;
	}


	public void setDob(Date dob) {
		this.dob = dob;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}


	public Long getDelegateId() {
		return delegateId;
	}


	public void setDelegateId(Long delegateId) {
		this.delegateId = delegateId;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public String getResidence() {
		return residence;
	}


	public void setResidence(String residence) {
		this.residence = residence;
	}


	public boolean isOverseas() {
		return isOverseas;
	}


	public void setOverseas(boolean isOverseas) {
		this.isOverseas = isOverseas;
	}


	public String getCounty() {
		return county;
	}


	public void setCounty(String county) {
		this.county = county;
	}

	public UserData clone(String ... opts){
		UserData data = new UserData();
		data.copy(this);
		
		return data;
	}

	public void copy(UserData userData) {
		setAgeGroup(userData.ageGroup);
		setCounty(userData.county);
		setDelegateId(userData.delegateId);
		setDob(userData.dob);
		setFirstName(userData.firstName);
		setGender(userData.gender);
		setLastName(userData.lastName);
		setNationality(userData.nationality);
		setOverseas(userData.isOverseas);
		setResidence(userData.residence);
		setSalutation(userData.salutation);
		setTitle(userData.title);
	}
	
}
