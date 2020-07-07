package com.ca.migration.vo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CrptoKeyPKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "tenant_id")
	private String tenantID;

	@Column(name = "app_id")
	private String appID;

	@Column(name = "key_version")
	private Long keyVersion;

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

	public Long getKeyVersion() {
		return keyVersion;
	}

	public void setKeyVersion(Long keyVersion) {
		this.keyVersion = keyVersion;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof CrptoKeyPKey))
			return false;
		CrptoKeyPKey obj = (CrptoKeyPKey) o;
		return Objects.equals(this.getAppID(), obj.getAppID()) && Objects.equals(this.getTenantID(), obj.getTenantID())
				&& Objects.equals(this.getKeyVersion(), obj.getKeyVersion());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getTenantID(), this.getAppID(), this.getKeyVersion());

	}

	@Override
	public String toString() {
		return "TENANT_ID: "+this.getTenantID()+", APP_ID: "+this.getAppID()+", key_version: "+this.getKeyVersion();
	}
}
