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
@Table(name = "MDO_ACTION")
@NamedNativeQuery(name = MigrationQueryConstants.MDO_ACTION_BY_ACTION_ID_NAME, query = MigrationQueryConstants.MDO_ACTION_BY_ACTION_ID_QUERY, resultClass = Action.class)
public class Action {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actionid_generator")
	@SequenceGenerator(name = "actionid_generator", sequenceName = MigrationQueryConstants.MDO_ACTION_SEQ_NAME, allocationSize = 1)
	@Column(name = "ACTIONID")
	private Long actionId;

	public Long getActionId() {
		return actionId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	@Column(name = "ACTIONTYPE")
	private String actionType;



}
