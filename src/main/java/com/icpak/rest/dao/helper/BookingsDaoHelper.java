package com.icpak.rest.dao.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.icpak.rest.IDUtils;
import com.icpak.rest.dao.BookingsDao;
import com.icpak.rest.dao.EventsDao;
import com.icpak.rest.dao.UsersDao;
import com.icpak.rest.exceptions.ServiceException;
import com.icpak.rest.models.ErrorCodes;
import com.icpak.rest.models.base.ResourceCollectionModel;
import com.icpak.rest.models.base.User;
import com.icpak.rest.models.event.Booking;

@Transactional
public class BookingsDaoHelper {

	@Inject BookingsDao dao;
	@Inject UsersDao userDao;
	@Inject EventsDao eventDao;
	
	public ResourceCollectionModel<Booking> getAllBookings(UriInfo uriInfo, 
			String eventId,	Integer offset,	Integer limit) {

		ResourceCollectionModel<Booking> bookings = new ResourceCollectionModel<>(offset,limit, dao.getBookingCount(),uriInfo);
		
		List<Booking> list = dao.getAllBookings(offset, limit);
		
		List<Booking> clones = new ArrayList<>();
		for(Booking booking: list){
			Booking clone = booking.clone();
			clone.setUri(uriInfo.getAbsolutePath()+"/"+clone.getBookingId());
			clone.getEvent().setUri(uriInfo.getBaseUri()+"events/"+clone.getEvent().getEventId());
			clone.getUser().setUri(uriInfo.getBaseUri()+"users/"+clone.getUser().getUserId());
			clones.add(clone);
		}
		
		bookings.setItems(clones);
		return bookings;
	}

	public Booking getBookingById(String eventId,String bookingId) {

		Booking booking = dao.getByBookingId(bookingId);
		
		return booking.clone();
	}
	
	public Booking createBooking(String eventId,Booking booking) {
		assert booking.getBookingId()==null;
		
		booking.setBookingId(IDUtils.generateId());
		booking.setBookingDate(new Date());
//		booking.setPaymentDate(booking.getPaymentDate());
//		booking.setPaymentRef(booking.getPaymentRef());
		booking.setStatus(booking.getStatus());
		
		if(booking.getUser()==null || booking.getUser().getUserId()==null ){
			throw new ServiceException(ErrorCodes.ILLEGAL_ARGUMENT, "User", "UserId=null");
		}
		
		String userId = booking.getUser().getUserId();
		User user = userDao.findByUserId(userId, true);
		booking.setUser(user);
		
		booking.setEvent(eventDao.getByEventId(eventId));
		dao.save(booking);
		
		assert booking.getId()!=null;
		
		return booking.clone();
	}

	public Booking updateBooking(String eventId,String bookingId, Booking booking) {
		assert booking.getBookingId()!=null;
		
		Booking poBooking = getBookingById(eventId,bookingId);
		poBooking.setBookingDate(booking.getBookingDate());
//		poBooking.setPaymentDate(booking.getPaymentDate());
//		poBooking.setPaymentRef(booking.getPaymentRef());
		poBooking.setStatus(booking.getStatus());
		
		if(booking.getUser()==null || booking.getUser().getUserId()==null ){
			throw new ServiceException(ErrorCodes.ILLEGAL_ARGUMENT, "User", "UserId=null");
		}
		
		if(booking.getUser().getUserId()==null){
			String userId = booking.getUser().getUserId();
			User user = userDao.findByUserId(userId, true);
			poBooking.setUser(user);
		}
		
		poBooking.setEvent(eventDao.getByEventId(eventId));
		poBooking.setBookingId(booking.getBookingId());
		dao.save(poBooking);
		
		return poBooking.clone();
	}

	public void deleteBooking(String eventId, String bookingId) {
		Booking booking = dao.getByBookingId(bookingId);
		dao.delete(booking);
	}

}
