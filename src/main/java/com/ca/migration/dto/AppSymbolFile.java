package com.ca.migration.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.ca.migration.constants.MigrationQueryConstants;
import com.ca.migration.vo.AppSymbolFilePK;

@Entity
@Table(name = "MDO_APP_SYMBOL_FILE")
@NamedNativeQueries({
	@NamedNativeQuery(name = MigrationQueryConstants.APPSYMBOLFILE_APPIDANDTENANTID_NAME, query = MigrationQueryConstants.APPSYMBOLFILE_APPIDANDTENANTID_QUERY, resultClass = AppSymbolFile.class),
	@NamedNativeQuery(name = MigrationQueryConstants.APPSYMBOLFILE_TENANTID_NAME, query = MigrationQueryConstants.APPSYMBOLFILE_TENANTID_QUERY, resultClass = AppSymbolFile.class) })

public class AppSymbolFile {

	@EmbeddedId
	private AppSymbolFilePK pKey;

	@Column(name = "FILE_NAME", nullable = false)
	private String fileName;

	@Column(name = "FILE_TYPE", nullable = false)
	private String fileType;

	@Column(name = "FILE_SIZE", nullable = false)
	private String fileSize;

	@Column(name = "FILE_PATH")
	private String filePath;

	@Column(name = "STATUS")
	private String status;

	@Lob
	@Column(name = "FILE_CONTENT", nullable = false)
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] fileContent;

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getStatus() {
		return status;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public AppSymbolFilePK getpKey() {
		return pKey;
	}

	public void setpKey(AppSymbolFilePK pKey) {
		this.pKey = pKey;
	}
}
