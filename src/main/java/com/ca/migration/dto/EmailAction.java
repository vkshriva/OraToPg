package com.ca.migration.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import com.ca.migration.constants.MigrationQueryConstants;

@Entity
@Table(name = "MDO_EMAIL_ACTION")
@NamedNativeQuery(name = MigrationQueryConstants.MDO_EMAIL_ACTION_BY_ACTION_ID_NAME, query = MigrationQueryConstants.MDO_EMAIL_ACTION_BY_ACTION_ID_QUERY, resultClass = EmailAction.class)
public class EmailAction implements Serializable {

	@Id
	@Column(name = "ACTIONID")
	private Long actionID;

	public Long getActionID() {
		return actionID;
	}

	public void setActionID(Long actionID) {
		this.actionID = actionID;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * @Id
	 *
	 * @OneToOne
	 *
	 * @JoinColumn(name="ACTIONID") private Action action;
	 */
	@Column(name = "ACTIONVALUE")
	private String actionValue;

	public String getActionValue() {
		return actionValue;
	}

	public void setActionValue(String actionValue) {
		this.actionValue = actionValue;
	}

}
