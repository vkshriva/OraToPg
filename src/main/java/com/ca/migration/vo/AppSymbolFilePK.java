package com.ca.migration.vo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AppSymbolFilePK implements Serializable {

	@Column(name = "TENANT_ID", nullable = false)
	private String tenantID;

	@Column(name = "APP_ID", nullable = false)
	private String appID;

	@Column(name = "APP_VERSION", nullable = false)
	private String appVersion;

	@Column(name = "PLATFORM", nullable = false)
	private String platform;

	@Column(name = "FILE_CHECKSUM", nullable = false)
	private String fileCheckSum;

	public String getTenantID() {
		return tenantID;
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

	public String getFileCheckSum() {
		return fileCheckSum;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
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

	public void setFileCheckSum(String fileCheckSum) {
		this.fileCheckSum = fileCheckSum;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof AppSymbolFilePK))
			return false;
		AppSymbolFilePK obj = (AppSymbolFilePK) o;
		return Objects.equals(this.getTenantID(), obj.getTenantID()) && Objects.equals(this.getAppID(), obj.getAppID())
				&& Objects.equals(this.getAppVersion(), obj.getAppVersion())
				&& Objects.equals(this.getPlatform(), obj.getPlatform())
				&& Objects.equals(this.getFileCheckSum(), obj.getFileCheckSum());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getTenantID(), this.getAppID(), this.getAppVersion(), this.getPlatform(),
				this.getFileCheckSum());

	}

	@Override
	public String toString() {
		return "TENANT_ID: "+this.getTenantID()+", APP_ID: "+this.getAppID()+", APP_VERSION: "+this.getAppVersion()+", PLATFORM: "+this.getPlatform()+"FILE_CHECKSUM: "+this.getFileCheckSum();
	}

}
