package com.ca.migration.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.ca.migration.constants.MigrationQueryConstants;
import com.ca.migration.vo.CrptoKeyPKey;

@Entity
@Table(name = "MDO_CRYPTO_KEY")
@NamedNativeQueries({
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_CRYPTO_KEY_BY_TENANT_ID_NAME, query = MigrationQueryConstants.MDO_CRYPTO_KEY_BY_TENANT_ID_QUERY, resultClass = CryptoKey.class),
	@NamedNativeQuery(name = MigrationQueryConstants.MDO_CRYPTO_KEY_BY_TENANT_ID_APP_ID_NAME, query = MigrationQueryConstants.MDO_CRYPTO_KEY_BY_TENANT_ID_APP_ID_QUERY, resultClass = CryptoKey.class)
})

public class CryptoKey {

	@EmbeddedId
	private CrptoKeyPKey key;

	@Column(name = "usage")
	private String usage;

	@Column(name = "algorithm")
	private String algorithm;

	@Column(name = "storage")
	private String storage;

	@Column(name = "key_size")
	private Integer keySize;

	@Column(name = "public_key")
	private String publicKey;

	@Column(name = "private_key")
	private String privateKey;

	@Lob
	@Column(name = "key_blob")
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] keyBlob;

	@Column(name = "blob_format")
	private String blobFormat;

	@Column(name = "state")
	private String state;

	@Column(name = "attributes")
	private String attributes;

	@Column(name = "expiry_date")
	private Timestamp expiryDate;

	@Column(name = "last_updated")
	private Timestamp lastUpdated;

	public CrptoKeyPKey getKey() {
		return key;
	}

	public String getUsage() {
		return usage;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getStorage() {
		return storage;
	}

	public Integer getKeySize() {
		return keySize;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public byte[] getKeyBlob() {
		return keyBlob;
	}

	public String getBlobFormat() {
		return blobFormat;
	}

	public String getState() {
		return state;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setKey(CrptoKeyPKey key) {
		this.key = key;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public void setKeySize(Integer keySize) {
		this.keySize = keySize;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public void setKeyBlob(byte[] keyBlob) {
		this.keyBlob = keyBlob;
	}

	public void setBlobFormat(String blobFormat) {
		this.blobFormat = blobFormat;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
