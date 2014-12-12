package com.icpak.rest.models.base;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.wordnik.swagger.annotations.ApiModel;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
@ApiModel(description="Membership details of a user")
public class Member{

	/**
	 * 
	 */
	private String status;
	
	public Member() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
