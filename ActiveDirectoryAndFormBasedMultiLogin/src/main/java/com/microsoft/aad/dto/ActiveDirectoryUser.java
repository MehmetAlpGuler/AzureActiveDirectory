package com.microsoft.aad.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author MehmetAlpGuler
 */
@XmlRootElement
public class ActiveDirectoryUser {

	private String[] businessPhones;
	private String preferredLanguage;
	private String mail;
	private String mobilePhone;
	private String officeLocation;
	private String displayName;
	private String surname;
	private String givenName;
	private String jobTitle;
	@JsonProperty("@odata.context")
	private String odataContext;
	private String id;
	private String userPrincipalName;

	public String[] getBusinessPhones() {
		return businessPhones;
	}

	public void setBusinessPhones(String[] businessPhones) {
		this.businessPhones = businessPhones;
	}

	public String getOdataContext() {
		return odataContext;
	}

	public void setOdataContext(String odataContext) {
		this.odataContext = odataContext;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserPrincipalName() {
		return userPrincipalName;
	}

	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}
}



