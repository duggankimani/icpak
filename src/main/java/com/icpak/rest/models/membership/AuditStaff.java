package com.icpak.rest.models.membership;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.icpak.rest.models.base.PO;
import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(description="Audit staff of a firm")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Member.class})

@Entity
@Table(name="auditstaff")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class AuditStaff extends PO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
}
