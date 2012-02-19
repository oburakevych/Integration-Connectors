package org.integration.payments.server.ui.controller;

import org.integration.payments.server.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(Constants.TEST_URI_SECRET_PREFIX + "/test")
public class MainTestController {
	private static final String PAGE_VIEW = "test/main";

	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return PAGE_VIEW;
	}
}
