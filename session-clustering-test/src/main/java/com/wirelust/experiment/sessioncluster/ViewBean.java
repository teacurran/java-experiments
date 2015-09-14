package com.wirelust.experiment.sessioncluster;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * Date: 01-Aug-2015
 *
 * @author T. Curran
 */
@Named
@ViewScoped
public class ViewBean implements Serializable {

	HashMap<String, KeyValue> hashMap = new HashMap<>();

	String key;
	String value;

	Date postConstructDate;
	transient Date transientDate;

	@PostConstruct
	public void init() {
		if (postConstructDate == null) {
			postConstructDate = new Date();
		}
		if (transientDate == null) {
			transientDate = new Date();
		}
	}

	public void addValue() {
		if (key != null) {
			hashMap.put(key, new KeyValue(key, value));
		}
	}

	public Collection<KeyValue> getList() {
		return hashMap.values();
	}

	public HashMap<String, KeyValue> getHashMap() {
		return hashMap;
	}

	public void setHashMap(HashMap<String, KeyValue> hashMap) {
		this.hashMap = hashMap;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public class KeyValue implements Serializable {
		String key;
		String value;

		KeyValue(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public Date getTransientDate() {
		return transientDate;
	}

	public void setTransientDate(Date transientDate) {
		this.transientDate = transientDate;
	}

	public Date getPostConstructDate() {
		return postConstructDate;
	}

	public void setPostConstructDate(Date postConstructDate) {
		this.postConstructDate = postConstructDate;
	}

}
