package com.icpak.rest.models.event;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.icpak.rest.models.auth.User;
import com.icpak.rest.models.base.ExpandTokens;
import com.icpak.rest.models.base.PO;
import com.icpak.rest.models.membership.Contact;
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
	
	private String companyName;
	private Contact contact;
	private String paymentMode;
	private String currency;
	private Date bookingDate;
	
	private String paymentRef;
	private Date paymentDate;
	private String status; //DRAFT/ PAID
	private int delegatesCount;
	
	@OneToMany(mappedBy="booking",fetch=FetchType.LAZY)
	private Collection<Delegate> delegates = new HashSet<>();
	
	@ManyToOne
	private Event event;
	
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

	public Booking clone(String ... expand){
		
		Booking booking = new Booking();
		booking.setBookingDate(bookingDate);
		booking.setRefId(getRefId());
		booking.setStatus(status);
		booking.setPaymentRef(paymentRef);
		booking.setPaymentDate(paymentDate);
		//booking.setUser(user.clone());
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getDelegatesCount() {
		return delegatesCount;
	}

	public void setDelegatesCount(int delegatesCount) {
		this.delegatesCount = delegatesCount;
	}

	public Collection<Delegate> getDelegates() {
		return delegates;
	}

	public void setDelegates(Collection<Delegate> delegates) {
		this.delegates = delegates;
	}
	
}
