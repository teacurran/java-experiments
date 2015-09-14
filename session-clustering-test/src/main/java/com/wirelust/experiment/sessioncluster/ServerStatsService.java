package com.wirelust.experiment.sessioncluster;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.wirelust.experiment.sessioncluster.qualifiers.ClasspathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 03-Aug-2015
 *
 * @author T. Curran
 */
@Named
@Singleton
public class ServerStatsService implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerStatsService.class);

	@Inject
	@ClasspathResource("version.properties")
	protected Properties versionProperties;

	protected String hostName = null;

	public String getHostName() {
		if (hostName != null) {
			return hostName;
		}

		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			hostName = inetAddress.getHostName();
		} catch (UnknownHostException une) {
			LOGGER.error("unable to get host name", une);
			hostName = "unknown";
		}

		return hostName;
	}

	public String getNodeName() {
		return System.getProperty("jboss.node.name");
	}

	public String getSystemProperty(String attribute) {
		return System.getProperty(attribute);
	}


	public String getVersionAttribute(String attribute) {
		if (versionProperties == null) {
			return null;
		}
		return versionProperties.getProperty(attribute);
	}

}
