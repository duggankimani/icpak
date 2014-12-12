package com.icpak.rest.models.base;

import com.icpak.rest.exceptions.ServiceException;
import com.icpak.rest.models.ErrorCodes;

public enum Permission {

	CAN_VIEW_ALL_USERS,
	CAN_CREATE_USER;

	public static Permission get(String permissionName) {
		
		try{
			return Permission.valueOf(permissionName.trim().toUpperCase());
		}catch(Exception e){
			throw new ServiceException(ErrorCodes.ILLEGAL_ARGUMENT, "Permission", "'"+permissionName+"'");
		}
	}
}
