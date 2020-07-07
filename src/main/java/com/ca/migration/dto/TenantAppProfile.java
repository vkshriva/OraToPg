package com.ca.migration.dto;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

import com.ca.migration.constants.MigrationQueryConstants;
import com.ca.migration.vo.TenantAppProfileKey;

@Entity
@Table(name = "MDO_TENANT_APP_PROFILE")
@NamedNativeQueries({
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_TENANT_APP_PROFILE_BY_TENANT_ID_APP_ID_NAME, query = MigrationQueryConstants.MDO_TENANT_APP_PROFILE_BY_TENANT_ID_APP_ID_QUERY, resultClass = TenantAppProfile.class),
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_TENANT_APP_PROFILE_DELETE_NAME, query = MigrationQueryConstants.MDO_TENANT_APP_PROFILE_DELETE_QUERY, resultClass = TenantAppProfile.class),
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_TENANT_APP_PROFILE_BY_TENANT_ID_NAME, query = MigrationQueryConstants.MDO_TENANT_APP_PROFILE_BY_TENANT_ID_QUERY, resultClass = TenantAppProfile.class)
})

public class TenantAppProfile {

	@EmbeddedId
	private TenantAppProfileKey key;

	@Column(name = "PROFILEID")
	private long profileID;

	public long getProfileID() {
		return profileID;
	}

	public void setProfileID(long profileID) {
		this.profileID = profileID;
	}

	/*
	 * @ManyToOne
	 *
	 * @ElementCollection(fetch=FetchType.EAGER)
	 *
	 * @JoinColumn(name="PROFILEID") private MonitoringProfile monitoringProfile;
	 *
	 * public MonitoringProfile getMonitoringProfile() { return monitoringProfile; }
	 *
	 *
	 * public void setMonitoringProfile(MonitoringProfile monitoringProfile) {
	 * this.monitoringProfile = monitoringProfile; }
	 */

	public TenantAppProfileKey getKey() {
		return key;
	}

	public void setKey(TenantAppProfileKey key) {
		this.key = key;
	}

}


