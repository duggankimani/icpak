package com.icpak.dao.test.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.mail.MessagingException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.icpak.rest.utils.EmailServiceHelper;

public class TestEmail{

	@Test
	public void sendEmail() throws MessagingException, IOException{
		InputStream is = EmailServiceHelper.class.getClassLoader().getResourceAsStream("email.html");
		String html = IOUtils.toString(is);
		
		EmailServiceHelper.sendEmail(html, "Testing", Arrays.asList("mdkimani@gmail.com","tosh0948@gmail.com"),
				Arrays.asList("Duggan Kimani","Tom Kimani"), null);
	}
}
