package com.ca.migration.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WhoColumns {
    
	@Column(name="CREATED")
	private Timestamp created;
	
	@Column(name="LAST_UPDATED")
	private Timestamp lastUpdated;

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
