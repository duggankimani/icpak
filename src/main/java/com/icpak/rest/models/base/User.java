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
package com.icpak.rest.models.base;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.icpak.rest.models.event.Booking;
import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import java.util.HashSet;
import java.util.Set;

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
public class User extends UserBase{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
         
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name="user_role",
    joinColumns=@JoinColumn(name="userid"),
    inverseJoinColumns=@JoinColumn(name="roleid"))
    @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
    private Set<Role> roles = new HashSet<Role>();
    
    @OneToMany(mappedBy="user")
    private Set<Booking> bookings = new HashSet<>();
    
    @Embedded
    private Member member;
    
    public User() {
	}

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void addRole(Role role) {
		roles.add(role);
	}

	public void removeRole(Role role) {
		roles.remove(role);
	}
	
	public User clone(String...expand){
		User user = new User();
		user.setUserId(getUserId());
		user.setUsername(getUsername());
		
		if(expand!=null){
			for(String token: expand){
//				if(token.equals("bookings")){
//					for(Booking booking: bookings){
//						user.addBooking(booking.clone());
//					}
//				}
				
				if(token.toUpperCase().equals(ExpandTokens.DETAIL.name())){
					user.setLastName(getLastName());
					user.setFirstName(getFirstName());
					user.setEmail(getEmail());
				}
				
				if(token.equals("member")){
					user.setMember(member);
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

}


