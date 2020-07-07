package com.ca.migration.vo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TenantAppConfigPKey implements Serializable {

	@Column(name = "TENANTID", nullable = false)
	private String tenantID;

	@Column(name = "APPID", nullable = false)
	private String appID;

	@Column(name = "CONFIGKEY", nullable = false)
	private String configKey;

	public String getTenantID() {
		return tenantID;
	}

	public String getAppID() {
		return appID;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof TenantAppConfigPKey))
			return false;
		TenantAppConfigPKey obj = (TenantAppConfigPKey) o;
		return Objects.equals(this.getAppID(), obj.getAppID()) && Objects.equals(this.getTenantID(), obj.getTenantID())
				&& Objects.equals(this.getConfigKey(), obj.getConfigKey());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getTenantID(), this.getAppID(), this.getConfigKey());

	}

	@Override
	public String toString() {
		return "TENANT_ID: "+this.getTenantID()+", APP_ID: "+this.getAppID()+", CONFIGKEY: "+this.getConfigKey();
	}

}
