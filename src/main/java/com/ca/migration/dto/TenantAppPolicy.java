package com.ca.migration.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import com.ca.migration.constants.MigrationQueryConstants;
import com.ca.migration.vo.TenantAppPolicyPKey;

@Entity
@Table(name = "MDO_TENANT_APP_POLICY")
@NamedNativeQueries({
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_TENANT_APP_POLICY_BY_TENANT_ID_APP_ID_NAME, query = MigrationQueryConstants.MDO_TENANT_APP_POLICY_BY_TENANT_ID_APP_ID_QUERY, resultClass = TenantAppPolicy.class),
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_TENANT_APP_POLICY_BY_TENANT_ID_NAME, query = MigrationQueryConstants.MDO_TENANT_APP_POLICY_BY_TENANT_ID_QUERY, resultClass = TenantAppPolicy.class)
})

public class TenantAppPolicy {

	@EmbeddedId
	private TenantAppPolicyPKey pkey;

	@Column(name = "POLICYID", nullable = false)
	private Long policyID;

	public TenantAppPolicyPKey getPkey() {
		return pkey;
	}

	public void setPkey(TenantAppPolicyPKey pkey) {
		this.pkey = pkey;
	}

	public Long getPolicyID() {
		return policyID;
	}

	public void setPolicyID(Long policyID) {
		this.policyID = policyID;
	}

}
