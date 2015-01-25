package com.icpak.rest.models.membership;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.icpak.rest.models.base.PO;
import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(description="Criminal offenses a member has been convicted for")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
@Table(name="criminaloffence")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class CriminalOffense extends PO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="memberid")
	private Member member;
	private String offense;
	private Date dateConvicted;
	private String placeConvicted;
	private String sentenceImposed;
	
	public CriminalOffense() {
	}
	
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getOffense() {
		return offense;
	}
	public void setOffense(String offense) {
		this.offense = offense;
	}
	public Date getDateConvicted() {
		return dateConvicted;
	}
	public void setDateConvicted(Date dateConvicted) {
		this.dateConvicted = dateConvicted;
	}
	public String getPlaceConvicted() {
		return placeConvicted;
	}
	public void setPlaceConvicted(String placeConvicted) {
		this.placeConvicted = placeConvicted;
	}
	public String getSentenceImposed() {
		return sentenceImposed;
	}
	public void setSentenceImposed(String sentenceImposed) {
		this.sentenceImposed = sentenceImposed;
	}
	
}
