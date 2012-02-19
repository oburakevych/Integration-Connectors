package org.integration.payments.server.polling;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.integration.payments.server.Constants;
import org.integration.payments.server.om.UserFeedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(Constants.TEST_URI_SECRET_PREFIX + "/test/polling")
public class PollingTestController {

	private static final String PAGE_VIEW = "test/polling";

	private static final String FEEDBACKS_PAGE_VIEW = "test/feedbacks";

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PollingService pollingService;

	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return PAGE_VIEW;
	}

	@RequestMapping(value = "/user/feedbacks", method = RequestMethod.GET)
	public ModelAndView viewFeedbacks() {
		List<UserFeedback> userFeedbacks = pollingService.getAllUserFeedbacks();

		return new ModelAndView(FEEDBACKS_PAGE_VIEW, "userFeedbacks", userFeedbacks);
	}

	@RequestMapping(value = "/user/feedback", method = RequestMethod.POST)
	public void userFeedback(@RequestParam("companyAccountId") UUID companyAccountId,
		@RequestParam("feedback_pg") String feedbackPg,
		@RequestParam("feedback_pg_int") String feedbackPgInt,
		@RequestParam("feedback_txt") String feedbackTxt,
		HttpServletResponse response) {

		UserFeedback feedback = new UserFeedback();

		feedback.setCompanyAccountId(companyAccountId);
		feedback.setFeedbackPg(feedbackPg);
		feedback.setFeedbackPgInt(feedbackPgInt);
		feedback.setFeedbackTxt(feedbackTxt);

		if (log.isTraceEnabled()) {
			log.trace("Received user feedback :" + feedback);
		}

		pollingService.saveUserFeedback(feedback);

		response.setStatus(HttpStatus.CREATED.value());
	}
}
