package com.wirelust.sosandbox.representations;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Date: 23-Feb-2017
 *
 * @author T. Curran
 */
@JsonRootName("foobar")
public class TestType {

	@JsonProperty("goodbye")
	String hello;

	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}
}
