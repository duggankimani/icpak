package com.icpak.test;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TestUserAPI extends TestBootstrap {

	@Test
	public void testGetAll() throws IOException {
		Client client = Client.create(new DefaultClientConfig());
		WebResource service = client.resource(getBaseURI());

		ClientResponse resp = service.path("api").path("users")
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		String text = resp.getEntity(String.class);
		Assert.assertEquals(200, resp.getStatus());
		
		System.err.println(text);

	}

	// @Test
	// public void testGetById() throws IOException {
	// Client client = Client.create( new DefaultClientConfig() );
	// WebResource service = client.resource( getBaseURI() );
	//
	// ClientResponse resp = service.path( "services" )
	// .path( "stuff" ).path( "id1" )
	// .accept( MediaType.TEXT_HTML )
	// .get( ClientResponse.class );
	//
	// String text = resp.getEntity( String.class );
	//
	// assertEquals( 200, resp.getStatus() );
	// assertEquals( "<html><body><div>stuff1</div></body></html>", text );
	//
	// String text2 = service.path( "services" )
	// .path( "stuff" ).path( "non_existent_id" )
	// .accept( MediaType.TEXT_HTML )
	// .get( String.class );
	//
	// assertEquals( 200, resp.getStatus() );
	// assertEquals( "<html><body><div>Not Found</div></body></html>", text2 );
	//
	// }
}
