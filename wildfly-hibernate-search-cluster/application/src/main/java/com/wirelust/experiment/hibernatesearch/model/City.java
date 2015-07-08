package com.wirelust.experiment.hibernatesearch.model;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * Date: 08-Jul-2015
 *
 * @author T. Curran
 */
@Entity
@Table(name = "cities")
@Indexed
@Cacheable
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DocumentId
	private Integer id;

	@Basic
	@Field(index = Index.YES, store = Store.YES, analyze = Analyze.YES)
	String name;

	@Basic
	@Field(index = Index.YES, store = Store.YES, analyze = Analyze.YES)
	String locode;

	@Basic
	@Field(index = Index.YES, store = Store.YES, analyze = Analyze.YES)
	String state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocode() {
		return locode;
	}

	public void setLocode(String locode) {
		this.locode = locode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
