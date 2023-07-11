package com.dev.BionLifeScienceWar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

@Configuration
public class ApplicationInitilaizer implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		 servletContext.setInitParameter("spring.profiles.active", "prod");
		
	}
}
