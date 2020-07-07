package com.ca.migration.vo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ApplicationPKey implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "TENANT_ID", nullable = false)
	private String tenantID;

	@Column(name = "APP_ID", nullable = false)
	private String appID;

	public String getTenantID() {
		return tenantID;
	}

	public String getAppID() {
		return appID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ApplicationPKey))
			return false;
		ApplicationPKey obj = (ApplicationPKey) o;
		return Objects.equals(this.getAppID(), obj.getAppID()) && Objects.equals(this.getTenantID(), obj.getTenantID());

	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getAppID(), this.getTenantID());

	}

	@Override
	public String toString() {
		return "TENANT_ID: "+this.getTenantID()+", APP_ID: "+this.getAppID();
	}

}
