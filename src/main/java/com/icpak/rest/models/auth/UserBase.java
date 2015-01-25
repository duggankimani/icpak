package com.icpak.rest.models.auth;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Index;

import com.icpak.rest.models.base.PO;
import com.wordnik.swagger.annotations.ApiModelProperty;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)

@MappedSuperclass
public class UserBase extends PO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="username", required=true)
    @Basic(optional=false)
    @Column(length=100, unique=true)
    @Index(name="idx_users_username")
    private String username;

	@ApiModelProperty(value="user email", required=true)
    @Basic(optional=false)
    @Index(name="idx_users_email")    
    private String email;
	
    @Basic(optional=false)
    @Column(length=255)
    private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
