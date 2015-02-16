/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.icpak.rest.models.auth;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

import com.icpak.rest.models.base.ExpandTokens;
import com.icpak.rest.models.base.PO;
import com.icpak.rest.models.membership.Member;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Simple class that represents any User domain entity in any application.
 */

@ApiModel(value="User Model", description="A User represents any person who may have access to the system including "
		+ "members, administrators, icpak staff and stakeholders")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Member.class})

@Entity
@Table(name="user")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class User extends PO{

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
    
    @XmlTransient
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name="user_role",
    joinColumns=@JoinColumn(name="userid"),
    inverseJoinColumns=@JoinColumn(name="roleid"))
    @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
    private Set<Role> roles = new HashSet<Role>();
    
    @OneToOne(mappedBy="user", 
    		cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.MERGE})
    private UserData userData=null;
    
    @OneToOne
    @JoinColumn(name="memberid")
    @XmlTransient
    private Member member; //A system user can be a member of ICPAK
    
    public User() {
	}

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

	public void addRole(Role role) {
		roles.add(role);
	}

	public void removeRole(Role role) {
		roles.remove(role);
	}
	
	public User clone(String...expand){
		User user = new User();
		user.setRefId(refId);
		user.setUsername(getUsername());
		
		if(expand!=null){
			for(String token: expand){
//				if(token.equals("bookings")){
//					for(Booking booking: bookings){
//						user.addBooking(booking.clone());
//					}
//				}
				
				if(token.toUpperCase().equals(ExpandTokens.DETAIL.name())){
//					user.setLastName(getLastName());
//					user.setFirstName(getFirstName());
					user.setEmail(getEmail());
				}
				
				if(token.equals("member")){
					//user.setMember(member);
				}
				
				if(token.equals("roles")){
					for(Role role: roles){
						user.addRole(role.clone());
					}
				}
			}
		}
		return user;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
		userData.setUser(this);
	}
	
	@PreUpdate
	@PrePersist
	public void updateUserDataRef(){
		if(refId==null && member!=null){
			setRefId(member.getRefId());
		}
	}

	public void copy(User user) {
		setEmail(user.getEmail());
		setPassword(user.getPassword());
		setUsername(user.getUsername());
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

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

