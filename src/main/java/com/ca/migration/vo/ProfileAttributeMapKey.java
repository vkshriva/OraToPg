package com.ca.migration.vo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProfileAttributeMapKey implements Serializable {

	@Column(name = "PROFILEID")
	private Long profileID;

	@Column(name = "ATTRIBUTEID")
	private Long attributeID;

	public Long getProfileID() {
		return profileID;
	}

	public Long getAttributeID() {
		return attributeID;
	}

	public void setProfileID(Long profileID) {
		this.profileID = profileID;
	}

	public void setAttributeID(Long attributeID) {
		this.attributeID = attributeID;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ProfileAttributeMapKey))
			return false;
		ProfileAttributeMapKey obj = (ProfileAttributeMapKey) o;
		return Objects.equals(this.getProfileID(), obj.getProfileID())
				&& Objects.equals(this.getAttributeID(), obj.getAttributeID());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getProfileID(), this.getAttributeID());

	}

	@Override
	public String toString() {
		return "PROFILEID: "+this.getProfileID()+", ATTRIBUTEID: "+this.getAttributeID();
	}

}
