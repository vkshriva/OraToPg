package com.ca.migration.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.ca.migration.constants.MigrationQueryConstants;
import com.ca.migration.vo.ApplicationPKey;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_APPLICATION_BY_TENANT_ID_NAME, query = MigrationQueryConstants.MDO_APPLICATION_BY_TENANT_ID_QUERY, resultClass = Application.class) })

@Table(name = "MDO_APPLICATION")
public class Application {

	@EmbeddedId
	private ApplicationPKey pkey;

	@Column(name = "APP_KEY", nullable = false)
	private String appKey;

	@Column(name = "ENC_KEY")
	private String encKey;

	@Column(name = "STATUS")
	private Boolean status;

	@Column(name = "PROFILE_ATTACHED")
	private Boolean profileAttached;

	@Lob
	@Column(name = "APP_LOGO")
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] appLogo;


	@Column(name = "ISLOGO")
	private byte isLogo;

	@Column(name = "SECURE_APP")
	private Boolean secureApp;

	@Column(name = "IS_DELETED")
	private Boolean isDeleted;

	public ApplicationPKey getPkey() {
		return pkey;
	}

	public String getAppKey() {
		return appKey;
	}

	public String getEncKey() {
		return encKey;
	}

	public Boolean getStatus() {
		return status;
	}

	public Boolean getProfileAttached() {
		return profileAttached;
	}

	public byte[] getAppLogo() {
		return appLogo;
	}

	public byte getIsLogo() {
		return isLogo;
	}

	public Boolean getSecureApp() {
		return secureApp;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setPkey(ApplicationPKey pkey) {
		this.pkey = pkey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setEncKey(String encKey) {
		this.encKey = encKey;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setProfileAttached(Boolean profileAttached) {
		this.profileAttached = profileAttached;
	}

	public void setAppLogo(byte[] appLogo) {
		this.appLogo = appLogo;
	}

	public void setIsLogo(byte isLogo) {
		this.isLogo = isLogo;
	}

	public void setSecureApp(Boolean secureApp) {
		this.secureApp = secureApp;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
