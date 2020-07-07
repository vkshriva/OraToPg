package com.ca.migration.dao;

import static com.ca.migration.constants.MigrationQueryConstants.MDO_APPLICATION_BY_TENANT_ID_NAME;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ca.migration.constants.MigrationQueryConstants;
import com.ca.migration.dto.Action;
import com.ca.migration.dto.EmailAction;
import com.ca.migration.dto.Filter;
import com.ca.migration.dto.FilterUser;
import com.ca.migration.dto.MonitoringPolicy;
import com.ca.migration.dto.MonitoringProfile;
import com.ca.migration.dto.Policy;
import com.ca.migration.dto.PolicyAction;
import com.ca.migration.dto.ProfileAttributeMap;
import com.ca.migration.dto.TenantAppPolicy;
import com.ca.migration.dto.TenantAppProfile;
import com.ca.migration.exception.MigrationOracleException;

@Repository
public class MigrationDAO {

	Logger logger = Logger.getLogger(MigrationDAO.class);

	File dir = new File(MigrationQueryConstants.SCRIPT_DIR);
	File writeFile = new File(dir, MigrationQueryConstants.PRINT_FILE);

	public void printTenantData(String tenantID, Session session, String dataName) {

		logger.info("************DATABASE SELECTED WHILE PRINTING DATA IS *************"+dataName.toUpperCase());
		printTableData(tenantID, session, dataName, MDO_APPLICATION_BY_TENANT_ID_NAME, "MDO_APPLICATION");
		printTableData(tenantID, session, dataName, MigrationQueryConstants.MDO_CRYPTO_KEY_BY_TENANT_ID_NAME,
				"MDO_CRYPTO_KEY");
		printTableData(tenantID, session, dataName, MigrationQueryConstants.APPSYMBOLFILE_TENANTID_NAME,
				"MDO_APP_SYMBOL_FILE");
		printTableData(tenantID, session, dataName, MigrationQueryConstants.TENANT_APP_BAEXT_TENANTID_NAME,
				"MDO_TENANT_APP_BAEXT");
		printTableData(tenantID, session, dataName, MigrationQueryConstants.MDO_TENANT_APP_CONFIG_BY_TENANT_ID_NAME,
				"MDO_TENANT_APP_CONFIG");

		printFilterData(tenantID, session, dataName);
		printProfileData(tenantID, session, dataName);
		printPolicyData(tenantID, session, dataName);

	}

	private void printTableData(String tenantID, Session session, String dataName, String namedQuery,
			String tableName) {

		try (FileWriter wr = new FileWriter(writeFile,true); PrintWriter pw = new PrintWriter(wr);) {
			Query query = session.getNamedQuery(namedQuery);
			query.setParameter("tenantID", tenantID);
			List applicationList = query.list();
			pw.println(
					"-------------------------------------------------------------------------------------------------------------------");
			pw.println("NUMBER OF ROWS PRESENT IN " + tableName + " FOR TENANTID: " + tenantID + " IS/ARE "
					+ applicationList.size());
			pw.println(
					"--------------------------------------------------------------------------------------------------------------------\n");
			pw.flush();
			applicationList.stream().forEachOrdered((actionObj) -> {
				Class objClass = actionObj.getClass();
				printAllGetters(actionObj, objClass);
				pw.println(
						"*****************************************************************************************************************");
				pw.flush();
			});

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data printTenantData for tenantID =" + tenantID;
			logger.error(exMsg);
			logger.error(ex.getMessage());
			throw new MigrationOracleException(exMsg);

		}

	}

	private void printFilterData(String tenantID, Session session, String dataName) {

		try (FileWriter wr = new FileWriter(writeFile, true); PrintWriter pw = new PrintWriter(wr);) {
			Query query = session.getNamedQuery(MigrationQueryConstants.MDO_FILTER_USER_BY_TENANT_NAME);
			query.setParameter("tenantID", tenantID);
			List<FilterUser> filterUserList = query.list();
			pw.println(
					"************************PRINTING FILTER RELATED DATA*****************************************************************");
			pw.println(
					"-------------------------------------------------------------------------------------------------------------------");
			pw.println("NUMBER OF ROWS PRESENT IN MDO_FILTER_USER FOR TENANTID: " + tenantID + " IS/ARE "
					+ filterUserList.size());
			pw.println(
					"--------------------------------------------------------------------------------------------------------------------\n");
			pw.flush();
			filterUserList.stream().forEachOrdered((actionObj) -> {

				Class objClass = actionObj.getClass();
				printAllGetters(actionObj, objClass);
				pw.println(
						"*****************************************************************************************************************");
				pw.flush();

				Long userTenantID = actionObj.getUserTenantID();
				Query query2 = session.getNamedQuery(MigrationQueryConstants.MDO_FILTER_BY_FITERID_NAME);
				query2.setParameter("userTenanatID", userTenantID);
				List<Filter> filterList = query2.list();
				pw.println(
						"-------------------------------------------------------------------------------------------------------------------");
				pw.println("NUMBER OF ROWS PRESENT IN MDO_FILTER FOR USERTENANTID: " + userTenantID + " IS/ARE "
						+ filterList.size());
				pw.println(
						"--------------------------------------------------------------------------------------------------------------------\n");
				pw.flush();

				filterList.stream().forEachOrdered((obj) -> {
					Class obClass = obj.getClass();
					printAllGetters(obj, obClass);
					pw.println(
							"*****************************************************************************************************************");
					pw.flush();
				});

			});

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data printFilterData for tenantID =" + tenantID;
			logger.error(exMsg);
			logger.error(ex.getMessage());
			throw new MigrationOracleException(exMsg);

		}

	}

	private void printProfileData(String tenantID, Session session, String dataName) {

		try (FileWriter wr = new FileWriter(writeFile, true); PrintWriter pw = new PrintWriter(wr);) {
			Query query = session.getNamedQuery(MigrationQueryConstants.MDO_TENANT_APP_PROFILE_BY_TENANT_ID_NAME);
			query.setParameter("tenantID", tenantID);
			List<TenantAppProfile> tenantAppProfileList = query.list();
			pw.println(
					"************************PRINTING PROFILE RELATED DATA*****************************************************************");
			pw.println(
					"-------------------------------------------------------------------------------------------------------------------");
			pw.println("NUMBER OF ROWS PRESENT IN MDO_TENANT_APP_PROFILE FOR TENANTID: " + tenantID + " IS/ARE "
					+ tenantAppProfileList.size());
			pw.println(
					"--------------------------------------------------------------------------------------------------------------------\n");
			pw.flush();
			tenantAppProfileList.stream().forEachOrdered((actionObj) -> {

				Class objClass = actionObj.getClass();
				printAllGetters(actionObj, objClass);
				pw.println(
						"*****************************************************************************************************************");
				pw.flush();

			});

			Query query2 = session.getNamedQuery(MigrationQueryConstants.MDO_MONITORING_PROFILE_BY_TENANT_ID_NAME);
			query2.setParameter("tenantID", tenantID);
			List<MonitoringProfile> monitoringProfileList = query2.list();
			pw.println(
					"-------------------------------------------------------------------------------------------------------------------");
			pw.println("NUMBER OF ROWS PRESENT IN MDO_MONITORING_PROFILE FOR TENANTID: " + tenantID + " IS/ARE "
					+ monitoringProfileList.size());
			pw.println(
					"--------------------------------------------------------------------------------------------------------------------\n");
			pw.flush();
			monitoringProfileList.stream().forEachOrdered((actionObj2) -> {

				Class objClass2 = actionObj2.getClass();
				printAllGetters(actionObj2, objClass2);
				pw.println(
						"*****************************************************************************************************************");
				pw.flush();

				Long profileID = actionObj2.getProfileID();
				Query query3 = session
						.getNamedQuery(MigrationQueryConstants.MDO_PROFILE_ATTRIBUTE_MAP_BY_PROFILE_ID_NAME);
				query3.setParameter("profileID", profileID);
				List<ProfileAttributeMap> profileAttributeMapList = query3.list();
				pw.println(
						"-------------------------------------------------------------------------------------------------------------------");
				pw.println("NUMBER OF ROWS PRESENT IN MDO_PROFILE_ATTRIBUTE_MAP FOR PROFILEID: " + profileID + " IS/ARE "
						+ profileAttributeMapList.size());
				pw.println(
						"--------------------------------------------------------------------------------------------------------------------\n");
				pw.flush();

				profileAttributeMapList.stream().forEachOrdered((obj) -> {
					Class obClass = obj.getClass();
					printAllGetters(obj, obClass);
					pw.println(
							"*****************************************************************************************************************");
					pw.flush();
				});

			});

		} catch (Exception ex) {
			ex.printStackTrace();
			String exMsg = "Exception while fetching data printProfileData for tenantID =" + tenantID;
			logger.error(exMsg);
			logger.error(ex.getMessage());
			throw new MigrationOracleException(exMsg);

		}

	}

	private void printPolicyData(String tenantID, Session session, String dataName) {

		try (FileWriter wr = new FileWriter(writeFile, true); PrintWriter pw = new PrintWriter(wr);) {
			Query query = session.getNamedQuery(MigrationQueryConstants.MDO_TENANT_APP_POLICY_BY_TENANT_ID_NAME);
			query.setParameter("tenantID", tenantID);
			List<TenantAppPolicy> tenantAppPolicyList = query.list();
			pw.println(
					"************************PRINTING PPOLICY RELATED DATA*****************************************************************");
			pw.println(
					"-------------------------------------------------------------------------------------------------------------------");
			pw.println("NUMBER OF ROWS PRESENT IN MDO_TENANT_APP_POLICY FOR TENANTID: " + tenantID + " IS/ARE "
					+ tenantAppPolicyList.size());
			pw.println(
					"--------------------------------------------------------------------------------------------------------------------\n");
			pw.flush();
			tenantAppPolicyList.stream().forEachOrdered((actionObj) -> {
				Class objClass = actionObj.getClass();
				printAllGetters(actionObj, objClass);
				pw.println(
						"*****************************************************************************************************************");
				pw.flush();

				final Long policyID = actionObj.getPolicyID();

				Query query2 = session.getNamedQuery(MigrationQueryConstants.MDO_MONITORING_POLICY_BY_POLICY_ID_NAME);
				query2.setParameter("policyID", policyID);
				List<MonitoringPolicy> monitoringPolicyList = query2.list();
				pw.println(
						"-------------------------------------------------------------------------------------------------------------------");
				pw.println("NUMBER OF ROWS PRESENT IN MDO_MONITORING_POLICY FOR TENANTID: " + policyID + " IS/ARE "
						+ monitoringPolicyList.size());
				pw.println(
						"--------------------------------------------------------------------------------------------------------------------\n");
				pw.flush();
				monitoringPolicyList.stream().forEachOrdered((actionObj2) -> {
					Class objClass2 = actionObj2.getClass();
					printAllGetters(actionObj2, objClass2);
					pw.println(
							"*****************************************************************************************************************");
					pw.flush();

				});

				Query query3 = session.getNamedQuery(MigrationQueryConstants.MDO_POLICY_BY_POLICY_ID_NAME);
				query3.setParameter("policyID", policyID);
				List<Policy> policyList = query3.list();
				pw.println(
						"-------------------------------------------------------------------------------------------------------------------");
				pw.println(
						"NUMBER OF ROWS PRESENT IN MDO_POLICY FOR TENANTID: " + policyID + " IS " + policyList.size());
				pw.println(
						"--------------------------------------------------------------------------------------------------------------------\n");
				pw.flush();
				policyList.stream().forEachOrdered((actionObj3) -> {
					Class objClass3 = actionObj3.getClass();
					printAllGetters(actionObj3, objClass3);
					pw.println(
							"*****************************************************************************************************************");
					pw.flush();

					Long ruleID = actionObj3.getRuleID();
					Query query4 = session
							.getNamedQuery(MigrationQueryConstants.MDO_POLICY_ACTION_BY_RULE_ID_POLICYID_NAME);
					query4.setParameter("policyID", policyID);
					query4.setParameter("ruleID", ruleID);
					List<PolicyAction> policyActionList = query4.list();
					pw.println(
							"-------------------------------------------------------------------------------------------------------------------");
					pw.println("NUMBER OF ROWS PRESENT IN MDO_POLICY_ACTION FOR TENANTID: " + policyID + " IS/ARE "
							+ policyActionList.size());
					pw.println(
							"--------------------------------------------------------------------------------------------------------------------\n");
					pw.flush();
					policyActionList.stream().forEachOrdered((actionObj4) -> {
						Class objClass4 = actionObj4.getClass();
						printAllGetters(actionObj4, objClass4);
						pw.println(
								"*****************************************************************************************************************");
						pw.flush();

						Long actionID = actionObj4.getActionID();
						Query query5 = session.getNamedQuery(MigrationQueryConstants.MDO_ACTION_BY_ACTION_ID_NAME);
						query5.setParameter("actionID", actionID);
						List<Action> actionList = query5.list();
						pw.println(
								"-------------------------------------------------------------------------------------------------------------------");
						pw.println("NUMBER OF ROWS PRESENT IN MDO_ACTION FOR TENANTID: " + actionID + " IS/ARE "
								+ actionList.size());
						pw.println(
								"--------------------------------------------------------------------------------------------------------------------\n");
						pw.flush();
						actionList.stream().forEachOrdered((actionObj5) -> {
							Class objClass5 = actionObj5.getClass();
							printAllGetters(actionObj5, objClass5);
							pw.println(
									"*****************************************************************************************************************");
							pw.flush();
						});

						Query query6 = session
								.getNamedQuery(MigrationQueryConstants.MDO_EMAIL_ACTION_BY_ACTION_ID_NAME);
						query6.setParameter("actionID", actionID);
						List<EmailAction> emailActionList = query6.list();
						pw.println(
								"-------------------------------------------------------------------------------------------------------------------");
						pw.println("NUMBER OF ROWS PRESENT IN MDO_EMAIL_ACTION FOR TENANTID: " + actionID + " IS/ARE "
								+ emailActionList.size());
						pw.println(
								"--------------------------------------------------------------------------------------------------------------------\n");
						pw.flush();
						emailActionList.stream().forEachOrdered((actionObj6) -> {
							Class objClass6 = actionObj6.getClass();
							printAllGetters(actionObj6, objClass6);
							pw.println(
									"*****************************************************************************************************************");
							pw.flush();
						});

					});

				});

			});

		} catch (Exception ex) {
			ex.printStackTrace();
			String exMsg = "Exception while fetching data printPolicyData for tenantID =" + tenantID;
			logger.error(exMsg);
			logger.error(ex.getMessage());
			throw new MigrationOracleException(exMsg);

		}

	}

	private void printAllGetters(Object obj, Class objClass) {

		Method methods[] = objClass.getDeclaredMethods();
		for (Method method : methods) {
			if (isGetter(method)) {
				try (FileWriter wr = new FileWriter(writeFile, true); PrintWriter pw = new PrintWriter(wr);) {
					// String format = "|%1$-60s|%2$-100s|\n";
					Object returnValue = method.invoke(obj);
					String fieldName = method.getName();
					fieldName = fieldName.replaceAll("get", "");
					fieldName = fieldName.trim().toUpperCase();
					pw.println(fieldName + ":\t\t\t" + returnValue);
					pw.flush();

				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
		}

	}

	private static boolean isGetter(Method method) {
		// identify get methods
		if ((method.getName().startsWith("get") || method.getName().startsWith("is")) && method.getParameterCount() == 0
				&& !method.getReturnType().equals(void.class)) {
			return true;
		}
		return false;
	}

}
