package com.ca.migration.vo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TenantAppProfileKey implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "TENANTID")
	private String tenantID;

	@Column(name = "APPID")
	private String appID;

	@Column(name = "APPVERSION")
	private String appVersion;

	public String getTenantID() {
		return tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof TenantAppProfileKey))
			return false;
		TenantAppProfileKey obj = (TenantAppProfileKey) o;
		return Objects.equals(this.getAppID(), obj.getAppID()) && Objects.equals(this.getTenantID(), obj.getTenantID())
				&& Objects.equals(this.getAppVersion(), obj.getAppVersion());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getTenantID(), this.getAppID(), this.getAppVersion());

	}

	@Override
	public String toString() {
		return "TENANT_ID: "+this.getTenantID()+", APP_ID: "+this.getAppID()+", APPVERSION: "+this.getAppVersion();
	}

}
