package com.icpak.dao.test;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Inject;
import com.icpak.dao.testbase.AbstractDaoTest;
import com.icpak.rest.dao.helper.EventsDaoHelper;
import com.icpak.rest.models.base.ResourceCollectionModel;
import com.icpak.rest.models.event.Event;

public class TestEventDao  extends AbstractDaoTest{

	@Inject EventsDaoHelper helper;
	
	String eventId1;
	String eventId2;
	
	@Ignore
	public void testCrud(){
		createEvent();
		retrieveEvents();
		getById();
		delete();
		retrieveEventsAfterDelete();
	}
	
	public void createEvent(){
		Event event = new Event();
		event.setName("jua kali Show");
		event.setDescription("Some Show");
		helper.createEvent(event);
		eventId1 = event.getRefId();
		
		event = new Event();
		event.setName("Mama show");
		event.setDescription("Testing");
		helper.createEvent(event);
		eventId2 = event.getRefId();
	}
	
	public void retrieveEvents(){
		ResourceCollectionModel<Event> events =  helper.getAllEvents(uriInfo, 0, 10);
		Assert.assertSame(events.getTotal(), 2);
	}
	
	public void getById(){
		Event event = helper.getEventById(eventId1);
		Assert.assertNotNull(event);
		
		event = helper.getEventById(eventId2);
		Assert.assertNotNull(event);
	}
	
	public void delete(){
		
		helper.deleteEvent(eventId1);
		helper.deleteEvent(eventId2);
	}
	
	public void retrieveEventsAfterDelete(){
		ResourceCollectionModel<Event> events =  helper.getAllEvents(uriInfo, 0, 10);
		Assert.assertSame(events.getTotal(), 0);
	}
	
}
