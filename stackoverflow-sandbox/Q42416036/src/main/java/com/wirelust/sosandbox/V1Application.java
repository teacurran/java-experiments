package com.wirelust.sosandbox;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.wirelust.sosandbox.providers.JacksonConfigurationProvider;

/**
 * Date: 23-Feb-2017
 *
 * @author T. Curran
 */
@ApplicationPath("/api/v1")
public class V1Application extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		classes.add(JacksonConfigurationProvider.class);
		classes.add(TestResource.class);

		return classes;
	}

}
