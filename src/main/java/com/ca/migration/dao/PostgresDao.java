package com.ca.migration.dao;

import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ca.migration.constants.MigrationQueryConstants;
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

@Repository
public class PostgresDao {

	@Autowired
	@Qualifier("sessionFactoryPostgres")
	private SessionFactory sessionFactory;

	@Autowired
	private MigrationDAO migrationDao;

	Logger logger = Logger.getLogger(PostgresDao.class);

	public boolean insertMdoApplication(Application application) {

		// logger.debug("Dao:Inserting Data for tenant " +
		// application.getPkey().getTenantID() + " MDO_APPLICATION");
		Session sessionPostgres = sessionFactory.getCurrentSession();

		sessionPostgres.save(application);

		/*
		 * logger.debug( "Dao:Successfull insert for tenant " +
		 * application.getPkey().getTenantID() + " MDO_APPLICATION");
		 */
		return true;

	}

	public boolean insertMdoCryptoKey(CryptoKey CryptoKeyObj) {
		/*
		 * logger.debug("Dao:Inserting Data for tenant " +
		 * CryptoKeyObj.getKey().getTenantID() + ",appId :" +
		 * CryptoKeyObj.getKey().getAppID() + " MDO_CRYPTO_KEY");
		 */
		Session sessionPostgres = sessionFactory.getCurrentSession();

		sessionPostgres.save(CryptoKeyObj);

		/*
		 * logger.debug("Dao:Inserting Data for tenant " +
		 * CryptoKeyObj.getKey().getTenantID() + ",appId :" +
		 * CryptoKeyObj.getKey().getAppID() + " MDO_CRYPTO_KEY");
		 */
		return true;

	}

	public boolean insertMdoTenantAppProfile(TenantAppProfile tenantAppProfileObj) {

		/*
		 * logger.debug("Dao:Inserting Data for tenant " +
		 * tenantAppProfileObj.getKey().getTenantID() + ",appId :" +
		 * tenantAppProfileObj.getKey().getAppID() + " MDO_TENANT_APP_PROFILE");
		 */
		Session sessionPostgres = sessionFactory.getCurrentSession();

		sessionPostgres.save(tenantAppProfileObj);

		/*
		 * logger.debug("Dao:Inserting Data for tenant " +
		 * tenantAppProfileObj.getKey().getTenantID() + ",appId :" +
		 * tenantAppProfileObj.getKey().getAppID() + " MDO_TENANT_APP_PROFILE");
		 */

		return true;
	}

	public boolean insertMdoMonitoringProfile(MonitoringProfile monitoringProfileObj) {

		/*
		 * logger.debug("Dao:Inserting Data for tenant " +
		 * monitoringProfileObj.getTenantID() + ",profileID :" +
		 * monitoringProfileObj.getProfileID() + " MDO_MONITORING_PROFILE");
		 */
		Session sessionPostgres = sessionFactory.getCurrentSession();

		sessionPostgres.save(monitoringProfileObj);

		/*
		 * logger.debug("Dao:Inserting Data for tenant " +
		 * monitoringProfileObj.getTenantID() + ",profileID :" +
		 * monitoringProfileObj.getProfileID() + " MDO_MONITORING_PROFILE");
		 */

		return true;
	}

	public boolean insertMdoProfileAttributeMap(ProfileAttributeMap profileAttributeMapObj) {

		/*
		 * logger.debug("Dao:Inserting Data for tenant " +
		 * profileAttributeMapObj.getpKey().getProfileID() +
		 * " MDO_PROFILE_ATTRIBUTE_MAP");
		 */
		Session sessionPostgres = sessionFactory.getCurrentSession();

		sessionPostgres.save(profileAttributeMapObj);

		/*
		 * logger.debug("Dao:Inserting Data for tenant " +
		 * profileAttributeMapObj.getpKey().getProfileID() +
		 * " MDO_PROFILE_ATTRIBUTE_MAP");
		 */

		return true;
	}

	public boolean insertMdoTenantAppPolicy(TenantAppPolicy tenantAppPolicyObj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(tenantAppPolicyObj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data MdoTenantAppPolicy for tenantID ="
					+ tenantAppPolicyObj.getPkey().getTenantID() + " appID =" + tenantAppPolicyObj.getPkey().getAppID();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}

	}

	public boolean insertMdoMonitoringPolicy(MonitoringPolicy obj) {
		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data MdoTenantAppPolicy for policyID =" + obj.getPolicyID();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	/*
	 * @SuppressWarnings("finally") public boolean updateSequence(String
	 * sequenceName, String maxSeqQuery) { boolean result = false; Session
	 * sessionPostgres = null; Transaction tx = null; try { sessionPostgres =
	 * sessionFactory.openSession(); tx = sessionPostgres.beginTransaction(); Query
	 * query = sessionPostgres.createSQLQuery(maxSeqQuery); Long maxSeqVal =
	 * ((BigInteger) query.uniqueResult()).longValue(); query =
	 * sessionPostgres.createSQLQuery(MigrationQueryConstants.SET_SEQUENCE_QUERY);
	 * query.setString(0, sequenceName); query.setLong(1, maxSeqVal); Long setSeqVal
	 * = ((BigInteger) query.uniqueResult()).longValue(); logger.debug(maxSeqVal +
	 * "..."+setSeqVal);
	 *
	 * if (maxSeqVal == setSeqVal) { logger.debug(maxSeqVal + "..."+setSeqVal);
	 * tx.commit(); result = true; }
	 *
	 * } catch (Exception ex) { String msg =
	 * "Exception while insert data sequenceName " + sequenceName;
	 * logger.debug(msg); logger.error(ex.getMessage(),ex); } finally { if (tx !=
	 * null && tx.isActive()) { tx.rollback(); } sessionPostgres.close(); return
	 * result; }
	 *
	 * }
	 */

	public boolean insertAppSymbolFile(AppSymbolFile obj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data MdoAppSymbolFile for tenantID =" + obj.getpKey().getTenantID()
					+ "appID :" + obj.getpKey().getAppID();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	public boolean insertTenantAppBAExt(TenantAppBAExt obj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data MdoTenantAppBAExt for tenantID =" + obj.getpKey().getTenantID()
					+ "appID :" + obj.getpKey().getAppID();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	public boolean insertTenantAppConfig(TenantAppConfig obj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data MdoTenantAppConfig for tenantID =" + obj.getpKey().getTenantID()
					+ "appID :" + obj.getpKey().getAppID();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	public boolean insertTenantAppPolicy(TenantAppPolicy obj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data MdoTenantAppPolicy for tenantID =" + obj.getPkey().getTenantID()
					+ "appID :" + obj.getPkey().getAppID()+".Will try using saveOrupdate() . Ignore this exceptions";
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			try {

				Session sessionPostgres = sessionFactory.getCurrentSession();
				sessionPostgres.saveOrUpdate(obj);
				return true;
			}

			catch (Exception ex2) {
				msg = "Exception while insert data MdoTenantAppPolicy for tenantID =" + obj.getPkey().getTenantID()
						+ "appID :" + obj.getPkey().getAppID()+".Even After using saveOrupdate()";
				logger.debug(msg);
				logger.error(ex2.getMessage(), ex2);
				return false;
			}

		}
	}

	public boolean getMonitoringPolicy(MonitoringPolicy obj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data MdoMonitoringPolicy for policyID =" + obj.getPolicyID();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	public boolean insertFilterUser(FilterUser obj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data FilterUser for tenantID =" + obj.getTenant();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	public boolean insertFilter(Filter obj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data Filter for filterID =" + obj.getFilterID();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	public boolean insertPolicy(Policy obj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data Policy for policyID =" + obj.getPolicyID();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	public boolean insertAction(Action obj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data Action for actionID =" + obj.getActionId();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	public boolean insertEmailAction(EmailAction obj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data EmailAction for actionID =" + obj.getActionID();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	public boolean insertPolicyAction(PolicyAction obj) {

		try {
			Session sessionPostgres = sessionFactory.getCurrentSession();
			sessionPostgres.save(obj);
			return true;
		}

		catch (Exception ex) {
			String msg = "Exception while insert data PolicyAction for actionID =" + obj.getActionID();
			logger.debug(msg);
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	public void printTenantData(String tenantID) {
		Session sessionPostgres = null;
		try {
			sessionPostgres = sessionFactory.openSession();
			migrationDao.printTenantData(tenantID, sessionPostgres, "Postgres DataBase");
		} catch (Exception ex) {
			String msg = "Exception while insert data printTenantData for tenantID =" + tenantID;
			logger.debug(msg);

			logger.error(ex.getMessage(), ex);
		} finally {
			sessionPostgres.close();
		}
	}

	/*
	 * Delete Functionality
	 */

	public List<Application> deleteMdoApplication(String tenantID) {

		List<Application> returnList = null;
		try {
			final Session sessionPostgres = sessionFactory.getCurrentSession();
			Query query = sessionPostgres.getNamedQuery(MigrationQueryConstants.MDO_APPLICATION_BY_TENANT_ID_NAME);
			query.setParameter("tenantID", tenantID);
			returnList = query.list();

			returnList.stream().filter(Objects::nonNull).forEach(obj -> {
				sessionPostgres.delete(obj);
				sessionPostgres.flush();
			});

		} catch (Exception ex) {
			String exMsg = "Exception while deleting data MDO_APPLICATION for tenantID =" + tenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(exMsg);
		}

		return returnList;

	}

	public List<CryptoKey> deleteMdoCryptoKey(String tenantID, String appID) {

		List<CryptoKey> returnList = null;
		try {
			final Session sessionPostgres = sessionFactory.getCurrentSession();
			Query query = sessionPostgres
					.getNamedQuery(MigrationQueryConstants.MDO_CRYPTO_KEY_BY_TENANT_ID_APP_ID_NAME);
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);
			returnList = query.list();

			returnList.stream().filter(Objects::nonNull).forEach(obj -> {
				sessionPostgres.delete(obj);
				sessionPostgres.flush();
			});

		} catch (Exception ex) {
			String exMsg = "Exception while deleting data MDO_CRYPTO_KEY for tenantID =" + tenantID + " appID " + appID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(exMsg);
		}

		return returnList;

	}

	public List<AppSymbolFile> deleteMdoAppSymbol(String tenantID, String appID) {

		List<AppSymbolFile> returnList = null;
		try {
			final Session sessionPostgres = sessionFactory.getCurrentSession();
			Query query = sessionPostgres.getNamedQuery(MigrationQueryConstants.APPSYMBOLFILE_APPIDANDTENANTID_NAME);
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);
			returnList = query.list();

			returnList.stream().filter(Objects::nonNull).forEach(obj -> {
				sessionPostgres.delete(obj);
				sessionPostgres.flush();
			});

		} catch (Exception ex) {
			String exMsg = "Exception while deleting data MDO_APP_SYMBOL_FILE for tenantID =" + tenantID + " appID "
					+ appID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(exMsg);
		}

		return returnList;

	}

	public TenantAppBAExt deleteTenantAppBAExt(String tenantID, String appID) {

		TenantAppBAExt returnList = null;
		try {
			final Session sessionPostgres = sessionFactory.getCurrentSession();
			Query query = sessionPostgres.getNamedQuery(MigrationQueryConstants.TENANT_APP_BAEXT_APPIDANDTENANTID_NAME);
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);
			returnList = (TenantAppBAExt) query.uniqueResult();
			if (null != returnList) {
				sessionPostgres.delete(returnList);
				sessionPostgres.flush();
			}

		} catch (Exception ex) {
			String exMsg = "Exception while deleting data MDO_TENANT_APP_BAEXT for tenantID =" + tenantID + " appID "
					+ appID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(exMsg);
		}

		return returnList;
	}

	public List<TenantAppConfig> deleteTenantAppConfig(String tenantID, String appID) {

		List<TenantAppConfig> returnList = null;
		try {
			final Session sessionPostgres = sessionFactory.getCurrentSession();
			Query query = sessionPostgres
					.getNamedQuery(MigrationQueryConstants.MDO_TENANT_APP_CONFIG_BY_TENANT_ID_APP_ID_NAME);
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);
			returnList = query.list();

			returnList.stream().filter(Objects::nonNull).forEach(obj -> {
				sessionPostgres.delete(obj);
				sessionPostgres.flush();
			});

		} catch (Exception ex) {
			String exMsg = "Exception while deleting data MDO_TENANT_APP_CONFIG for tenantID =" + tenantID + " appID "
					+ appID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(exMsg);
		}
		return returnList;
	}

	public void deletePolicyRelatedTblsData(String tenantID, String appID) {

		/*
		 * Deleted Below tables MDO_EMAIL_ACTION MDO_ACTION MDO_POLICY_ACTION MDO_POLICY
		 * MDO_TENANT_APP_POLICY MDO_MONITORING_POLICY
		 */

		List<TenantAppPolicy> returnList = null;
		try {
			final Session sessionPostgres = sessionFactory.getCurrentSession();

			// sessionPostgres.clear();
			Query query = sessionPostgres
					.getNamedQuery(MigrationQueryConstants.MDO_TENANT_APP_POLICY_BY_TENANT_ID_APP_ID_NAME);
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);
			//sessionPostgres.evict(returnList);
			returnList = query.list();

			logger.debug("No of Rows selected for MDO_TENANT_APP_POLICY for tenantID, appID is : "+tenantID+","+appID+","+returnList.size());


			if (null != returnList && returnList.size() > 0) {

				returnList.stream().filter(Objects::nonNull).forEach(tenantAppPolicyObj -> {
					final long policyID = tenantAppPolicyObj.getPolicyID();
					Query queryMonitoringPolicy = sessionPostgres
							.getNamedQuery(MigrationQueryConstants.MDO_MONITORING_POLICY_BY_POLICY_ID_NAME);
					queryMonitoringPolicy.setParameter("policyID", policyID);
					MonitoringPolicy monitoringPolicyObj = (MonitoringPolicy) queryMonitoringPolicy.uniqueResult();
					sessionPostgres.delete(tenantAppPolicyObj);
					sessionPostgres.flush();
					sessionPostgres.delete(monitoringPolicyObj);
					sessionPostgres.flush();
					logger.debug("Deletion Completed:  MDO_TENANT_APP_POLICY, MDO_MONITORING_POLICY  for policyID ="
							+ policyID + " tenantID: " + tenantID + "appID: " + appID);

					Query queryPolicy = sessionPostgres
							.getNamedQuery(MigrationQueryConstants.MDO_POLICY_BY_POLICY_ID_NAME);
					queryPolicy.setParameter("policyID", policyID);
					List<Policy> policyList = queryPolicy.list();

					if (null != policyList && policyList.size() > 0) {

						policyList.stream().filter(Objects::nonNull).forEach(policyObj -> {
							final long ruleID = policyObj.getRuleID();
							Query queryPolicyAction = sessionPostgres
									.getNamedQuery(MigrationQueryConstants.MDO_POLICY_ACTION_BY_RULE_ID_POLICYID_NAME);
							queryPolicyAction.setParameter("policyID", policyID);
							queryPolicyAction.setParameter("ruleID", ruleID);

							List<PolicyAction> policyActionList = queryPolicyAction.list();

							if (null != policyActionList && policyActionList.size() > 0) {

								policyActionList.stream().filter(Objects::nonNull).forEach(policyActionObj -> {
									final long actionID = policyActionObj.getActionID();
									Query queryAction = sessionPostgres
											.getNamedQuery(MigrationQueryConstants.MDO_ACTION_BY_ACTION_ID_NAME);
									queryAction.setParameter("actionID", actionID);
									Action actionObj = (Action) queryAction.uniqueResult();

									Query queryEmailAction = sessionPostgres
											.getNamedQuery(MigrationQueryConstants.MDO_EMAIL_ACTION_BY_ACTION_ID_NAME);
									queryEmailAction.setParameter("actionID", actionID);
									EmailAction emailActionObj = (EmailAction) queryEmailAction.uniqueResult();
									sessionPostgres.delete(policyActionObj);
									sessionPostgres.flush();

									if (null != emailActionObj) {
										sessionPostgres.delete(emailActionObj);
										sessionPostgres.flush();
									}

									if (null != actionObj) {
										sessionPostgres.delete(actionObj);
										sessionPostgres.flush();
									}

								});
							} else {
								logger.debug("NO DATA  MDO_POLICY_ACTION ");
							}
							sessionPostgres.delete(policyObj);
							sessionPostgres.flush();
						});

					}

					else {
						logger.debug("NO DATA  MDO_POLICY for policyID =" + policyID);
					}

				});

			}

			else {
				logger.debug("NO DATA  MDO_TENANT_APP_POLICY for tenantID =" + tenantID + " appID " + appID);
			}

		} catch (Exception ex) {
			String exMsg = "Exception while deleting data MDO_EMAIL_ACTION, MDO_ACTION, MDO_POLICY_ACTION, MDO_POLICY, MDO_TENANT_APP_POLICY, MDO_MONITORING_POLICY for tenantID ="
					+ tenantID + " appID " + appID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(exMsg);
		}

	}

	public void deletePrflTablData(String tenantID) {
		/*
		 * Delete MDO_TENANT_APP_PROFILE MDO_MONITORING_PROFILE
		 * MDO_PROFILE_ATTRIBUTE_MAP
		 *
		 */

		final Session sessionPostgres = sessionFactory.getCurrentSession();
		try {
			Query query = sessionPostgres.getNamedQuery(MigrationQueryConstants.MDO_TENANT_APP_PROFILE_DELETE_NAME);
			query.setParameter("tenantID", tenantID);

			logger.debug("Deletion Started for MDO_TENANT_APP_PROFILE tenantID: " + tenantID);
			int result = query.executeUpdate();
			sessionPostgres.flush();
			logger.debug("No of rows delelted in MDO_TENANT_APP_PROFILE is "+result);
			if (result > 0) {

				logger.debug("Deletion Completed for MDO_TENANT_APP_PROFILE tenantID: " + tenantID+" no of rows affected: "+result);
			} else {
				logger.debug("No Data for MDO_TENANT_APP_PROFILE tenantID: " + tenantID);
			}

		} catch (Exception ex) {
			String exMsg = "Exception while deleting data MDO_TENANT_APP_PROFILE for tenantID =" + tenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(exMsg);
		}

		try {
			Query queryMonitoringProfile = sessionPostgres
					.getNamedQuery(MigrationQueryConstants.MDO_MONITORING_PROFILE_BY_TENANT_ID_NAME);
			queryMonitoringProfile.setParameter("tenantID", tenantID);

			logger.debug("Deletion Started for MDO_MONITORING_PROFILE tenantID: " + tenantID);
			List<MonitoringProfile> monitoringProfileList = queryMonitoringProfile.list();

			if (null != monitoringProfileList) {
				monitoringProfileList.stream().filter(Objects::nonNull).forEach(monitoringProfileObj -> {
					final Long profileID = monitoringProfileObj.getProfileID();
					Query queryProfileAttributeMap = sessionPostgres
							.getNamedQuery(MigrationQueryConstants.MDO_PROFILE_ATTRIBUTE_MAP_DELETE_NAME);
					queryProfileAttributeMap.setParameter("profileID", profileID);
					queryProfileAttributeMap.executeUpdate();
					sessionPostgres.flush();
					sessionPostgres.delete(monitoringProfileObj);
					sessionPostgres.flush();
				});
			}
		} catch (Exception ex) {
			String exMsg = "Exception while deleting data MDO_MONITORING_PROFILE ,MDO_PROFILE_ATTRIBUTE_MAP for tenantID ="
					+ tenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(exMsg);
		}
	}

	public void deleteFilterTablelData(String tenantID) {
		/*
		 * Delete MDO_FILTER_USER MDO_FILTER
		 *
		 *
		 */

		final Session sessionPostgres = sessionFactory.getCurrentSession();
		try {
			Query queryFilterUser = sessionPostgres
					.getNamedQuery(MigrationQueryConstants.MDO_FILTER_USER_BY_TENANT_NAME);
			queryFilterUser.setParameter("tenantID", tenantID);

			logger.debug("Deletion Started for MDO_FILTER_USER and MDO_FILTER tenantID: " + tenantID);
			List<FilterUser> filterUserList = queryFilterUser.list();

			if (null != filterUserList) {
				filterUserList.stream().filter(Objects::nonNull).forEach(filterUserObj -> {
					final Long userTenantID = filterUserObj.getUserTenantID();
					sessionPostgres.flush();
					Query queryFilter = sessionPostgres.getNamedQuery(MigrationQueryConstants.MDO_FILTER_DELETE_NAME);
					queryFilter.setParameter("userTenantID", userTenantID);
					queryFilter.executeUpdate();
					sessionPostgres.flush();
					sessionPostgres.delete(filterUserObj);
					sessionPostgres.flush();
				});
			}

		} catch (Exception ex) {
			String exMsg = "Exception while deleting data MDO_FILTER_USER,MDO_FILTER for tenantID =" + tenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(exMsg);
		}

	}

	public boolean tenantSpeceficValidation(String tenantID) {
		/*
		 * 1.Check If TenantID is already present in Database
		 *
		 *
		 */
		boolean flag = false;
		final Session sessionPostgres = sessionFactory.getCurrentSession();
		try {
			Query queryApplication = sessionPostgres
					.getNamedQuery(MigrationQueryConstants.MDO_APPLICATION_BY_TENANT_ID_NAME);
			queryApplication.setParameter("tenantID", tenantID);
			List<Application> applicationsList = queryApplication.list();
			if (applicationsList.size() > 0) {
				String exMsg = "Data is already present for tenantID:  " + tenantID;
				throw new MigrationPostgresException(exMsg);

			}
			flag = true;

		} catch (Exception ex) {
			String exMsg = "Exception while initialValidation for tenantID =" + tenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			flag = false;
			// throw new MigrationPostgresException(exMsg);
		}

		return flag;
	}

	/*public boolean tenantMAUProfileValidation() {

	 *
	 * 1.Check If "Tenant_MAU_Profile" is present in mdo_monitoring_profile
	 *

		boolean flag = true;
		final Session sessionPostgres = sessionFactory.openSession();
		try {
			Query query = sessionPostgres.createSQLQuery(MigrationQueryConstants.TENENT_MAU_PROFILE_VALIDATION_QUERY);
			BigInteger count = (BigInteger) query.uniqueResult();
			if (count.intValue() <= 0) {
				flag = false;
				String exMsg = "Tenant_MAU_Profile is not present in mdo_monitoring_profile.Pease create one dummy tenant before using oracleToPostgres";
				throw new MigrationPostgresException(exMsg);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);

		}

		return flag;

	}*/


	public boolean isDataDeleted(String tenantID, String namedQuery, String tableName) {
		boolean flag = true;
		final Session sessionPostgres = sessionFactory.openSession();

		try {
			Query query = sessionPostgres.getNamedQuery(namedQuery);
			query.setParameter("tenantID", tenantID);
			List applicationList = query.list();
			logger.info("NO OF ROWS PRESENT IN TABLE " + tableName + " FOR TENANTID: " + tenantID + "  IS/ARE "
					+ applicationList.size());
			if (null != applicationList && applicationList.size() > 0) {
				flag = false;
			}

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data for isDataDeleted  tenantID =" + tenantID;
			logger.error(exMsg);
			logger.error(ex.getMessage());
			throw new MigrationOracleException(exMsg);

		} finally {
			sessionPostgres.close();
		}
		return flag;
	}

	public boolean isFilterDataDeleted(String tenantID) {

		boolean flag = true;
		final Session sessionPostgres = sessionFactory.openSession();
		try {
			Query query = sessionPostgres.getNamedQuery(MigrationQueryConstants.MDO_FILTER_USER_BY_TENANT_NAME);
			query.setParameter("tenantID", tenantID);
			List<FilterUser> filterUserList = query.list();
			logger.info("NUMBER OF ROWS PRESENT IN MDO_FILTER_USER FOR TENANTID: " + tenantID + " IS/ARE "
					+ filterUserList.size());
			if (null != filterUserList && filterUserList.size() > 0) {
				flag = false;

			}
		} catch (Exception ex) {
			String exMsg = "Exception while fetching data isFilterDataDeleted for tenantID =" + tenantID;
			logger.error(exMsg);
			logger.error(ex.getMessage());
			throw new MigrationOracleException(exMsg);

		} finally {
			sessionPostgres.close();
		}
		return flag;
	}



	public  boolean isProfileDataDeleted(String tenantID) {

		boolean flag = true;
		final Session sessionPostgres = sessionFactory.openSession();
		try {
			Query query = sessionPostgres.getNamedQuery(MigrationQueryConstants.MDO_TENANT_APP_PROFILE_BY_TENANT_ID_NAME);
			query.setParameter("tenantID", tenantID);
			List<TenantAppProfile> tenantAppProfileList = query.list();
			logger.info("NUMBER OF ROWS PRESENT IN MDO_TENANT_APP_PROFILE FOR TENANTID: " + tenantID + " IS/ARE "
					+ tenantAppProfileList.size());
			if (null != tenantAppProfileList && tenantAppProfileList.size() > 0) {
				flag = false;

			}
			Query query2 = sessionPostgres.getNamedQuery(MigrationQueryConstants.MDO_MONITORING_PROFILE_BY_TENANT_ID_NAME);
			query2.setParameter("tenantID", tenantID);
			List<MonitoringProfile> monitoringProfileList = query2.list();
			logger.info("NUMBER OF ROWS PRESENT IN MDO_MONITORING_PROFILE FOR TENANTID: " + tenantID + " IS/ARE "
					+ monitoringProfileList.size());
			if (null != monitoringProfileList && monitoringProfileList.size() > 0) {
				flag = false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			String exMsg = "Exception while fetching data isProfileDataDeleted for tenantID =" + tenantID;
			logger.error(exMsg);
			logger.error(ex.getMessage());
			throw new MigrationOracleException(exMsg);

		}
		finally {
			sessionPostgres.close();
		}
		return flag;

	}



	public boolean isPolicyDataDeleted(String tenantID) {
		boolean flag = true;
		final Session sessionPostgres = sessionFactory.openSession();
		try {
			Query query = sessionPostgres.getNamedQuery(MigrationQueryConstants.MDO_TENANT_APP_POLICY_BY_TENANT_ID_NAME);
			query.setParameter("tenantID", tenantID);
			List<TenantAppPolicy> tenantAppPolicyList = query.list();
			logger.info("NUMBER OF ROWS PRESENT IN MDO_TENANT_APP_POLICY FOR TENANTID: " + tenantID + " IS/ARE "
					+ tenantAppPolicyList.size());
			if (null != tenantAppPolicyList && tenantAppPolicyList.size() > 0) {
				flag = false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			String exMsg = "Exception while fetching data printPolicyData for tenantID =" + tenantID;
			logger.error(exMsg);
			logger.error(ex.getMessage());
			throw new MigrationOracleException(exMsg);

		}
		finally {
			sessionPostgres.close();
		}
		return flag;


	}

	public List<TenantAppPolicy> getTenantAppPolicyByTenantID(String tenantID) {

		List<TenantAppPolicy> returnList = null;
		try {
			final Session sessionPostgres = sessionFactory.getCurrentSession();
			Query query = sessionPostgres
					.getNamedQuery(MigrationQueryConstants.MDO_TENANT_APP_POLICY_BY_TENANT_ID_NAME);
			query.setParameter("tenantID", tenantID);
			returnList = query.list();
			sessionPostgres.evict(returnList);

			/*
			 * returnList.stream().filter(Objects::nonNull).forEach(obj -> {
			 * sessionPostgres.delete(obj); sessionPostgres.flush(); });
			 */

		} catch (Exception ex) {
			String exMsg = "Exception while deleting data MDO_APPLICATION for tenantID =" + tenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationPostgresException(exMsg);
		}

		return returnList;

	}




}
