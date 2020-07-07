package com.ca.migration.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ca.migration.constants.MigrationQueryConstants;

@Entity
@Table(name = "MDO_FILTER")
@NamedNativeQueries({
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_FILTER_BY_FITERID_NAME, query = MigrationQueryConstants.MDO_FILTER_BY_FITERID_QUERY, resultClass = Filter.class),
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_FILTER_DELETE_NAME, query = MigrationQueryConstants.MDO_FILTER_DELETE_QUERY, resultClass = Filter.class)
})

public class Filter {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filterid_generator")
	@SequenceGenerator(name = "filterid_generator", sequenceName = MigrationQueryConstants.MDO_FILTERID_SEQ_NAME, allocationSize = 1)
	@Column(name = "FILTERID")
	private Long filterID;

	@Column(name = "USERTENANTID", nullable = false)
	private Long userTenantID;

	@Column(name = "FILTERNAME", nullable = false)
	private String filterName;

	@Column(name = "APPID")
	private String appID;

	@Column(name = "APPVERSION")
	private String appVersion;

	@Column(name = "PLATFORM")
	private String platform;

	@Column(name = "PLATFORMVERSION")
	private String platformVersion;

	@Column(name = "DEVICE")
	private String device;

	@Column(name = "DEVICEMODEL")
	private String deviceModel;

	@Column(name = "CARRIER")
	private String carrier;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "STATE")
	private String state;

	@Column(name = "CITY")
	private String city;

	@Column(name = "BOOKMARKED")
	private Boolean bookmarked;

	public Long getFilterID() {
		return filterID;
	}

	public Long getUserTenantID() {
		return userTenantID;
	}

	public String getFilterName() {
		return filterName;
	}

	public String getAppID() {
		return appID;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public String getPlatform() {
		return platform;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public String getDevice() {
		return device;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public String getCarrier() {
		return carrier;
	}

	public String getCountry() {
		return country;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public Boolean getBookmarked() {
		return bookmarked;
	}

	public void setFilterID(Long filterID) {
		this.filterID = filterID;
	}

	public void setUserTenantID(Long userTenantID) {
		this.userTenantID = userTenantID;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setBookmarked(Boolean bookmarked) {
		this.bookmarked = bookmarked;
	}



}
