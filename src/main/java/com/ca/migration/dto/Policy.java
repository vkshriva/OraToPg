package com.ca.migration.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NamedNativeQuery;

import com.ca.migration.constants.MigrationQueryConstants;

@Entity
@Table(name = "MDO_POLICY", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "POLICYID", "ATTRIBUTEID", "NV_PAIR", "FILTERID" }) })
@NamedNativeQuery(name = MigrationQueryConstants.MDO_POLICY_BY_POLICY_ID_NAME, query = MigrationQueryConstants.MDO_POLICY_BY_POLICY_ID_QUERY, resultClass = Policy.class)
public class Policy {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ruleid_generator")
	@SequenceGenerator(name = "ruleid_generator", sequenceName = MigrationQueryConstants.MDO_RULEID_SEQ_NAME, allocationSize = 1)
	@Column(name = "RULEID")
	private Long ruleID;

	@Column(name = "POLICYID", nullable = false)
	private Long policyID;

	@Column(name = "ATTRIBUTEID", nullable = false)
	private Integer attributeID;

	@Column(name = "WARN_THRESHOLD")
	private Long warnThresholed;

	@Column(name = "CRITICAL_THRESHOLD")
	private Long criticalThreshold;

	@Column(name = "BOOLEAN_VALUE")
	private byte booleanValue;

	@Column(name = "FREQUENCY")
	private Integer frequency;

	@Column(name = "ENUMERATION")
	private String enumeration;

	@Column(name = "EXPRESSION")
	private String expression;

	@Column(name = "REPEAT_COUNT")
	private Integer repeatCount;

	@Column(name = "TIME_INTERVAL")
	private Long timeInterval;

	@Column(name = "NV_PAIR")
	private String nvPair;

	@Column(name = "ATTR_RULE_NAME", nullable = false)
	private String attrRuleName;

	@Column(name = "FILTERID")
	private Long filterID;

	public Long getRuleID() {
		return ruleID;
	}

	public Long getPolicyID() {
		return policyID;
	}

	public Integer getAttributeID() {
		return attributeID;
	}

	public Long getWarnThresholed() {
		return warnThresholed;
	}

	public Long getCriticalThreshold() {
		return criticalThreshold;
	}

	public byte getBooleanValue() {
		return booleanValue;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public String getEnumeration() {
		return enumeration;
	}

	public String getExpression() {
		return expression;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public Long getTimeInterval() {
		return timeInterval;
	}

	public String getNvPair() {
		return nvPair;
	}

	public String getAttrRuleName() {
		return attrRuleName;
	}

	public Long getFilterID() {
		return filterID;
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

	public void setWarnThresholed(Long warnThresholed) {
		this.warnThresholed = warnThresholed;
	}

	public void setCriticalThreshold(Long criticalThreshold) {
		this.criticalThreshold = criticalThreshold;
	}

	public void setBooleanValue(byte booleanValue) {
		this.booleanValue = booleanValue;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public void setEnumeration(String enumeration) {
		this.enumeration = enumeration;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public void setTimeInterval(Long timeInterval) {
		this.timeInterval = timeInterval;
	}

	public void setNvPair(String nvPair) {
		this.nvPair = nvPair;
	}

	public void setAttrRuleName(String attrRuleName) {
		this.attrRuleName = attrRuleName;
	}

	public void setFilterID(Long filterID) {
		this.filterID = filterID;
	}


}
