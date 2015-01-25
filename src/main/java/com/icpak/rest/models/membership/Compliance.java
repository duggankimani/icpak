package com.icpak.rest.models.membership;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.icpak.rest.models.base.PO;
import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(description = "Audit Compliance in audit application")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ Member.class })
@Embeddable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Compliance extends PO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
