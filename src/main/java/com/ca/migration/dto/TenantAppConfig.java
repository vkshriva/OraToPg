package com.ca.migration.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import com.ca.migration.constants.MigrationQueryConstants;
import com.ca.migration.vo.TenantAppConfigPKey;

@Entity
@Table(name = "MDO_TENANT_APP_CONFIG")
@NamedNativeQueries({
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_TENANT_APP_CONFIG_BY_TENANT_ID_APP_ID_NAME, query = MigrationQueryConstants.MDO_TENANT_APP_CONFIG_BY_TENANT_ID_APP_ID_QUERY, resultClass = TenantAppConfig.class),
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_TENANT_APP_CONFIG_BY_TENANT_ID_NAME, query = MigrationQueryConstants.MDO_TENANT_APP_CONFIG_BY_TENANT_ID_QUERY, resultClass = TenantAppConfig.class)
})

public class TenantAppConfig {

	@EmbeddedId
	private TenantAppConfigPKey pKey;

	@Column(name = "CONFIGVALUE", nullable = false)
	private String configValue;

	public TenantAppConfigPKey getpKey() {
		return pKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setpKey(TenantAppConfigPKey pKey) {
		this.pKey = pKey;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

}
