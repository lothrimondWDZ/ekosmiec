package pl.ekosmiec.controllers;

import static pl.ekosmiec.navigation.Navigator.SCHEDULE;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ScheduleController {

	@RequestMapping(value = SCHEDULE, method = RequestMethod.GET)
	public String schedulePage(final ModelMap modelMap) {
		return SCHEDULE;
	}

}
