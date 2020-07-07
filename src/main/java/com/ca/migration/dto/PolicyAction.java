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
@Table(name = "MDO_POLICY_ACTION")
@NamedNativeQuery(name = MigrationQueryConstants.MDO_POLICY_ACTION_BY_RULE_ID_POLICYID_NAME, query = MigrationQueryConstants.MDO_POLICY_ACTION_BY_RULE_ID_POLICYID_QUERY, resultClass = PolicyAction.class)
public class PolicyAction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "policyActionid_generator")
	@SequenceGenerator(name = "policyActionid_generator", sequenceName = MigrationQueryConstants.MDO_POLICY_ACTION_ID_SEQ_NAME, allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "RULEID", nullable = false)
	private Long ruleID;

	@Column(name = "POLICYID", nullable = false)
	private Long policyID;

	@Column(name = "ATTRIBUTEID", nullable = false)
	private Integer attributeID;

	@Column(name = "ACTIONID", nullable = false)
	private Long actionID;

	@Column(name = "LOCALE", nullable = false)
	private String locale;

	public Long getId() {
		return id;
	}

	public Long getRuleID() {
		return ruleID;
	}

	public Long getPolicyID() {
		return policyID;
	}

	public Integer getAttributeID() {
		return attributeID;
	}

	public Long getActionID() {
		return actionID;
	}

	public String getLocale() {
		return locale;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRuleID(Long ruleID) {
		this.ruleID = ruleID;
	}

	public void setPolicyID(Long policyID) {
		this.policyID = policyID;
	}

	public void setAttributeID(Integer attributeID) {
		this.attributeID = attributeID;
	}

	public void setActionID(Long actionID) {
		this.actionID = actionID;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}


}
