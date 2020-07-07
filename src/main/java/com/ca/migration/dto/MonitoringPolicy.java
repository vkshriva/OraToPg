package com.ca.migration.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ca.migration.constants.MigrationQueryConstants;

@Entity
@Table(name = "MDO_MONITORING_POLICY")

@NamedNativeQueries({
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_MONITORING_POLICY_BY_POLICY_ID_NAME, query = MigrationQueryConstants.MDO_MONITORING_POLICY_BY_POLICY_ID_QUERY, resultClass = MonitoringPolicy.class),
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_MONITORING_POLICY_DELETE_NAME, query = MigrationQueryConstants.MDO_MONITORING_POLICY_DELETE_QUERY, resultClass = MonitoringPolicy.class)

})


public class MonitoringPolicy {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "policy_generator")
	@SequenceGenerator(name = "policy_generator", sequenceName = MigrationQueryConstants.MDO_POLICYID_SEQ_NAME, allocationSize = 1)
	@Column(name = "POLICYID")
	private Long policyID;

	@Column(name = "POLICYNAME", nullable = false)
	private String policyName;

	public Long getPolicyID() {
		return policyID;
	}

	public void setPolicyID(Long policyID) {
		this.policyID = policyID;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

}
