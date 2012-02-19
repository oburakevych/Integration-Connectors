package org.integration.payments.server.polling;

import java.util.List;
import java.util.UUID;

import org.integration.payments.server.dao.PluginUsageInfoDao;
import org.integration.payments.server.dao.UserFeedbackDao;
import org.integration.payments.server.dao.UserProfileDao;
import org.integration.payments.server.om.PluginUsageInfo;
import org.integration.payments.server.om.UserFeedback;

public class PollingService {
	@SuppressWarnings("unused")
	private UserProfileDao userProfileDao;

	private UserFeedbackDao userFeedbackDao;

	private PluginUsageInfoDao pluginUsageInfoDao;

	public void setUserProfileDao(UserProfileDao userProfileDao) {
		this.userProfileDao = userProfileDao;
	}

	public void setUserFeedbackDao(UserFeedbackDao userFeedbackDao) {
		this.userFeedbackDao = userFeedbackDao;
	}

	public void setPluginUsageInfoDao(PluginUsageInfoDao pluginUsageInfoDao) {
		this.pluginUsageInfoDao = pluginUsageInfoDao;
	}

	public void saveUserFeedback(UserFeedback feedback) {
		// TODO: Use more correct method to check entity on existing
		UserFeedback oFeedback = userFeedbackDao.get(feedback.getCompanyAccountId());

		if (oFeedback == null) {
			userFeedbackDao.create(feedback);
		} else {
			userFeedbackDao.update(feedback);
		}
	}

	public void trackPluginUsege(UUID companyAccountId, boolean activated) {
		PluginUsageInfo pluginUsageInfo = pluginUsageInfoDao.get(companyAccountId);

		if (pluginUsageInfo == null) {
			pluginUsageInfoDao.create(companyAccountId);
		}

		if (pluginUsageInfo != null && activated) {
			pluginUsageInfoDao.markActivated(companyAccountId);
		}

		if (!activated) {
			pluginUsageInfoDao.markDisabled(companyAccountId);
		}
	}

	public List<UserFeedback> getAllUserFeedbacks() {
		return userFeedbackDao.getAll();
	}
}
