package pl.ekosmiec.controllers;

import static pl.ekosmiec.navigation.Navigator.SCHEDULE;
import static pl.ekosmiec.navigation.Navigator.WORK_SCHEDULE;

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
	@RequestMapping(value = WORK_SCHEDULE, method = RequestMethod.GET)
	public String workSchedulePage(final ModelMap modelMap) {
		return WORK_SCHEDULE;
	}

}
