package com.icpak.rest.models.membership;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.icpak.rest.models.base.PO;

@Entity
@Table(name = "`Application Form Educational`")
public class ApplicationFormEducational extends PO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "`timestamp`", columnDefinition = "timestamp NOT NULL default current_timestamp")
	private Timestamp timestamp;

	@Column(nullable = false, name = "`Line No_`")
	private int lineNo;

	@Column(nullable = false, name = "`Application No_`", length = 20)
	private String applicationNo;

	@Column(nullable = false, name = "`Where Obtained`", length = 100)
	private String whereObtained;

	@Column(nullable = false, name = "`From Date`", columnDefinition = "datetime")
	private Date fromDate;

	@Column(nullable = false, name = "`To Date`", columnDefinition = "datetime")
	private Date toDate;

	@Column(nullable = false, name = "`Class_Division Attained`", length = 50)
	private String classDivisionAttained;

	@Column(nullable = false, name = "`Certificate Awarded`")
	private int certificateAwarded;

	@Column(nullable = false, name = "`Examining Body`", length = 50)
	private String examiningBody;

	@Column(nullable = false, name = "`Qualification Description`", length = 80)
	private String qualificationDesc;

	@Column(nullable = false, name = "`Qualification code`", length = 20)
	private String qualificationCode;

	@Column(nullable = false, name = "`Type`")
	private int type;

	@Column(nullable = false, name = "`Reg_No`", length = 15)
	private String regNo;

	@Column(nullable = false, name = "`Sections`", length = 30)
	private String sections;

	@Column(nullable = false, name = "`Description`", length = 50)
	private String description;

	public ApplicationFormEducational() {
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getWhereObtained() {
		return whereObtained;
	}

	public void setWhereObtained(String whereObtained) {
		this.whereObtained = whereObtained;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getClassDivisionAttained() {
		return classDivisionAttained;
	}

	public void setClassDivisionAttained(String classDivisionAttained) {
		this.classDivisionAttained = classDivisionAttained;
	}

	public int getCertificateAwarded() {
		return certificateAwarded;
	}

	public void setCertificateAwarded(int certificateAwarded) {
		this.certificateAwarded = certificateAwarded;
	}

	public String getExaminingBody() {
		return examiningBody;
	}

	public void setExaminingBody(String examiningBody) {
		this.examiningBody = examiningBody;
	}

	public String getQualificationDesc() {
		return qualificationDesc;
	}

	public void setQualificationDesc(String qualificationDesc) {
		this.qualificationDesc = qualificationDesc;
	}

	public String getQualificationCode() {
		return qualificationCode;
	}

	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getSections() {
		return sections;
	}

	public void setSections(String sections) {
		this.sections = sections;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
