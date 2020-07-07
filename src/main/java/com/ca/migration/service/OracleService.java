package com.ca.migration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ca.migration.dao.OracleDao;
import com.ca.migration.dto.Action;
import com.ca.migration.dto.AppSymbolFile;
import com.ca.migration.dto.Application;
import com.ca.migration.dto.CryptoKey;
import com.ca.migration.dto.EmailAction;
import com.ca.migration.dto.Filter;
import com.ca.migration.dto.FilterUser;
import com.ca.migration.dto.MonitoringPolicy;
import com.ca.migration.dto.MonitoringProfile;
import com.ca.migration.dto.Policy;
import com.ca.migration.dto.PolicyAction;
import com.ca.migration.dto.ProfileAttributeMap;
import com.ca.migration.dto.TenantAppBAExt;
import com.ca.migration.dto.TenantAppConfig;
import com.ca.migration.dto.TenantAppPolicy;
import com.ca.migration.dto.TenantAppProfile;

@Service("oracleService")
public class OracleService {

	@Autowired
	private OracleDao dao;

	public List<Application> getMdoApplication(String tenantID) {

		return dao.getMdoApplication(tenantID);

	}

	/*
	 * Fetch the data from below table 1.MDO_MONITORING_PROFILE
	 * 2.MDO_TENANT_APP_PROFILE 3.MDO_PROFILE_ATTRIBUTE_MAP
	 *
	 */
	public List<TenantAppProfile> getMdoTenantAppProfile(String tenantID, String appID) {

		List<TenantAppProfile> tenantAppProfileList = dao.getMdoTenantAppProfile(tenantID, appID);

		return tenantAppProfileList;
	}

	public List<CryptoKey> getMdoCryptoKey(String tenantID, String appID) {

		return dao.getMdoCryptoKey(tenantID, appID);
	}

	public List<MonitoringProfile> getMdoMonitoringProfile(String tenantID) {
		return dao.getMdoMonitoringProfile(tenantID);
	}

	public List<ProfileAttributeMap> getMdoProfileAttributMap(long profileID) {
		return dao.getMdoProfileAttributMap(profileID);
	}

	public TenantAppPolicy getMdoTenantAppPolicy(String tenantID, String appID) {

		TenantAppPolicy returnObj = null;
		returnObj = dao.getMdoTenantAppPolicy(tenantID, appID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MdoTenantAppPolicy for tenantID =" + tenantID
		 * + " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */
		return returnObj;
	}

	public MonitoringPolicy getMdoMonitoringPolicy(long policyID) {
		MonitoringPolicy returnObj = null;
		returnObj = dao.getMdoMonitoringPolicy(policyID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MonitoringPolicy for policyID =" + policyID;
		 * throw new MigrationOracleException(expMsg); }
		 */
		return returnObj;

	}

	public List<AppSymbolFile> getAppSymbolFile(String tenantID, String appID) {
		List<AppSymbolFile> returnObj = null;
		returnObj = dao.getAppSymbolFile(tenantID, appID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MDOAppSymbolFile for for tenantID ==" +
		 * tenantID + " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */
		return returnObj;

	}

	public TenantAppBAExt getTenantAppBAExt(String tenantID, String appID) {

		TenantAppBAExt returnObj = null;
		returnObj = dao.getTenantAppBAExt(tenantID, appID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MdoTenantAppBAExt for tenantID =" + tenantID +
		 * " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */

		return returnObj;
	}

	public List<TenantAppConfig> getTenantAppConfig(String tenantID, String appID) {

		List<TenantAppConfig> returnObj = null;
		returnObj = dao.getTenantAppConfig(tenantID, appID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MdoTenantAppBAExt for tenantID =" + tenantID +
		 * " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */

		return returnObj;
	}

	public TenantAppPolicy getTenantAppPolicy(String tenantID, String appID) {

		TenantAppPolicy returnObj = null;
		returnObj = dao.getTenantAppPolicy(tenantID, appID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MdoTenantAppBAExt for tenantID =" + tenantID +
		 * " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */

		return returnObj;
	}

	public MonitoringPolicy getMonitoringPolicy(long policyID) {

		MonitoringPolicy returnObj = null;
		returnObj = dao.getMonitoringPolicy(policyID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MdoTenantAppBAExt for tenantID =" + tenantID +
		 * " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */

		return returnObj;
	}

	public List<FilterUser> getFilterUser(String tenantID) {

		List<FilterUser> returnObj = null;
		returnObj = dao.getFilterUser(tenantID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MdoTenantAppBAExt for tenantID =" + tenantID +
		 * " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */

		return returnObj;
	}

	public List<Policy> getPolicy(long policyID) {

		List<Policy> returnObj = null;
		returnObj = dao.getPolicy(policyID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MdoTenantAppBAExt for tenantID =" + tenantID +
		 * " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */

		return returnObj;
	}

	public List<Filter> getFilter(long userTenanatID) {
		List<Filter> returnObj = null;
		returnObj = dao.getFilter(userTenanatID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MdoTenantAppBAExt for tenantID =" + tenantID +
		 * " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */

		return returnObj;

	}

	public List<PolicyAction> getPolicyAction(long ruleID, long policyID) {
		List<PolicyAction> returnObj = null;
		returnObj = dao.getPolicyAction(ruleID, policyID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MdoTenantAppBAExt for tenantID =" + tenantID +
		 * " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */

		return returnObj;
	}

	public Action getAction(long actionID) {
		Action returnObj = null;
		returnObj = dao.getAction(actionID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MdoTenantAppBAExt for tenantID =" + tenantID +
		 * " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */

		return returnObj;

	}

	public EmailAction getEmailAction(long actionID) {
		EmailAction returnObj = null;
		returnObj = dao.getEmailAction(actionID);
		/*
		 * if (null == returnObj) { String expMsg =
		 * "Exception while fetching data MdoTenantAppBAExt for tenantID =" + tenantID +
		 * " appID =" + appID; throw new MigrationOracleException(expMsg); }
		 */

		return returnObj;

	}

	/*	public void printTenantData(String tenantID) {
		dao.printTenantData(tenantID);
	}*/

	public List<TenantAppPolicy> getTenantAppPolicy(String tenantID) {

		return dao.getTenantAppPolicy(tenantID);

	}

}
