package pl.ekosmiec.controllers;

import static pl.ekosmiec.navigation.Navigator.SCHEDULE;
import static pl.ekosmiec.navigation.Navigator.WORK_SCHEDULE;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.ekosmiec.algorithms.GeneratorHarmonogramu;
import pl.ekosmiec.beans.PlanowanyOdbiorSmieci;
import pl.ekosmiec.data.DatabaseConnection;
import pl.ekosmiec.services.GeneratorService;

@Controller
public class ScheduleController {
	
	@Autowired
	private GeneratorService generatorService;
	


	@RequestMapping(value = SCHEDULE, method = RequestMethod.GET)
	public String schedulePage(final ModelMap modelMap) 
	{
		List<PlanowanyOdbiorSmieci> harmonogram = generatorService.zaktualizujIZwrocHarmonogram();
		
		return SCHEDULE;
	}
	@RequestMapping(value = WORK_SCHEDULE, method = RequestMethod.GET)
	public String workSchedulePage(final ModelMap modelMap) {
		return WORK_SCHEDULE;
	}

}
