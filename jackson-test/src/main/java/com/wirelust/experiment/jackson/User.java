package com.wirelust.experiment.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Date: 21-Jul-2015
 *
 * @author T. Curran
 */
public class User {

	@JsonProperty("user_id")
	Integer userId;

	String name;

	@JsonProperty("mention_name")
	String mentionName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMentionName() {
		return mentionName;
	}

	public void setMentionName(String mentionName) {
		this.mentionName = mentionName;
	}
}
