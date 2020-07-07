package com.ca.migration.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.migration.constants.MigrationQueryConstants;
import com.ca.migration.dao.OracleDao;
import com.ca.migration.dao.PostgresDao;
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
import com.ca.migration.exception.MigrationOracleException;
import com.ca.migration.exception.MigrationPostgresException;
import com.ca.migration.utility.MigrationReaderUtility;
import com.ca.migration.vo.ApplicationPKey;

@Service("migrationService")
public class MigrationService {

	@Autowired
	private OracleService oracleServc;

	@Autowired
	private PostgresService postgresServc;

	@Autowired
	private OracleDao oracleDAO;

	@Autowired
	private PostgresDao postgresDAO;

	private Map<Long, Long> insertedActionIDMap = new HashMap<>();
	private Map<Long, Long> insertedProfileIDMap = new HashMap<>();
	private Map<Long, Long> insertedPolicyIDMap = new HashMap<>();
	private Map<Long, Long> filterIDSequenceMap = new HashMap<>();
	private Map<Long, Long> userTenantSequenceMap = null;

	Logger logger = Logger.getLogger(MigrationService.class);

	@Transactional(rollbackFor = Exception.class)

	public void fetchAndInsertMDOTables(String tenantID) {

		try {

			if (null == tenantID || 0 == tenantID.length()) {
				logger.debug("Tenant ID is null or empty");
				return;
			}

			if (!postgresDAO.tenantSpeceficValidation(tenantID)) {
				/*
				 * Tenant Data is already present
				 */
				return;
			}

			/* 1. Fetch and Insert Data in MDO_APPLICATION */
			List<Application> applicationList = oracleServc.getMdoApplication(tenantID);
			if (null == applicationList || applicationList.size() == 0) {
				logger.debug("**********No Data Found for Tenant***************************************************"
						+ tenantID);
				return;
			}

			postgresServc.insertMdoApplication(applicationList);

			logger.debug("Insertion Completed:  MDO_APPLICATION");

			/*
			 * 1.1 Fetch and Insert data in MDO_FILTER_USER with sequence
			 * MDO_USERTENANTID_SEQ MDO_FILTER
			 */
			List<FilterUser> filterUserList = oracleServc.getFilterUser(tenantID);
			userTenantSequenceMap = postgresServc.insertFilterUser(filterUserList);
			logger.debug("Insertion Completed: MDO_FILTER_USER");

			if (null == userTenantSequenceMap || userTenantSequenceMap.size() == 0) {

				String exMsg = "No MDO_FILTER_USER iss available for  tenantID " + tenantID;
				logger.debug(exMsg);

			} else {
				Set<Map.Entry<Long, Long>> userTenantSequenceSet = userTenantSequenceMap.entrySet();

				for (Map.Entry<Long, Long> userTenantSequenceEntry : userTenantSequenceSet) {
					Long oldUserTenantID = userTenantSequenceEntry.getKey();
					List<Filter> filterList = oracleServc.getFilter(oldUserTenantID);
					if (null != filterList && filterList.size() > 0) {
						Long newUserTenantID = userTenantSequenceEntry.getValue();
						Map<Long, Long> returnMap = postgresServc.insertFilter(filterList, newUserTenantID);
						filterIDSequenceMap = Stream
								.concat(filterIDSequenceMap.entrySet().stream(), returnMap.entrySet().stream())
								.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
					}
				}
			}

			/*
			 * 1.6 Fetch and Insert Data in MDO_MONITORING_PROFILE MDO_PROFILE_ATTRIBUTE_MAP
			 */

			List<MonitoringProfile> monitoringProfileList = oracleServc.getMdoMonitoringProfile(tenantID);
			if (null == monitoringProfileList || monitoringProfileList.size() == 0) {
				logger.debug("No Data for MDO_MONITORING_PROFILE  tenantID " + tenantID);
			} else {
				insertedProfileIDMap = postgresServc.insertMdoMonitoringProfile(monitoringProfileList);
				logger.debug("Insertion Completed: MDO_MONITORING_PROFILE");

				Set<Map.Entry<Long, Long>> insertedProfileIDMapSet = insertedProfileIDMap.entrySet();

				for (Map.Entry<Long, Long> insertedProfileIDMapEntry : insertedProfileIDMapSet) {
					Long oldProfileID = insertedProfileIDMapEntry.getKey();
					Long newProfileID = insertedProfileIDMapEntry.getValue();
					List<ProfileAttributeMap> profileAttributeMapList = oracleServc
							.getMdoProfileAttributMap(oldProfileID);
					for (ProfileAttributeMap ProfileAttributeObj : profileAttributeMapList) {
						ProfileAttributeObj.getpKey().setProfileID(newProfileID);
						postgresServc.insertMdoProfileAttributeMap(ProfileAttributeObj);
					}

					logger.debug("Insertion Completed: MDO_PROFILE_ATTRIBUTE_MAP for ProfileID " + oldProfileID
							+ " with new ProfileID " + newProfileID);

				}

			}

			/*
			 * for (FilterUser filterUserObj : filterUserList) { List<Filter> filterList =
			 * oracleServc.getFilter(filterUserObj.getUserTenantID()); Map<Long, Long>
			 * returnMap = postgresServc.insertFilter(filterList);
			 *
			 * filterIDSequenceMap = Stream .concat(filterIDSequenceMap.entrySet().stream(),
			 * returnMap.entrySet().stream()) .collect(Collectors.toMap(entry ->
			 * entry.getKey(), entry -> entry.getValue()));
			 *
			 * }
			 */

			logger.debug("Insertion Completed: MDO_FILTER");

			/*
			 * Adding DUMMYAPPFORTENANT for each tenant so that it will copy all
			 * Tenantspecific data as well
			 *
			 */

			Application dummyApplication = new Application();
			ApplicationPKey pkey = new ApplicationPKey();
			dummyApplication.setPkey(pkey);
			dummyApplication.getPkey().setAppID(MigrationQueryConstants.DUMMYAPPFORTENANT);
			applicationList.add(dummyApplication);

			for (Application application : applicationList) {

				String appID = application.getPkey().getAppID();

				/* 1.2 Fetch and Insert Data in MDO_CRYPTO_KEY */
				List<CryptoKey> cryptoKeyList = oracleServc.getMdoCryptoKey(tenantID, appID);

				if (null != cryptoKeyList && cryptoKeyList.size() > 0) {
					postgresServc.insertMdoCryptoKey(cryptoKeyList);
					logger.debug("Insertion Completed: MDO_CRYPTO_KEY");
				}

				/* 1.3 Fetch and Insert Data in MDO_APP_SYMBOL_FILE */
				List<AppSymbolFile> appSymbolFileList = oracleServc.getAppSymbolFile(tenantID, appID);
				if (null == appSymbolFileList || appSymbolFileList.size() == 0) {
					logger.debug("No Data for MDO_APP_SYMBOL FILE tenantID " + tenantID + " appID: " + appID);
				} else {
					postgresServc.insertAppSymbolFile(appSymbolFileList);
					logger.debug("Insertion Completed: MDO_APP_SYMBOL_FILE");
				}

				/* 1.4 Fetch and Insert Data in MDO_TENANT_APP_BAEXT */
				TenantAppBAExt tenantAppBAExtObj = oracleServc.getTenantAppBAExt(tenantID, appID);
				if (null == tenantAppBAExtObj) {
					logger.debug("No Data for MDO_TENANT_APP_BA_EXT tenantID " + tenantID + " appID: " + appID);

				} else {
					postgresServc.insertTenantAppBAExt(tenantAppBAExtObj);
					logger.debug("Insertion Completed: MDO_TENANT_APP_BAEXT");
				}

				/*
				 * 1.5 Fetch and Insert Data in MDO_TENANT_APP_CONFIG
				 */

				List<TenantAppConfig> tenantAppConfigList = oracleServc.getTenantAppConfig(tenantID, appID);
				if (null == tenantAppConfigList || tenantAppConfigList.size() == 0) {
					logger.debug("No Data for MDO_TENANT_APP_CONFIG  tenantID " + tenantID + " appID: " + appID);
				} else {
					postgresServc.insertTenantAppConfig(tenantAppConfigList);
					logger.debug("Insertion Completed: MDO_TENANT_APP_BAEXT");
				}

				/*
				 * 1.8 Fetch and Insert Data in MDO_TENANT_APP_POLICY MDO_MONITORING_POLICY
				 *
				 * MDO_POLICY MDO_POLICY_ACTION MDO_ACTION MDO_Email_Action
				 */
				Long oldPolicyID = null;
				Long newPolicyID = null;
				TenantAppPolicy tenantAppPolicyObj = oracleServc.getTenantAppPolicy(tenantID, appID);
				if (null == tenantAppPolicyObj) {
					logger.debug("No Data for MDO_TENANT_APP_POLICY tenantID " + tenantID + " appID: " + appID);
					/*throw new MigrationOracleException(
							"No Data for MDO_TENANT_APP_POLICY tenantID" + tenantID + "appID: " + appID);
					 */
				} else {
					oldPolicyID = tenantAppPolicyObj.getPolicyID();
					logger.debug("Insertion Started: MDO_TENANT_APP_POLICY policyID: " + oldPolicyID);
					newPolicyID = insertedPolicyIDMap.get(oldPolicyID);

					if (null == newPolicyID) {
						/*
						 * Insert Data
						 */
						MonitoringPolicy monitoringPolicyObj = oracleServc.getMonitoringPolicy(oldPolicyID);
						postgresServc.insertMonitoringPolicy(monitoringPolicyObj);
						logger.debug("Insertion Completed: MDO_MONITORING_POLICY");
						newPolicyID = monitoringPolicyObj.getPolicyID();
						insertedPolicyIDMap.put(oldPolicyID, newPolicyID);

					}

					tenantAppPolicyObj.setPolicyID(newPolicyID);
					postgresServc.insertTenantAppPolicy(tenantAppPolicyObj);
					logger.debug("Insertion Completed: MDO_TENANT_APP_POLICY  newpolicyID: " + newPolicyID);

					Long oldRuleID = null;
					Long newRuleID = null;
					Long oldFilterID = null;
					Long newFilterID = null;
					Long oldActionID = null;
					Long newActionID = null;

					List<Policy> policyList = oracleServc.getPolicy(oldPolicyID);
					for (Policy policy : policyList) {
						oldRuleID = policy.getRuleID();
						oldFilterID = policy.getFilterID();
						newFilterID = filterIDSequenceMap.get(oldFilterID);
						policy.setPolicyID(newPolicyID);
						policy.setFilterID(newFilterID);
						logger.debug("Insertion Started: MDO_POLICY  newpolicyID: " + newPolicyID + "newFilterID :"
								+ newFilterID);
						postgresServc.insertPolicy(policy);
						logger.debug("Insertion Completed: MDO_POLICY  newRuleID: " + newRuleID);
						newRuleID = policy.getRuleID();

						List<PolicyAction> policyActionList = oracleServc.getPolicyAction(oldRuleID, oldPolicyID);
						for (PolicyAction policyActionObj : policyActionList) {
							oldActionID = policyActionObj.getActionID();
							logger.debug("Insertion Started: MDO_POLICY_ACTION  oldActionID: " + oldActionID);
							newActionID = insertedActionIDMap.get(oldActionID);

							/*
							 * Check if Data as not be inserted for this Action yet.If no insert else use
							 * the inserted data
							 */

							if (null == newActionID) {
								/*
								 * Insert Data
								 */

								Action actionObj = oracleServc.getAction(oldActionID);
								postgresServc.insertAction(actionObj);
								logger.debug("Insertion Completed : MDO_ACTION  newActionID: " + newActionID);
								newActionID = actionObj.getActionId();
								insertedActionIDMap.put(oldActionID, newActionID);
								EmailAction emailActionObj = oracleServc.getEmailAction(oldActionID);
								emailActionObj.setActionID(newActionID);
								postgresServc.insertEmailAction(emailActionObj);
								logger.debug("Insertion completed: MDO_EMAIL_ACTION  newActionID: " + newActionID);

							}

							policyActionObj.setPolicyID(newPolicyID);
							policyActionObj.setRuleID(newRuleID);
							policyActionObj.setActionID(newActionID);
							postgresServc.insertPolicyAction(policyActionObj);
							logger.debug("Insertion Completed: MDO_POLICY_ACTION  newPolicyID: " + newPolicyID
									+ " newRuleID: " + newRuleID + " newActionID; " + newActionID);

						}

					}

				}

				Long oldProfileID = null;
				Long newProfileID = null;

				List<TenantAppProfile> tenantAppProfileList = oracleServc.getMdoTenantAppProfile(tenantID, appID);

				if (null == tenantAppProfileList) {
					logger.debug("No Data for MDO_TENANT_APP_PROFILE tenantID " + tenantID + " appID: " + appID);

				} else {

					for (TenantAppProfile tenantAppProfileObj : tenantAppProfileList) {
						logger.debug(
								"Insertion Started  MDO_TENANT_APP_PROFILE tenantID " + tenantID + " appID: " + appID);
						oldProfileID = tenantAppProfileObj.getProfileID();
						logger.debug("oldProfileID:-----" + oldProfileID + "---");
						Long enableProfile = MigrationReaderUtility.getEnableDisableProfile("ENABLE.TENANT.PROFILEID");
						Long disableProfile = MigrationReaderUtility
								.getEnableDisableProfile("DISABLE.TENANT.PROFILEID");
						logger.debug("enableProfile:---" + enableProfile + "-----");
						logger.debug("disableProfile:---" + disableProfile + "---");

						if (null != oldProfileID && oldProfileID.equals(enableProfile)) {
							logger.debug("IT HAS  ENABLE.TENANT.PROFILEID  MDO_TENANT_APP_PROFILE tenantID " + tenantID
									+ " appID: " + appID);
							newProfileID = MigrationReaderUtility
									.getEnableDisableProfile("NEW.ENABLE.TENANT.PROFILEID");
						}

						else if (null != oldProfileID && oldProfileID.equals(disableProfile)) {
							logger.debug("IT HAS  DISABLE.TENANT.PROFILEID  MDO_TENANT_APP_PROFILE tenantID " + tenantID
									+ " appID: " + appID);
							newProfileID = MigrationReaderUtility
									.getEnableDisableProfile("NEW.DISABLE.TENANT.PROFILEID");
						}

						else if (oldProfileID == MigrationQueryConstants.PROFILE_ID_DEFAULT
								|| oldProfileID == MigrationQueryConstants.PROFILE_ID_TENANT_MAU_PROFILE) {
							/*
							 * This is to handle 'DEFAULT' and Tenant_MAU_Profile
							 */
							newProfileID = oldProfileID;
						} else {
							newProfileID = insertedProfileIDMap.get(oldProfileID);
						}

						/*
						 * Check if Data as not be inserted for this MonitoringProfile yet.If no insert
						 * else use the inserted data
						 */
						/*
						 * if (null == newProfileID) {
						 *
						 * Insert Data
						 *
						 *
						 * MonitoringProfile monitoringProfileObj =
						 * oracleServc.getMdoMonitoringProfile(tenantID, oldProfileID); if (null ==
						 * monitoringProfileObj) { newProfileID = oldProfileID; } else {
						 *
						 * postgresServc.insertMdoMonitoringProfile(monitoringProfileObj); newProfileID
						 * = monitoringProfileObj.getProfileID(); insertedProfileIDMap.put(oldProfileID,
						 * newProfileID);
						 *
						 * List<ProfileAttributeMap> profileAttributeMapList = oracleServc
						 * .getMdoProfileAttributMap(oldProfileID);
						 *
						 * for (ProfileAttributeMap ProfileAttributeObj : profileAttributeMapList) {
						 *
						 * ProfileAttributeObj.getpKey().setProfileID(newProfileID);
						 *
						 * postgresServc.insertMdoProfileAttributeMap(ProfileAttributeObj); }
						 *
						 * } }
						 *
						 */
						logger.debug("newProfileID  MDO_TENANT_APP_PROFILE---" + newProfileID + "---");
						tenantAppProfileObj.setProfileID(newProfileID);
						postgresServc.insertMdoTenantAppProfile(tenantAppProfileObj);
						logger.debug("Insertion Completed  MDO_TENANT_APP_PROFILE tenantID " + tenantID + " appID: "
								+ appID);

					}

				}
			}

		}

		catch (

				Exception ex) {
			logger.debug("***********************************************Error for Migrating  Tenant***************"
					+ tenantID);

			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(ex.getMessage());

		}

	}

	public void printTenantData(String tenantID, String database) {

		if ("ORACLE".equalsIgnoreCase(database)) {
			oracleDAO.printTenantData(tenantID);
		} else {
			postgresDAO.printTenantData(tenantID);
		}

	}

	@Transactional
	public void deleteMDOTables(String tenantID) {

		try {

			if (null == tenantID || 0 == tenantID.length()) {
				logger.error("Tenant ID is null or empty");
				return;
			}

			List<Application> applicationList = null;
			/*
			 * Delete MDO_APPLICATION
			 */

			applicationList = postgresDAO.deleteMdoApplication(tenantID);
			if (null == applicationList || applicationList.size() == 0) {
				logger.debug(
						"**********No Data Found for Tenant***********************************************" + tenantID);
				// return;
			}

			/*
			 * Adding DUMMYAPPFORTENANT for each tenant so that it will copy all
			 * Tenantspecific data as well
			 *
			 */

			Application dummyApplication = new Application();
			ApplicationPKey pkey = new ApplicationPKey();
			dummyApplication.setPkey(pkey);
			dummyApplication.getPkey().setAppID(MigrationQueryConstants.DUMMYAPPFORTENANT);
			dummyApplication.getPkey().setTenantID(tenantID);
			applicationList.add(dummyApplication);

			applicationList.stream().filter(Objects::nonNull).forEach(application -> {

				String appID = application.getPkey().getAppID();

				/* Delete MDO_CRYPTO_KEY */
				List<CryptoKey> cryptoKeyList = postgresDAO.deleteMdoCryptoKey(tenantID, appID);

				if (null != cryptoKeyList && cryptoKeyList.size() > 0) {
					logger.debug("Deletion Completed: MDO_CRYPTO_KEY for tenantID =" + tenantID + " appID " + appID);
				} else {
					logger.debug("NO DATA MDO_CRYPTO_KEY for tenantID =" + tenantID + " appID " + appID);
				}

				List<AppSymbolFile> appSymbolFileList = postgresDAO.deleteMdoAppSymbol(tenantID, appID);
				if (null != appSymbolFileList && appSymbolFileList.size() > 0) {
					logger.debug("Deletion Completed: MDO_APP_SYMBOL for tenantID =" + tenantID + " appID " + appID);
				} else {
					logger.debug("NO DATA MDO_APP_SYMBOL for tenantID =" + tenantID + " appID " + appID);
				}

				TenantAppBAExt tenantAppBAExtObj = postgresDAO.deleteTenantAppBAExt(tenantID, appID);
				if (null != tenantAppBAExtObj) {
					logger.debug(
							"Deletion Completed: MDO_TENANT_APP_BAEXT for tenantID =" + tenantID + " appID " + appID);
				} else {
					logger.debug("NO DATA MDO_TENANT_APP_BAEXT for tenantID =" + tenantID + " appID " + appID);
				}

				List<TenantAppConfig> tenantAppConfigList = postgresDAO.deleteTenantAppConfig(tenantID, appID);
				if (null != tenantAppConfigList && tenantAppConfigList.size() > 0) {
					logger.debug(
							"Deletion Completed: MDO_TENANT_APP_CONFIG for tenantID =" + tenantID + " appID " + appID);
				} else {
					logger.debug("NO DATA MDO_TENANT_APP_CONFIG for tenantID =" + tenantID + " appID " + appID);
				}

				logger.debug(
						"Deletion Started: MDO_TENANT_APP_POLICY,MDO_MONITORING_POLICY,MDO_POLICY,MDO_POLICY_ACTION,MDO_ACTION,MDO_EMAIL_ACTION for tenantID ="
								+ tenantID + " appID " + appID);
				postgresDAO.deletePolicyRelatedTblsData(tenantID, appID);
				logger.debug(
						"Deletion Completed: MDO_TENANT_APP_POLICY,MDO_MONITORING_POLICY,MDO_POLICY,MDO_POLICY_ACTION,MDO_ACTION,MDO_EMAIL_ACTION for tenantID ="
								+ tenantID + " appID " + appID);

			});

			/*
			 * Delete MDO_TENANT_APP_PROFILE MDO_MONITORING_PROFILE
			 * MDO_PROFILE_ATTRIBUTE_MAP
			 *
			 */

			logger.debug(
					"Deletion Started: MDO_TENANT_APP_PROFILE,MDO_MONITORING_PROFILE,MDO_PROFILE_ATTRIBUTE_MAP for tenantID ="
							+ tenantID);
			postgresDAO.deletePrflTablData(tenantID);
			logger.debug(
					"Deletion Completed: MDO_TENANT_APP_PROFILE,MDO_MONITORING_PROFILE,MDO_PROFILE_ATTRIBUTE_MAP for tenantID ="
							+ tenantID);

			logger.debug("Deletion Started: MDO_FILTER_USER, MDO_FILTER for tenantID =" + tenantID);
			postgresDAO.deleteFilterTablelData(tenantID);
			logger.debug("Deletion Started: MDO_FILTER_USER, MDO_FILTER for tenantID =" + tenantID);

		}

		catch (Exception ex) {
			logger.debug("**************************************Error while deleting Tenant***************" + tenantID);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(ex.getMessage());

		}

	}

	/*
	 * public boolean tenantMAUProfileValidation() { return
	 * postgresDAO.tenantMAUProfileValidation(); }
	 */

	public boolean isCompleteDataDeleted(String tenantID) {

		return (postgresDAO.isDataDeleted(tenantID, MigrationQueryConstants.MDO_APPLICATION_BY_TENANT_ID_NAME,
				"MDO_APPLICATION")
				&& postgresDAO.isDataDeleted(tenantID, MigrationQueryConstants.MDO_CRYPTO_KEY_BY_TENANT_ID_NAME,
						"MDO_CRYPTO_KEY")
				&& postgresDAO.isDataDeleted(tenantID, MigrationQueryConstants.APPSYMBOLFILE_TENANTID_NAME,
						"MDO_APP_SYMBOL_FILE")
				&& postgresDAO.isDataDeleted(tenantID, MigrationQueryConstants.TENANT_APP_BAEXT_TENANTID_NAME,
						"MDO_TENANT_APP_BAEXT")
				&& postgresDAO.isDataDeleted(tenantID, MigrationQueryConstants.MDO_TENANT_APP_CONFIG_BY_TENANT_ID_NAME,
						"MDO_TENANT_APP_CONFIG")
				&&

				postgresDAO.isFilterDataDeleted(tenantID) && postgresDAO.isProfileDataDeleted(tenantID)
				&& postgresDAO.isPolicyDataDeleted(tenantID)

				);
	}

	@Transactional
	public void deleteAlertSpecificTables(String tenantID) {

		try {

			if (null == tenantID || 0 == tenantID.length()) {
				logger.error("Tenant ID is null or empty");
				return;
			}

			List<TenantAppPolicy> tenantAppPolicyList = null;
			/*
			 * Get TenanntAppPolicyList
			 */

			tenantAppPolicyList = postgresDAO.getTenantAppPolicyByTenantID(tenantID);
			if (null == tenantAppPolicyList || tenantAppPolicyList.size() == 0) {
				logger.debug(
						"**********No Data Found for Tenant***********************************************" + tenantID);
				// return;
			}

			tenantAppPolicyList.stream().filter(Objects::nonNull).forEach(tenantAppPolicy -> {

				String appID = tenantAppPolicy.getPkey().getAppID();
				logger.debug(
						"Deletion Started: MDO_TENANT_APP_POLICY,MDO_MONITORING_POLICY,MDO_POLICY,MDO_POLICY_ACTION,MDO_ACTION,MDO_EMAIL_ACTION for tenantID ="
								+ tenantID + " appID " + appID);
				postgresDAO.deletePolicyRelatedTblsData(tenantID, appID);
				logger.debug(
						"Deletion Completed: MDO_TENANT_APP_POLICY,MDO_MONITORING_POLICY,MDO_POLICY,MDO_POLICY_ACTION,MDO_ACTION,MDO_EMAIL_ACTION for tenantID ="
								+ tenantID + " appID " + appID);

			});

			logger.debug("Deletion Started: MDO_FILTER_USER, MDO_FILTER for tenantID =" + tenantID);
			postgresDAO.deleteFilterTablelData(tenantID);
			logger.debug("Deletion Started: MDO_FILTER_USER, MDO_FILTER for tenantID =" + tenantID);

		}

		catch (Exception ex) {
			logger.debug("**************************************Error while deleting Tenant***************" + tenantID);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(ex.getMessage());

		}

	}

	@Transactional(rollbackFor = Exception.class)

	public void fetchAndInsertAlertSpecificTables(String tenantID) {

		try {

			if (null == tenantID || 0 == tenantID.length()) {
				logger.debug("Tenant ID is null or empty");
				return;
			}

			List<Application> applicationList = oracleServc.getMdoApplication(tenantID);
			if (null == applicationList || applicationList.size() == 0) {
				logger.info(
						"**********No Data Found for Tenant in MDO APPLICATION*************************************************"
								+ tenantID);
			}

			/* 1. Fetch and Insert Data in MDO_TENANT_APP_POLICY */
			List<TenantAppPolicy> tenantAppPolicyList = oracleServc.getTenantAppPolicy(tenantID);
			if (null == tenantAppPolicyList || tenantAppPolicyList.size() == 0) {
				logger.debug("**********No Data Found for Tenant***************************************************"
						+ tenantID);
				return;
			}

			/*
			 * 1.1 Fetch and Insert data in MDO_FILTER_USER with sequence
			 * MDO_USERTENANTID_SEQ MDO_FILTER
			 */
			List<FilterUser> filterUserList = oracleServc.getFilterUser(tenantID);
			userTenantSequenceMap = postgresServc.insertFilterUser(filterUserList);
			logger.debug("Insertion Completed: MDO_FILTER_USER for TenantID " + tenantID);

			if (null == userTenantSequenceMap || userTenantSequenceMap.size() == 0) {

				String exMsg = "No MDO_FILTER_USER is available for  tenantID " + tenantID;
				logger.debug(exMsg);

			} else {
				Set<Map.Entry<Long, Long>> userTenantSequenceSet = userTenantSequenceMap.entrySet();

				for (Map.Entry<Long, Long> userTenantSequenceEntry : userTenantSequenceSet) {
					Long oldUserTenantID = userTenantSequenceEntry.getKey();
					List<Filter> filterList = oracleServc.getFilter(oldUserTenantID);
					if (null != filterList && filterList.size() > 0) {
						Long newUserTenantID = userTenantSequenceEntry.getValue();
						Map<Long, Long> returnMap = postgresServc.insertFilter(filterList, newUserTenantID);
						filterIDSequenceMap = Stream
								.concat(filterIDSequenceMap.entrySet().stream(), returnMap.entrySet().stream())
								.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
					}
				}
			}

			logger.debug("Insertion Completed: MDO_FILTER");

			for (TenantAppPolicy application : tenantAppPolicyList) {

				String appID = application.getPkey().getAppID();

				/*
				 * 1.8 Fetch and Insert Data in MDO_TENANT_APP_POLICY MDO_MONITORING_POLICY
				 *
				 * MDO_POLICY MDO_POLICY_ACTION MDO_ACTION MDO_Email_Action
				 */
				Long oldPolicyID = null;
				Long newPolicyID = null;
				TenantAppPolicy tenantAppPolicyObj = oracleServc.getTenantAppPolicy(tenantID, appID);
				if (null == tenantAppPolicyObj) {
					logger.debug("No Data for MDO_TENANT_APP_POLICY tenantID " + tenantID + " appID: " + appID);
					/*throw new MigrationOracleException(
							"No Data for MDO_TENANT_APP_POLICY tenantID" + tenantID + "appID: " + appID);
					 */
				} else {
					oldPolicyID = tenantAppPolicyObj.getPolicyID();
					logger.debug("Insertion Started: MDO_TENANT_APP_POLICY policyID: " + oldPolicyID);
					newPolicyID = insertedPolicyIDMap.get(oldPolicyID);

					if (null == newPolicyID) {
						/*
						 * Insert Data
						 */
						MonitoringPolicy monitoringPolicyObj = oracleServc.getMonitoringPolicy(oldPolicyID);
						postgresServc.insertMonitoringPolicy(monitoringPolicyObj);
						logger.debug("Insertion Completed: MDO_MONITORING_POLICY");
						newPolicyID = monitoringPolicyObj.getPolicyID();
						insertedPolicyIDMap.put(oldPolicyID, newPolicyID);

					}

					tenantAppPolicyObj.setPolicyID(newPolicyID);
					postgresServc.insertTenantAppPolicy(tenantAppPolicyObj);
					logger.debug("Insertion Completed: MDO_TENANT_APP_POLICY  newpolicyID: " + newPolicyID);

					Long oldRuleID = null;
					Long newRuleID = null;
					Long oldFilterID = null;
					Long newFilterID = null;
					Long oldActionID = null;
					Long newActionID = null;

					List<Policy> policyList = oracleServc.getPolicy(oldPolicyID);
					for (Policy policy : policyList) {
						oldRuleID = policy.getRuleID();
						oldFilterID = policy.getFilterID();
						newFilterID = filterIDSequenceMap.get(oldFilterID);
						policy.setPolicyID(newPolicyID);
						policy.setFilterID(newFilterID);
						logger.debug("Insertion Started: MDO_POLICY  newpolicyID: " + newPolicyID + "newFilterID :"
								+ newFilterID);
						postgresServc.insertPolicy(policy);
						logger.debug("Insertion Completed: MDO_POLICY  newRuleID: " + newRuleID);
						newRuleID = policy.getRuleID();

						List<PolicyAction> policyActionList = oracleServc.getPolicyAction(oldRuleID, oldPolicyID);
						for (PolicyAction policyActionObj : policyActionList) {
							oldActionID = policyActionObj.getActionID();
							logger.debug("Insertion Started: MDO_POLICY_ACTION  oldActionID: " + oldActionID);
							newActionID = insertedActionIDMap.get(oldActionID);

							/*
							 * Check if Data as not be inserted for this Action yet.If no insert else use
							 * the inserted data
							 */

							if (null == newActionID) {
								/*
								 * Insert Data
								 */

								Action actionObj = oracleServc.getAction(oldActionID);
								postgresServc.insertAction(actionObj);
								logger.debug("Insertion Completed : MDO_ACTION  newActionID: " + newActionID);
								newActionID = actionObj.getActionId();
								insertedActionIDMap.put(oldActionID, newActionID);
								EmailAction emailActionObj = oracleServc.getEmailAction(oldActionID);
								emailActionObj.setActionID(newActionID);
								postgresServc.insertEmailAction(emailActionObj);
								logger.debug("Insertion completed: MDO_EMAIL_ACTION  newActionID: " + newActionID);

							}

							policyActionObj.setPolicyID(newPolicyID);
							policyActionObj.setRuleID(newRuleID);
							policyActionObj.setActionID(newActionID);
							postgresServc.insertPolicyAction(policyActionObj);
							logger.debug("Insertion Completed: MDO_POLICY_ACTION  newPolicyID: " + newPolicyID
									+ " newRuleID: " + newRuleID + " newActionID; " + newActionID);

						}

					}

				}

			}

		}

		catch (

				Exception ex) {
			logger.debug("***********************************************Error for Migrating  Tenant***************"
					+ tenantID);

			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(ex.getMessage());

		}

	}

}
