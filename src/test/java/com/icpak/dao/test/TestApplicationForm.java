package com.icpak.dao.test;

import org.junit.Test;

import com.google.inject.Inject;
import com.icpak.dao.testbase.AbstractDaoTest;
import com.icpak.rest.dao.helper.ApplicationFormDaoHelper;
import com.icpak.rest.models.membership.ApplicationFormHeader;
import com.icpak.rest.models.membership.ApplicationType;

public class TestApplicationForm extends AbstractDaoTest{

	@Inject ApplicationFormDaoHelper helper;
	
	@Test
	public void create(){
		ApplicationFormHeader header = new ApplicationFormHeader();
		header.setSurname("Macharia");
		header.setOtherNames("Duggan");
		header.setEmail("mdkimani@gmail.com");
		header.setApplicationType(ApplicationType.ASSOCIATE);
		header.setAddress1("P.o Box 37425 Nrb");
		header.setPostCode("00100");
		header.setCity1("Nairobi");
		header.setEmployer("Workpoint Limited");
		
		helper.createApplication(header);
		
	}
}
