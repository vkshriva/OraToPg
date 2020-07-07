package com.ca.migration.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.ca.migration.exception.MigrationPostgresException;

@Service("postgresService")
public class PostgresService {

	@Autowired
	private PostgresDao dao;

	Logger logger = Logger.getLogger(PostgresService.class);

	public boolean insertMdoApplication(List<Application> applicationList) {
		boolean result = false;
		for (Application application : applicationList) {
			// logger.debug("Service: Inserting MDO_APPLICATION for Tenant " +
			// application.getPkey().getTenantID());
			dao.insertMdoApplication(application);
			// logger.debug("Service: Completed MDO_APPLICATION for Tenant " +
			// application.getPkey().getTenantID());

		}

		return result;

	}

	public boolean insertMdoCryptoKey(List<CryptoKey> CryptoKeyList) {
		boolean result = false;
		for (CryptoKey cryptoKeyObj : CryptoKeyList) {
			/*
			 * logger.debug("Service:Inserting Data for tenant " +
			 * cryptoKeyObj.getKey().getTenantID() + ",appId :" +
			 * cryptoKeyObj.getKey().getAppID() + " MDO_CRYPTO_KEY");
			 */
			dao.insertMdoCryptoKey(cryptoKeyObj);
			/*
			 * logger.debug("Service:Inserting Data for tenant " +
			 * cryptoKeyObj.getKey().getTenantID() + ",appId :" +
			 * cryptoKeyObj.getKey().getAppID() + " MDO_CRYPTO_KEY");
			 */

		}

		return result;

	}

	public boolean insertMdoTenantAppProfile(TenantAppProfile obj) {

		if (null == obj) {
			String msg = "No data while insert data MdoTenantAppProfile";
			logger.debug(msg);
			return false;
		}

		boolean result = dao.insertMdoTenantAppProfile(obj);
		if (!result) {
			String msg = "Exception while insert data MdoTenantAppProfile for tenantID =" + obj.getKey().getTenantID();
			throw new MigrationPostgresException(msg);
		}

		return result;

	}

	public Map<Long, Long> insertMdoMonitoringProfile(List<MonitoringProfile> monitoringProfileList) {

		Map<Long, Long> profileIDSequenceMap = new HashMap<>();

		if (null == monitoringProfileList || monitoringProfileList.size() == 0) {
			String msg = "No data while insert data MdoMonitoringProfile";
			logger.debug(msg);
			return profileIDSequenceMap;
		}

		for (MonitoringProfile obj : monitoringProfileList) {
			long oldProfileID = obj.getProfileID();
			boolean result = dao.insertMdoMonitoringProfile(obj);
			if (!result) {
				String msg = "Exception while insert data MdoMonitoringProfile";
				throw new MigrationPostgresException(msg);
			}
			long newProfileID = obj.getProfileID();
			profileIDSequenceMap.put(oldProfileID, newProfileID);

		}

		return profileIDSequenceMap;

	}

	public boolean insertMdoProfileAttributeMap(ProfileAttributeMap obj) {

		if (null == obj) {
			String msg = "No data while insert data MdoProfileAttributeMap";
			logger.debug(msg);
			return false;
		}

		boolean result = dao.insertMdoProfileAttributeMap(obj);
		if (!result) {
			String msg = "Exception while insert data MdoProfileAttributeMap for ProfileID ="
					+ obj.getpKey().getProfileID();
			throw new MigrationPostgresException(msg);
		}

		return true;

	}

	public boolean insertMdoTenantAppPolicy(TenantAppPolicy tenantAppPolicyObj) {
		if (null == tenantAppPolicyObj) {
			String msg = "No data while insert data MdoTenantAppPolicy";
			logger.debug(msg);
			return false;
		}
		boolean result = false;
		result = dao.insertMdoTenantAppPolicy(tenantAppPolicyObj);
		if (!result) {
			String exMsg = "Exception while insert data MdoTenantAppPolicy for tenantID ="
					+ tenantAppPolicyObj.getPkey().getTenantID() + " appID= " + tenantAppPolicyObj.getPkey().getAppID();
			throw new MigrationPostgresException(exMsg);
		}

		return result;

	}

	public boolean insertMdoMonitoringPolicy(MonitoringPolicy obj) {

		if (null == obj) {
			String msg = "No data while insert data MonitoringPolicy";
			logger.debug(msg);
			return false;
		}

		boolean result = false;
		result = dao.insertMdoMonitoringPolicy(obj);
		if (!result) {
			String exMsg = "Exception while insert data MonitoringPolicy for policyID =" + obj.getPolicyID();
			throw new MigrationPostgresException(exMsg);
		}

		return result;
	}

	/*
	 * public boolean updateSequence(String sequenceName, String maxSeqQuery) {
	 *
	 * boolean result = false; result = dao.updateSequence(sequenceName,
	 * maxSeqQuery); if (!result) { String exMsg =
	 * "Exception while insert updateSequence for sequence =" + sequenceName; throw
	 * new MigrationPostgresException(exMsg); }
	 *
	 * return result; }
	 */

	public boolean insertAppSymbolFile(List<AppSymbolFile> list) {
		if (null == list) {
			String msg = "No data while insert data AppSymbolFile";
			logger.debug(msg);
			return false;
		}

		list.stream().forEach(obj -> {
			boolean result = dao.insertAppSymbolFile(obj);
			if (!result) {
				String msg = "Exception while insert data MdoAppSymbolFile for tenantID =" + obj.getpKey().getTenantID()
						+ "appID :" + obj.getpKey().getAppID();
				throw new MigrationPostgresException(msg);
			}

		});

		return true;
	}

	public boolean insertTenantAppBAExt(TenantAppBAExt obj) {
		if (null == obj) {
			String msg = "No data while insert data TenantAppBAExt";
			logger.debug(msg);
			return false;
		}

		boolean result = dao.insertTenantAppBAExt(obj);
		if (!result) {
			String msg = "Exception while insert data MdoTenantAppBAExt for tenantID =" + obj.getpKey().getTenantID()
					+ "appID :" + obj.getpKey().getAppID();
			throw new MigrationPostgresException(msg);
		}

		return true;
	}

	public boolean insertTenantAppConfig(List<TenantAppConfig> list) {
		if (null == list) {
			String msg = "No data while insert data TenantAppConfig";
			logger.debug(msg);
			return false;
		}

		list.stream().forEach(obj -> {
			boolean result = dao.insertTenantAppConfig(obj);
			if (!result) {
				String msg = "Exception while insert data MdoProfileAttributeMap for tenantID ="
						+ obj.getpKey().getTenantID() + "appID :" + obj.getpKey().getAppID();
				throw new MigrationPostgresException(msg);
			}

		});

		return true;
	}

	public boolean insertTenantAppPolicy(TenantAppPolicy obj) {
		if (null == obj) {
			String msg = "No data while insert data TenantAppPolicy";
			logger.debug(msg);
			return false;
		}

		boolean result = dao.insertTenantAppPolicy(obj);
		if (!result) {
			String msg = "Exception while insert data MdoTenantAppPolicy for tenantID =" + obj.getPkey().getTenantID()
					+ "appID :" + obj.getPkey().getAppID();
			throw new MigrationPostgresException(msg);
		}

		return true;
	}

	public boolean insertMonitoringPolicy(MonitoringPolicy obj) {
		if (null == obj) {
			String msg = "No data while insert data MonitoringPolicy";
			logger.debug(msg);
			return false;
		}

		boolean result = dao.getMonitoringPolicy(obj);
		if (!result) {
			String msg = "Exception while insert data MdoMonitoringPolicy for policyID =" + obj.getPolicyID();
			throw new MigrationPostgresException(msg);
		}

		return true;
	}

	public Map<Long, Long> insertFilterUser(List<FilterUser> list) {

		Map<Long, Long> userTenantSequenceMap = new HashMap<>();

		if (null == list) {
			String msg = "No data while insert data FilterUser";
			logger.debug(msg);
			return userTenantSequenceMap;
		}

		for (FilterUser obj : list) {
			long oldUserTenantID = obj.getUserTenantID();
			boolean result = dao.insertFilterUser(obj);
			if (!result) {
				String msg = "Exception while insert data FilterUser =" + obj.getTenant();
				throw new MigrationPostgresException(msg);
			}
			long newUserTenantID = obj.getUserTenantID();
			userTenantSequenceMap.put(oldUserTenantID, newUserTenantID);

		}

		return userTenantSequenceMap;

	}

	public Map<Long, Long> insertFilter(List<Filter> list, Long newUserTenantID) {

		Map<Long, Long> filterSequenceMap = new HashMap<>();
		if (null == list) {
			String msg = "No data while insert data Filter";
			logger.debug(msg);
			return filterSequenceMap;
		}

		for (Filter obj : list) {
			obj.setUserTenantID(newUserTenantID);
			long oldFilterID = obj.getFilterID();
			boolean result = dao.insertFilter(obj);
			if (!result) {
				String msg = "Exception while insert data Filter";
				throw new MigrationPostgresException(msg);
			}
			long newFilterID = obj.getFilterID();
			filterSequenceMap.put(oldFilterID, newFilterID);

		}

		return filterSequenceMap;
	}

	boolean insertPolicy(Policy obj) {
		if (null == obj) {
			String msg = "No data while insert data Policy";
			logger.debug(msg);
			return false;
		}

		boolean result = dao.insertPolicy(obj);
		if (!result) {
			String msg = "Exception while insert data MdoPolicy for policyID =" + obj.getPolicyID();
			throw new MigrationPostgresException(msg);
		}

		return true;
	}

	boolean insertAction(Action obj) {
		if (null == obj) {
			String msg = "No data while insert data Action";
			logger.debug(msg);
			return false;
		}

		boolean result = dao.insertAction(obj);
		if (!result) {
			String msg = "Exception while insert data MdoAction for actionID =" + obj.getActionId();
			throw new MigrationPostgresException(msg);
		}

		return true;
	}

	boolean insertEmailAction(EmailAction obj) {
		if (null == obj) {
			String msg = "No data while insert data MdoEmailAction";
			logger.debug(msg);
			return false;
		}

		boolean result = dao.insertEmailAction(obj);
		if (!result) {
			String msg = "Exception while insert data MdoEmailAction for actionID =" + obj.getActionID();
			throw new MigrationPostgresException(msg);
		}

		return true;
	}

	boolean insertPolicyAction(PolicyAction obj) {
		if (null == obj) {
			String msg = "No data while insert data MdoPolicyAction";
			logger.debug(msg);
			return false;
		}

		boolean result = dao.insertPolicyAction(obj);
		if (!result) {
			String msg = "Exception while insert data MdoPolicyAction for actionID =" + obj.getActionID();
			throw new MigrationPostgresException(msg);
		}

		return true;
	}

	public boolean insertTenantAppPolicy(List<TenantAppPolicy> applicationList) {
		boolean result = false;
		for (TenantAppPolicy application : applicationList) {
			// logger.debug("Service: Inserting MDO_APPLICATION for Tenant " +
			// application.getPkey().getTenantID());
			dao.insertMdoTenantAppPolicy(application);
			// logger.debug("Service: Completed MDO_APPLICATION for Tenant " +
			// application.getPkey().getTenantID());

		}

		return result;

	}

}
