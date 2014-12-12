package com.icpak.test;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.Test;

public class TestBootstrap{

	static final URI BASE_URI = getBaseURI();
	HttpServer server;

	protected static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/icpak").port(9998).build();
	}
	
//	@Before
//	public void startServer() throws IOException {
//		Injector injector = Guice.createInjector(new BootstrapServletModule());
//		org.apache.shiro.mgt.SecurityManager securityManager = 
//				injector.getInstance(org.apache.shiro.mgt.SecurityManager.class);
//		SecurityUtils.setSecurityManager(securityManager);
//
//		ResourceConfig rc = new PackagesResourceConfig(
//				GenericBootstrapConstants.JERSEY_PROPERTY_PACKAGES);
//		IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory(rc,
//				injector);
//		server = GrizzlyServerFactory.createHttpServer(BASE_URI + "api/",
//				rc, ioc);
//		
//	}
	
	@Test
	public void testMe(){
		System.err.println("Testing");
	}
}
