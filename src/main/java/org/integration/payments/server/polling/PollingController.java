package org.integration.payments.server.polling;

import javax.servlet.http.HttpServletResponse;

import org.integration.payments.server.om.UserFeedback;
import org.integration.payments.server.ui.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("polling")
public class PollingController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PollingService pollingService;

	public void setPollingService(PollingService pollingService) {
		this.pollingService = pollingService;
	}

	@RequestMapping(value = "/user/feedback", method = RequestMethod.POST, consumes = "application/json")
	public void userFeedback(@RequestBody UserFeedback feedback,
		HttpServletResponse response) {

		if (log.isTraceEnabled()) {
			log.trace("Received user feedback :" + feedback);
		}

		// validation TODO: find more nice solution
		if (feedback.getCompanyAccountId() == null) {
			throw new BadRequestException("companyAccountId is requited");
		}

		pollingService.saveUserFeedback(feedback);

		response.setStatus(HttpStatus.CREATED.value());
	}
}
