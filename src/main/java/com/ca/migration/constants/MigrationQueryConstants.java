package com.ca.migration.constants;

public class MigrationQueryConstants {

	public static final String APPSYMBOLFILE_APPIDANDTENANTID_NAME = "AppSymbolFile.byAppIDAndTenantID";
	public static final String APPSYMBOLFILE_APPIDANDTENANTID_QUERY = "SELECT * FROM MDO_APP_SYMBOL_FILE WHERE APP_ID=:appID AND TENANT_ID=:tenantID";

	public static final String TENANT_APP_BAEXT_APPIDANDTENANTID_NAME = "TenantAppBAExt.byAppIDAndTenantID";
	public static final String TENANT_APP_BAEXT_APPIDANDTENANTID_QUERY = "SELECT * FROM MDO_TENANT_APP_BAEXT WHERE APPID=:appID AND TENANTID=:tenantID";

	public static final String MDO_USERTENANTID_SEQ_NAME = "MDO_USERTENANTID_SEQ";
	public static final String MDO_FILTER_USER_BY_TENANT_NAME = "FilterUser.byTenantID";
	public static final String MDO_FILTER_USER_BY_TENANT_QUERY = "SELECT * FROM MDO_FILTER_USER WHERE TENANT=:tenantID";

	public static final String MDO_FILTERID_SEQ_NAME = "MDO_FILTERID_SEQ";
	public static final String MDO_FILTER_BY_FITERID_NAME = "Filter.byUserTenantIDAndAppID";
	public static final String MDO_FILTER_BY_FITERID_QUERY = "SELECT * FROM MDO_FILTER WHERE USERTENANTID=:userTenanatID";

	public static final String MDO_RULEID_SEQ_NAME = "MDO_RULEID_SEQ";
	public static final String MDO_POLICY_BY_POLICY_ID_NAME = "Policy.byPolicyID";
	public static final String MDO_POLICY_BY_POLICY_ID_QUERY = "SELECT * FROM MDO_POLICY WHERE POLICYID=:policyID";

	public static final String MDO_ACTION_SEQ_NAME = "MDO_ACTION_ID_SEQ";
	public static final String MDO_ACTION_BY_ACTION_ID_NAME = "Action.byActionID";
	public static final String MDO_ACTION_BY_ACTION_ID_QUERY = "SELECT * FROM MDO_ACTION WHERE ACTIONID=:actionID";

	public static final String MDO_APPLICATION_BY_TENANT_ID_NAME = "Application.byTenantID";
	public static final String MDO_APPLICATION_BY_TENANT_ID_QUERY = "SELECT * FROM MDO_APPLICATION WHERE TENANT_ID=:tenantID";

	public static final String MDO_EMAIL_ACTION_BY_ACTION_ID_NAME = "EmailAction.byActionID";
	public static final String MDO_EMAIL_ACTION_BY_ACTION_ID_QUERY = "SELECT * FROM MDO_EMAIL_ACTION WHERE ACTIONID=:actionID";

	public static final String MDO_CRYPTO_KEY_BY_TENANT_ID_APP_ID_NAME = "CryptoKey.byTenantIDAndappID";
	public static final String MDO_CRYPTO_KEY_BY_TENANT_ID_APP_ID_QUERY = "SELECT * FROM MDO_CRYPTO_KEY WHERE TENANT_ID=:tenantID and APP_ID=:appID";

	public static final String MDO_POLICY_ACTION_ID_SEQ_NAME = "MDO_POLICY_ACTION_ID_SEQ";
	public static final String MDO_POLICY_ACTION_BY_RULE_ID_POLICYID_NAME = "PolicyAction.byRuleIDAndPolicyID";
	public static final String MDO_POLICY_ACTION_BY_RULE_ID_POLICYID_QUERY = "SELECT * FROM MDO_POLICY_ACTION  WHERE RULEID=:ruleID AND POLICYID=:policyID";

	public static final String MDO_PROFILE_ATTRIBUTE_MAP_BY_PROFILE_ID_NAME = "ProfileAttributeMap.byProfileID";
	public static final String MDO_PROFILE_ATTRIBUTE_MAP_BY_PROFILE_ID_QUERY = "SELECT * FROM MDO_PROFILE_ATTRIBUTE_MAP WHERE  PROFILEID=:profileID";

	public static final String MDO_TENANT_APP_CONFIG_BY_TENANT_ID_APP_ID_NAME = "TenantAppConfig.byTenantIDAndAppID";
	public static final String MDO_TENANT_APP_CONFIG_BY_TENANT_ID_APP_ID_QUERY = "SELECT * FROM MDO_TENANT_APP_CONFIG WHERE TENANTID=:tenantID AND APPID=:appID";

	public static final String MDO_TENANT_APP_POLICY_BY_TENANT_ID_APP_ID_NAME = "TenantAppPolicy.byTeanatIDandAppID";
	public static final String MDO_TENANT_APP_POLICY_BY_TENANT_ID_APP_ID_QUERY = "SELECT * FROM MDO_TENANT_APP_POLICY WHERE TENANTID=:tenantID AND APPID=:appID";

	public static final String MDO_POLICYID_SEQ_NAME = "MDO_POLICYID_SEQ";
	public static final String MDO_MONITORING_POLICY_BY_POLICY_ID_NAME = "MonitoringPolicy.byPolicyID";
	public static final String MDO_MONITORING_POLICY_BY_POLICY_ID_QUERY = "SELECT * FROM MDO_MONITORING_POLICY WHERE POLICYID=:policyID";

	public static final String MDO_TENANT_APP_PROFILE_BY_TENANT_ID_APP_ID_NAME = "TenantAppProfile.byTenantIDAndappID";
	public static final String MDO_TENANT_APP_PROFILE_BY_TENANT_ID_APP_ID_QUERY = "SELECT * FROM MDO_TENANT_APP_PROFILE WHERE TENANTID=:tenantID and APPID=:appID";

	public static final String MDO_TENANT_APP_TXN_AVG_BY_TENANT_AND_APP_ID_NAME = "TenantAppTxnAvg.TenantIDAndAppID";
	public static final String MDO_TENANT_APP_TXN_AVG_BY_TENANT_AND_APP_ID_QUERY = "SELECT * FROM MDO_TENANT_APP_TXN_AVG WHERE USERTENANTID=:userTenantID AND APPID=:appID";

	public static final String MDO_PROFILEID_SEQ = "MDO_PROFILEID_SEQ";
	public static final String MDO_MONITORING_PROFILE_BY_TENANT_ID_NAME = "MonitoringProfile.byTenantIDAndProfileID";
	// public static final String
	// MDO_MONITORING_PROFILE_BY_TENANT_ID_PROFILE_ID_QUERY = "SELECT * FROM
	// MDO_MONITORING_PROFILE WHERE (TENANTID=:tenantID OR TENANTID='*') AND
	// PROFILEID=:profileID AND UPPER(PROFILENAME) != 'DEFAULT'";
	public static final String MDO_MONITORING_PROFILE_BY_TENANT_ID_QUERY = "SELECT * FROM MDO_MONITORING_PROFILE WHERE TENANTID=:tenantID";

	public static final String DUMMYAPPFORTENANT = "dummyappfortenant";
	public static final long PROFILE_ID_DEFAULT = 1L;
	public static final long PROFILE_ID_TENANT_MAU_PROFILE = 2L;

	public static final String TENENT_MAU_PROFILE_VALIDATION_QUERY = "SELECT COUNT(*) FROM mdo_monitoring_profile WHERE UPPER(profilename)='TENANT_MAU_PROFILE'";

	/*
	 * Delete functionality
	 */
	public static final String MDO_MONITORING_POLICY_DELETE_QUERY = "DELETE FROM MDO_MONITORING_POLICY WHERE POLICYID=:policyID";
	public static final String MDO_MONITORING_POLICY_DELETE_NAME = "MDO_MONITORING_POLICY_DELETE_NAME";

	public static final String MDO_TENANT_APP_PROFILE_DELETE_NAME = "MDO_TENANT_APP_PROFILE_DELETE_NAME";
	public static final String MDO_TENANT_APP_PROFILE_DELETE_QUERY = "DELETE FROM MDO_TENANT_APP_PROFILE WHERE TENANTID=:tenantID";

	public static final String MDO_PROFILE_ATTRIBUTE_MAP_DELETE_NAME = "MDO_PROFILE_ATTRIBUTE_MAP_DELETE_NAME";
	public static final String MDO_PROFILE_ATTRIBUTE_MAP_DELETE_QUERY = "DELETE FROM MDO_PROFILE_ATTRIBUTE_MAP WHERE PROFILEID= :profileID";

	public static final String MDO_FILTER_DELETE_NAME = "MDO_FILTER_DELETE_NAME";
	public static final String MDO_FILTER_DELETE_QUERY = "DELETE FROM MDO_FILTER WHERE USERTENANTID= :userTenantID";

	/*
	 * Other Constant
	 */
	public static final int INSERT_OPTION = 1;
	public static final int DELETE_OPTION = 2;
	public static final int PRINT_OPTION = 3;
	public static final int EXIT_OPTION = 6;
	public static final int DELETE_ALERTS_ONLY = 5;
	public static final int INSERT_ALERTS_ONLY = 4;


	public static final String SELECT_AS_TOKENIZER ="SELECT * FROM MDO_APPLICATION WHERE TENANT_ID=";


	public static final String SCRIPT_DIR="log";
	public static final String GENERATED_SCRIPT_DIR="sqlscript";
	public static final String MASTER_SCRIPT_FILE="masterScript.log";
	public static final String INTERMEDIATE_SCRIPT_FILE="intermediateScript.log";
	public static final String ERROR_MSG_WHILE_INSERTION_FAILS ="\n\n\n\n*******ERROR WHILE INSERTING THE DATA .PLEASE CHECK LOG/MIGRATION.LOG AND FIX THE ISSUE MANUALLY BEFORE RUNNING THE INSERT FUNCTION AGAIN FOR THE SAME TENANT. *****";
	public static final String PRINT_FILE="tenantdata.txt";


	/*
	 * Printing Data
	 */
	public static final String MDO_CRYPTO_KEY_BY_TENANT_ID_NAME = "CryptoKey.byTenantID";
	public static final String MDO_CRYPTO_KEY_BY_TENANT_ID_QUERY = "SELECT * FROM MDO_CRYPTO_KEY WHERE TENANT_ID=:tenantID";

	public static final String APPSYMBOLFILE_TENANTID_NAME = "AppSymbolFile.byAppID";
	public static final String APPSYMBOLFILE_TENANTID_QUERY = "SELECT * FROM MDO_APP_SYMBOL_FILE WHERE TENANT_ID=:tenantID";

	public static final String TENANT_APP_BAEXT_TENANTID_NAME = "TenantAppBAExt.byTenantID";
	public static final String TENANT_APP_BAEXT_TENANTID_QUERY = "SELECT * FROM MDO_TENANT_APP_BAEXT WHERE TENANTID=:tenantID";

	public static final String MDO_TENANT_APP_CONFIG_BY_TENANT_ID_NAME = "TenantAppConfig.byTenantID";
	public static final String MDO_TENANT_APP_CONFIG_BY_TENANT_ID_QUERY = "SELECT * FROM MDO_TENANT_APP_CONFIG WHERE TENANTID=:tenantID";

	public static final String MDO_TENANT_APP_PROFILE_BY_TENANT_ID_NAME = "TenantAppProfile.byTenantID";
	public static final String MDO_TENANT_APP_PROFILE_BY_TENANT_ID_QUERY = "SELECT * FROM MDO_TENANT_APP_PROFILE WHERE TENANTID=:tenantID";

	public static final String MDO_TENANT_APP_POLICY_BY_TENANT_ID_NAME = "TenantAppPolicy.byTeanatID";
	public static final String MDO_TENANT_APP_POLICY_BY_TENANT_ID_QUERY = "SELECT * FROM MDO_TENANT_APP_POLICY WHERE TENANTID=:tenantID";



}
