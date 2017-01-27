package com.wirelust.stackoverflowsandbox.ID41901838;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;

public class RecurringDetail implements java.io.Serializable {
	private static final long serialVersionUID = 5302883242997268343L;


	private String name;
	private Date creationDate;
	private Card card;
	private AdditionalData additionalData;
	private String socialSecurityNumber;
	private String recurringDetailReference;
	private String alias;
	private String aliasType;
	private String variant;
	private String paymentMethodVariant;
	private String firstPspReference;
	private List<String> contractTypes;
	private String acquirer;
	private String acquirerAccount;

	public class AdditionalData {
		String cardBin;

		public String getCardBin() {
			return cardBin;
		}

		public void setCardBin(String cardBin) {
			this.cardBin = cardBin;
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date value) {
		this.creationDate = value;
	}

	public Card getCard() {
		return this.card;
	}

	public void setCard(Card value) {
		this.card = value;
	}

	public AdditionalData getAdditionalData() {
		return this.additionalData;
	}

	public void setAdditionalData(AdditionalData value) {
		this.additionalData = value;
	}

	public String getSocialSecurityNumber() {
		return this.socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String value) {
		this.socialSecurityNumber = value;
	}

	public String getRecurringDetailReference() {
		return this.recurringDetailReference;
	}

	public void setRecurringDetailReference(String value) {
		this.recurringDetailReference = value;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String value) {
		this.alias = value;
	}

	public String getAliasType() {
		return this.aliasType;
	}

	public void setAliasType(String value) {
		this.aliasType = value;
	}

	public String getVariant() {
		return this.variant;
	}

	public void setVariant(String value) {
		this.variant = value;
	}

	public String getPaymentMethodVariant() {
		return this.paymentMethodVariant;
	}

	public void setPaymentMethodVariant(String value) {
		this.paymentMethodVariant = value;
	}

	public String getFirstPspReference() {
		return this.firstPspReference;
	}

	public void setFirstPspReference(String value) {
		this.firstPspReference = value;
	}

	public List<String> getContractTypes() {
		return this.contractTypes;
	}

	public void setContractTypes(List<String> value) {
		this.contractTypes = value;
	}

	public String getAcquirer() {
		return this.acquirer;
	}

	public void setAcquirer(String value) {
		this.acquirer = value;
	}

	public String getAcquirerAccount() {
		return this.acquirerAccount;
	}

	public void setAcquirerAccount(String value) {
		this.acquirerAccount = value;
	}

}