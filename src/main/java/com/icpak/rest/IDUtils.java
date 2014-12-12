package com.icpak.rest;

import org.apache.commons.lang.RandomStringUtils;

public class IDUtils {

	public static String generateId(){
		//String uuid = UUID.randomUUID().toString();
		//uuid.replaceAll("-", "");
		return RandomStringUtils.random(16, true, true);
		
	}
}
