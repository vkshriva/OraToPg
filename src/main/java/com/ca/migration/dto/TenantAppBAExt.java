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
import com.ca.migration.vo.TenantAppBAExtPKey;

@Entity
@Table(name = "MDO_TENANT_APP_BAEXT")
@NamedNativeQueries({
	@NamedNativeQuery(name = MigrationQueryConstants.TENANT_APP_BAEXT_APPIDANDTENANTID_NAME, query = MigrationQueryConstants.TENANT_APP_BAEXT_APPIDANDTENANTID_QUERY, resultClass = TenantAppBAExt.class),
	@NamedNativeQuery(name = MigrationQueryConstants.TENANT_APP_BAEXT_TENANTID_NAME, query = MigrationQueryConstants.TENANT_APP_BAEXT_TENANTID_QUERY, resultClass = TenantAppBAExt.class)
})

public class TenantAppBAExt {

	@EmbeddedId
	private TenantAppBAExtPKey pKey;

	@Lob
	@Column(name = "BAEXTJS")
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] baExtJS;

	public TenantAppBAExtPKey getpKey() {
		return pKey;
	}

	public byte[] getBaExtJS() {
		return baExtJS;
	}

	public void setpKey(TenantAppBAExtPKey pKey) {
		this.pKey = pKey;
	}

	public void setBaExtJS(byte[] baExtJS) {
		this.baExtJS = baExtJS;
	}

}
