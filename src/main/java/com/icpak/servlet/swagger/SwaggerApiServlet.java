package com.icpak.servlet.swagger;

import javax.servlet.ServletConfig;

import com.google.inject.Singleton;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.jersey.config.JerseyJaxrsConfig;
import com.wordnik.swagger.reader.ClassReaders;

@Singleton
public class SwaggerApiServlet extends JerseyJaxrsConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig servletConfig) {
		super.init(servletConfig);
		SwaggerConfig swaggerConfig = new SwaggerConfig();
		ConfigFactory.setConfig(swaggerConfig);
		swaggerConfig.setBasePath("http://localhost:8080/icpak/api");
		swaggerConfig.setApiVersion("1.0.0");
		ScannerFactory.setScanner(new DefaultJaxrsScanner());
		ClassReaders.setReader(new DefaultJaxrsApiReader());
	}
}
