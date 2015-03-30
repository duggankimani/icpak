package com.icpak.services.test;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.icpak.servlet.modules.BootstrapServletModule;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;

public abstract class TestBootstrap{

	static final URI BASE_URI = getBaseURI();
	HttpServer server;

	protected static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/").port(9998).build();
	}
	
	@Before
	public void startServer() throws IOException {
		Injector injector = Guice.createInjector(new BootstrapServletModule());
		ResourceConfig rc = new PackagesResourceConfig("com.icpak.rest");
		IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory(rc,
				injector);
		server = GrizzlyServerFactory.createHttpServer(BASE_URI+"/icpak",
				rc, ioc);
		
		System.out.println(String.format("Jersey app started with WADL available at "
                + "%sicpak/application.wadl\nTry out %sicpak\nHit enter to stop it...",
                BASE_URI, BASE_URI));
		
	}
	
	@After
    public void stopServer() {
        server.stop();
    }

	
}
