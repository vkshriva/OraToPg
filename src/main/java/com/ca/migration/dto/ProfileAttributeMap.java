package com.ca.migration.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import com.ca.migration.constants.MigrationQueryConstants;
import com.ca.migration.vo.ProfileAttributeMapKey;

@Entity
@Table(name = "MDO_PROFILE_ATTRIBUTE_MAP")
@NamedNativeQueries({
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_PROFILE_ATTRIBUTE_MAP_BY_PROFILE_ID_NAME, query = MigrationQueryConstants.MDO_PROFILE_ATTRIBUTE_MAP_BY_PROFILE_ID_QUERY, resultClass = ProfileAttributeMap.class),
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_PROFILE_ATTRIBUTE_MAP_DELETE_NAME, query = MigrationQueryConstants.MDO_PROFILE_ATTRIBUTE_MAP_DELETE_QUERY, resultClass = ProfileAttributeMap.class)
})

public class ProfileAttributeMap {

	@EmbeddedId
	private ProfileAttributeMapKey pKey;

	@Column(name = "VALUE")
	private String value;

	/*
	 * @ManyToOne
	 *
	 * @JoinColumn(name ="PROFILEID") private MonitoringProfile monitoringProfile;
	 *
	 *
	 *
	 * public MonitoringProfile getMonitoringProfile() { return monitoringProfile; }
	 *
	 * public void setMonitoringProfile(MonitoringProfile monitoringProfile) {
	 * this.monitoringProfile = monitoringProfile; }
	 */
	public ProfileAttributeMapKey getpKey() {
		return pKey;
	}

	public String getValue() {
		return value;
	}

	public void setpKey(ProfileAttributeMapKey pKey) {
		this.pKey = pKey;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
