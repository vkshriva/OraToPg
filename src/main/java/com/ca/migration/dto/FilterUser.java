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
@Table(name = "MDO_FILTER_USER")
@NamedNativeQuery(name = MigrationQueryConstants.MDO_FILTER_USER_BY_TENANT_NAME, query = MigrationQueryConstants.MDO_FILTER_USER_BY_TENANT_QUERY, resultClass = FilterUser.class)
public class FilterUser {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usertenantid_generator")
	@SequenceGenerator(name = "usertenantid_generator", sequenceName = MigrationQueryConstants.MDO_USERTENANTID_SEQ_NAME, allocationSize = 1)
	@Column(name = "USERTENANTID")
	private Long userTenantID;

	@Column(name = "USERNAME", nullable = false)
	private String userName;

	@Column(name = "TENANT", nullable = false)
	private String tenant;

	public Long getUserTenantID() {
		return userTenantID;
	}

	public String getUserName() {
		return userName;
	}

	public String getTenant() {
		return tenant;
	}

	public void setUserTenantID(Long userTenantID) {
		this.userTenantID = userTenantID;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

}
