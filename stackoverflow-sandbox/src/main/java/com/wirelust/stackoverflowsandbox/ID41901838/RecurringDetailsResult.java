package com.wirelust.stackoverflowsandbox.ID41901838;

import java.util.Date;
import java.util.List;

public class RecurringDetailsResult implements java.io.Serializable {

	private Date creationDate;
	private String shopperReference;
	private List<RecurringDetailWrapper> details;
	private String lastKnownShopperEmail;

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date value) {
		this.creationDate = value;
	}

	public String getShopperReference() {
		return this.shopperReference;
	}

	public void setShopperReference(String value) {
		this.shopperReference = value;
	}

	public List<RecurringDetailWrapper> getDetails() {
		return this.details;
	}

	public void setDetails(List<RecurringDetailWrapper> value) {
		this.details = value;
	}

	public String getLastKnownShopperEmail() {
		return this.lastKnownShopperEmail;
	}

	public void setLastKnownShopperEmail(String value) {
		this.lastKnownShopperEmail = value;
	}

}