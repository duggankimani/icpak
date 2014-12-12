package com.icpak.rest.models.event;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import com.icpak.rest.models.base.ExpandTokens;
import com.icpak.rest.models.base.PO;
import com.icpak.rest.models.base.User;
import com.wordnik.swagger.annotations.ApiModel;

/**
 * Booking Model 
 * @author duggan
 *
 */
@ApiModel(value="A Booking Model", description="Represents a booking of an event by a user")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Event.class, User.class})

@Entity
@Table(name="booking")
public class Booking extends PO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String bookingId;
	private Date bookingDate;
	private String paymentRef;
	private Date paymentDate;
	private String status;
	
	@ManyToOne
	private Event event;
	
	@ManyToOne
	private User user;
	
	//Utility Methods for REST
	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getPaymentRef() {
		return paymentRef;
	}

	public void setPaymentRef(String paymentRef) {
		this.paymentRef = paymentRef;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Booking clone(String ... expand){
		
		Booking booking = new Booking();
		booking.setBookingDate(bookingDate);
		booking.setBookingId(bookingId);
		booking.setStatus(status);
		booking.setPaymentRef(paymentRef);
		booking.setPaymentDate(paymentDate);
		booking.setUser(user.clone());
		booking.setEvent(event.clone());
		
		if(expand!=null)
		for(String token: expand){
			if(token.equals("user")){
				
			}
			
			if(token.equals("event")){
				
			}
			
			if(token.equalsIgnoreCase(ExpandTokens.META.name())){
//				booking.setCreated(created);
//				booking.setCreatedBy(createdBy);
			}
			
			
		}
		
		return booking;
	}
	
}
