package com.ca.migration.vo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TenantAppBAExtPKey implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "TENANTID", nullable = false)
	private String tenantID;

	@Column(name = "APPID", nullable = false)
	private String appID;

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

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof TenantAppBAExtPKey))
			return false;
		TenantAppBAExtPKey obj = (TenantAppBAExtPKey) o;
		return Objects.equals(this.getAppID(), obj.getAppID()) && Objects.equals(this.getTenantID(), obj.getTenantID());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getTenantID(), this.getAppID());

	}

	@Override
	public String toString() {
		return "TENANT_ID: "+this.getTenantID()+", APP_ID: "+this.getAppID();
	}
}
