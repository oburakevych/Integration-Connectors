package org.integration.payments.server.ui.context;

import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.integration.payments.server.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationListener implements ServletContextListener {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	/*
    	String applicationConfigurationsBeanId = servletContextEvent.getServletContext().getInitParameter("applicationConfigurationsBean");

    	ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());

    	Properties properties = (Properties) appContext.getBean(applicationConfigurationsBeanId);

        addAttributesToServletContext(properties, servletContextEvent.getServletContext());
        */
    	Properties properties = new Properties();

    	properties.put("ctx", servletContextEvent.getServletContext().getContextPath());
    	properties.put("testUriSecretPrefix", Constants.TEST_URI_SECRET_PREFIX);

    	addAttributesToServletContext(properties, servletContextEvent.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    /**
     * Adds application properties to servlet context as attributes from application profile.
     */
    private void addAttributesToServletContext(Properties properties, ServletContext servletContext) {
        for (Entry<?, ?> property : properties.entrySet()) {
        	servletContext.setAttribute(property.getKey().toString(), property.getValue());
        }

        if (log.isInfoEnabled()) {            
            log.info("Added the next properties to application context (as attributes): " + properties.toString());
        }
    }
}

