package com.wirelust.experiment.sessioncluster;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Date: 01-Aug-2015
 *
 * @author T. Curran
 */
@Named
@SessionScoped
public class SessionBean implements Serializable {

	HashMap<String, KeyValue> hashMap = new HashMap<>();

	String key;
	String value;

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

	public class KeyValue {
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
}
