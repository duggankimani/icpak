package com.icpak.rest.models.membership;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.icpak.rest.models.base.PO;
import com.icpak.rest.models.event.Booking;
import com.wordnik.swagger.annotations.ApiModel;


@ApiModel(description="A Persons or organizations contacts")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Member.class})

@Entity
@Table(name="contact")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class Contact extends PO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private ContactType type;
	private boolean isPrimaryContact;
	private String email;

	private String telephoneNumbers;
	
	private String mobileNumbers;
	
	@Column(length=500)
	private String physicalAddress;
	
	private String postalCode;
	
	@ManyToOne
	@JoinColumn(name="memberid")
	private Member member;
	
	private String contactName;
	
	@ManyToOne
	@JoinColumn(name="practiceid")
	private Practice practice;
	
	@ManyToOne
	@JoinColumn(name="branchid")
	private Branch branch;
	
	private String website;
	
	private String fax;
	
	private String city;
	
	private String country;
	
	@OneToOne
	@XmlTransient
	private Booking booking;
	

	public ContactType getType() {
		return type;
	}

	public void setType(ContactType type) {
		this.type = type;
	}

	public boolean isPrimaryContact() {
		return isPrimaryContact;
	}

	public void setPrimaryContact(boolean isPrimaryContact) {
		this.isPrimaryContact = isPrimaryContact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Practice getPractice() {
		return practice;
	}

	public void setPractice(Practice practice) {
		this.practice = practice;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTelephoneNumbers() {
		return telephoneNumbers;
	}

	public void setTelephoneNumbers(String telephoneNumbers) {
		this.telephoneNumbers = telephoneNumbers;
	}

	public String getMobileNumbers() {
		return mobileNumbers;
	}

	public void setMobileNumbers(String mobileNumbers) {
		this.mobileNumbers = mobileNumbers;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
}
