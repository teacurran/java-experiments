package com.wirelust.experiment.sessioncluster.producers;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Properties;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import com.wirelust.experiment.sessioncluster.qualifiers.ClasspathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 03-Aug-2015
 *
 * @author T. Curran
 */
@ApplicationScoped
public class ResourceBundleProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceBundleProducer.class);

	@Produces
	@ClasspathResource("")
	Properties loadPropertiesBundle(InjectionPoint injectionPoint) {
		String name = getName(injectionPoint);

		Properties properties = new Properties();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(name);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			LOGGER.error("error loading properties bundle:{}", name, e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error("error closing properties file", e);
				}
			}
		}
		return properties;
	}

	private String getName(InjectionPoint ip) {
		Set<Annotation> qualifiers = ip.getQualifiers();
		for (Annotation qualifier : qualifiers) {
			if (qualifier.annotationType().equals(ClasspathResource.class)) {
				return ((ClasspathResource) qualifier).value();
			}
		}
		throw new IllegalArgumentException("Injection point " + ip + " does not have @Resource qualifier");
	}


}