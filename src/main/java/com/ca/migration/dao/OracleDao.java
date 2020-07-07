package com.ca.migration.dao;

import static com.ca.migration.constants.MigrationQueryConstants.MDO_APPLICATION_BY_TENANT_ID_NAME;

import java.util.List;

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

@Repository
public class OracleDao {

	@Autowired
	@Qualifier("sessionFactoryOracle")
	private SessionFactory sessionFactory;

	@Autowired
	private MigrationDAO migrationDao;

	Session sessionOracle = null;

	Logger logger = Logger.getLogger(OracleDao.class);

	public List<Application> getMdoApplication(String tenantID) {
		List<Application> returnList = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery(MDO_APPLICATION_BY_TENANT_ID_NAME);
			query.setParameter("tenantID", tenantID);
			returnList = query.list();
			return returnList;

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoApplication for tenantID =" + tenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);
		}

		finally {
			sessionOracle.close();

		}

	}

	public List<CryptoKey> getMdoCryptoKey(String tenantID, String appID) {
		List<CryptoKey> returnList = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery("CryptoKey.byTenantIDAndappID");
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);
			returnList = query.list();

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoCryptoKey for tenantID =" + tenantID + " appID =" + appID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);
		}

		finally {
			sessionOracle.close();

		}

		return returnList;
	}

	public List<TenantAppProfile> getMdoTenantAppProfile(String tenantID, String appID) {
		List<TenantAppProfile> returnList = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery("TenantAppProfile.byTenantIDAndappID");
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);
			returnList = query.list();

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoTenantAppProfile for tenantID =" + tenantID + " appID ="
					+ appID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);
		}

		finally {
			sessionOracle.close();

		}

		return returnList;

	}

	public List<MonitoringProfile> getMdoMonitoringProfile(String tenantID) {
		List<MonitoringProfile> returnList = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle
					.getNamedQuery(MigrationQueryConstants.MDO_MONITORING_PROFILE_BY_TENANT_ID_NAME);
			query.setParameter("tenantID", tenantID);
			returnList = query.list();

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoMonitoringProfile for tenantID =" + tenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);
		}

		finally {
			sessionOracle.close();

		}

		return returnList;

	}

	// Table MDO_PROFILE_ATTRIBUTE_MAP
	public List<ProfileAttributeMap> getMdoProfileAttributMap(long profileID) {
		List<ProfileAttributeMap> returnList = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery("ProfileAttributeMap.byProfileID");
			query.setParameter("profileID", profileID);
			returnList = query.list();

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoProfileAttributMap for profileID =" + profileID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);
		}

		finally {
			sessionOracle.close();

		}
		return returnList;

	}

	public TenantAppPolicy getMdoTenantAppPolicy(String tenantID, String appID) {

		TenantAppPolicy returnObj = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery("TenantAppPolicy.byTeanatIDandAppID");
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);
			returnObj = (TenantAppPolicy) query.uniqueResult();
			return returnObj;

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoTenantAppPolicy for tenantID =" + tenantID + " appID ="
					+ appID;

			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);
		}

		finally {
			sessionOracle.close();

		}

	}

	@SuppressWarnings("finally")
	public MonitoringPolicy getMdoMonitoringPolicy(long policyID) {

		MonitoringPolicy returnObj = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery("MonitoringPolicy.byPolicyID");
			query.setParameter("policyID", policyID);

			returnObj = (MonitoringPolicy) query.uniqueResult();
			return returnObj;

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MonitoringPolicy for policyID =" + policyID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);
		}

		finally {
			sessionOracle.close();

		}

	}

	public List<AppSymbolFile> getAppSymbolFile(String tenantID, String appID) {

		List<AppSymbolFile> returnList = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery(MigrationQueryConstants.APPSYMBOLFILE_APPIDANDTENANTID_NAME);
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);

			returnList = query.list();
			return returnList;
		} catch (Exception ex) {
			String exMsg = "Exception while fetching data AppSymbolFile for tenantID =" + tenantID + " appID:= "
					+ appID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);
		}

		finally {
			sessionOracle.close();

		}

	}

	public TenantAppBAExt getTenantAppBAExt(String tenantID, String appID) {

		TenantAppBAExt returnObj = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery(MigrationQueryConstants.TENANT_APP_BAEXT_APPIDANDTENANTID_NAME);
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);

			returnObj = (TenantAppBAExt) query.uniqueResult();
			return returnObj;

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoTenantAppBAExt for tenantID =" + tenantID + " appID ="
					+ appID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);

		}

		finally {
			sessionOracle.close();

		}

	}

	public List<TenantAppConfig> getTenantAppConfig(String tenantID, String appID) {
		List<TenantAppConfig> returnObjList = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle
					.getNamedQuery(MigrationQueryConstants.MDO_TENANT_APP_CONFIG_BY_TENANT_ID_APP_ID_NAME);
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);

			returnObjList = query.list();
			return returnObjList;

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoTenantAppConfig for tenantID =" + tenantID + " appID ="
					+ appID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);

		}

		finally {
			sessionOracle.close();

		}

	}

	public TenantAppPolicy getTenantAppPolicy(String tenantID, String appID) {
		TenantAppPolicy returnObj = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle
					.getNamedQuery(MigrationQueryConstants.MDO_TENANT_APP_POLICY_BY_TENANT_ID_APP_ID_NAME);
			query.setParameter("tenantID", tenantID);
			query.setParameter("appID", appID);

			returnObj = (TenantAppPolicy) query.uniqueResult();
			return returnObj;

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoTenantAppPolicy for tenantID =" + tenantID + " appID ="
					+ appID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);

		}

		finally {
			sessionOracle.close();

		}

	}

	public MonitoringPolicy getMonitoringPolicy(long policyID) {
		MonitoringPolicy returnObj = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery(MigrationQueryConstants.MDO_MONITORING_POLICY_BY_POLICY_ID_NAME);
			query.setParameter("policyID", policyID);

			returnObj = (MonitoringPolicy) query.uniqueResult();
			return returnObj;

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoMonitoringPolicy for policyID =" + policyID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);

		}

		finally {
			sessionOracle.close();

		}

	}

	public List<FilterUser> getFilterUser(String tenantID) {
		List<FilterUser> returnObj = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery(MigrationQueryConstants.MDO_FILTER_USER_BY_TENANT_NAME);
			query.setParameter("tenantID", tenantID);

			returnObj = query.list();
			return returnObj;

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data getFilterUser for tenantID =" + tenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);

		}

		finally {
			sessionOracle.close();

		}

	}

	public List<Policy> getPolicy(long policyID) {
		List<Policy> returnObj = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery(MigrationQueryConstants.MDO_POLICY_BY_POLICY_ID_NAME);
			query.setParameter("policyID", policyID);

			returnObj = query.list();
			return returnObj;
		} catch (Exception ex) {
			String exMsg = "Exception while fetching data getPolicy for policyID =" + policyID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);

		}

		finally {
			sessionOracle.close();

		}

	}

	public List<Filter> getFilter(long userTenantID) {
		List<Filter> returnObj = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery(MigrationQueryConstants.MDO_FILTER_BY_FITERID_NAME);
			query.setParameter("userTenanatID", userTenantID);

			returnObj = query.list();
			return returnObj;

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoFilter for userTenantID =" + userTenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);

		}

		finally {
			sessionOracle.close();

		}

	}

	public List<PolicyAction> getPolicyAction(long ruleID, long policyID) {
		List<PolicyAction> returnObj = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle
					.getNamedQuery(MigrationQueryConstants.MDO_POLICY_ACTION_BY_RULE_ID_POLICYID_NAME);
			query.setParameter("ruleID", ruleID);
			query.setParameter("policyID", policyID);

			returnObj = query.list();
			return returnObj;

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data getPolicyAction for policyID =" + policyID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);

		}

		finally {
			sessionOracle.close();

		}
	}

	public Action getAction(long actionID) {
		Action returnObj = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery(MigrationQueryConstants.MDO_ACTION_BY_ACTION_ID_NAME);
			query.setParameter("actionID", actionID);

			returnObj = (Action) query.uniqueResult();
			return returnObj;
		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoAction for actionID =" + actionID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);

		}

		finally {
			sessionOracle.close();

		}

	}

	public EmailAction getEmailAction(long actionID) {
		EmailAction returnObj = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery(MigrationQueryConstants.MDO_EMAIL_ACTION_BY_ACTION_ID_NAME);
			query.setParameter("actionID", actionID);

			returnObj = (EmailAction) query.uniqueResult();
			return returnObj;
		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoEmailAction for actionID =" + actionID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);

		}

		finally {
			sessionOracle.close();

		}

	}

	public void printTenantData(String tenantID) {

		try {
			sessionOracle = sessionFactory.openSession();
			migrationDao.printTenantData(tenantID, sessionOracle, "Oracle DataBase");

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data printTenantData for tenantID =" + tenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);

		}

		finally {
			sessionOracle.close();
		}

	}


	public List<TenantAppPolicy> getTenantAppPolicy(String tenantID) {
		List<TenantAppPolicy> returnList = null;
		try {
			sessionOracle = sessionFactory.openSession();
			Query query = sessionOracle.getNamedQuery(MigrationQueryConstants.MDO_TENANT_APP_POLICY_BY_TENANT_ID_NAME);
			query.setParameter("tenantID", tenantID);
			returnList = query.list();
			return returnList;

		} catch (Exception ex) {
			String exMsg = "Exception while fetching data MdoApplication for tenantID =" + tenantID;
			logger.debug(exMsg);
			logger.error(ex.getMessage(), ex);
			throw new MigrationOracleException(exMsg);
		}

		finally {
			sessionOracle.close();

		}

	}

}
