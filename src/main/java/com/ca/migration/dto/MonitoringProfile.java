package com.ca.migration.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ca.migration.constants.MigrationQueryConstants;

@Entity

@Table(name = "MDO_MONITORING_PROFILE")
@NamedNativeQuery(name = MigrationQueryConstants.MDO_MONITORING_PROFILE_BY_TENANT_ID_NAME, query = MigrationQueryConstants.MDO_MONITORING_PROFILE_BY_TENANT_ID_QUERY, resultClass = MonitoringProfile.class)


/* @org.hibernate.annotations.Entity(selectBeforeUpdate = true) */
public class MonitoringProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileid_generator")
	@SequenceGenerator(name = "profileid_generator", sequenceName = MigrationQueryConstants.MDO_PROFILEID_SEQ, allocationSize = 1)
	@Column(name = "PROFILEID")
	private Long profileID;

	@Column(name = "PROFILENAME")
	private String profileName;

	@Column(name = "TENANTID")
	private String tenantID;

	/*
	 * @OneToMany(mappedBy="monitoringProfile") private Collection<TenantAppProfile>
	 * tennantAppProfileList = new ArrayList<>();
	 *
	 * public Collection<TenantAppProfile> getTennantAppProfileList() { return
	 * tennantAppProfileList; }
	 *
	 * public void setTennantAppProfileList(Collection<TenantAppProfile>
	 * tennantAppProfileList) { this.tennantAppProfileList = tennantAppProfileList;
	 * }
	 *
	 *
	 * @OneToMany(mappedBy="monitoringProfile") private
	 * Collection<ProfileAttributeMap> profileAttributeMapList = new ArrayList<>();
	 *
	 *
	 *
	 * public Collection<ProfileAttributeMap> getProfileAttributeMapList() { return
	 * profileAttributeMapList; }
	 *
	 * public void setProfileAttributeMapList(Collection<ProfileAttributeMap>
	 * profileAttributeMapList) { this.profileAttributeMapList =
	 * profileAttributeMapList; }
	 */

	public Long getProfileID() {
		return profileID;
	}

	public String getProfileName() {
		return profileName;
	}

	public String getTenantID() {
		return tenantID;
	}

	public void setProfileID(Long profileID) {
		this.profileID = profileID;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

}
