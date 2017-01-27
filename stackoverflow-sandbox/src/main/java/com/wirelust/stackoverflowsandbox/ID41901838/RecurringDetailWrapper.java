package com.wirelust.stackoverflowsandbox.ID41901838;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("RecurringDetail")
public class RecurringDetailWrapper {

	@JsonProperty("RecurringDetail")
	RecurringDetail recurringDetail;

	public RecurringDetail getRecurringDetail() {
		return recurringDetail;
	}

	public void setRecurringDetail(RecurringDetail recurringDetail) {
		this.recurringDetail = recurringDetail;
	}
}
