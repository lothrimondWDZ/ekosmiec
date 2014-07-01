package pl.ekosmiec.controllers;

import static pl.ekosmiec.navigation.Navigator.SCHEDULE;
import static pl.ekosmiec.navigation.Navigator.WORK_SCHEDULE;
import static pl.ekosmiec.navigation.Navigator.HOME;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.ekosmiec.algorithms.GeneratorHarmonogramu;
import pl.ekosmiec.beans.PlanowanyOdbiorSmieci;
import pl.ekosmiec.data.DatabaseConnection;
import pl.ekosmiec.entities.WorkingDayOfTheWeek;
import pl.ekosmiec.services.GeneratorService;
import pl.ekosmiec.view.ScheduleForm;

@Controller
public class ScheduleController {
	
	@Autowired
	private GeneratorService generatorService;
	@Autowired
	private DatabaseConnection databaseConnection;

	@RequestMapping(value = SCHEDULE, method = RequestMethod.GET)
	public String schedulePage(final ModelMap modelMap) 
	{
		List<PlanowanyOdbiorSmieci> harmonogram = generatorService.zaktualizujIZwrocHarmonogram();
		
		return SCHEDULE;
	}
	@RequestMapping(value = WORK_SCHEDULE, method = RequestMethod.GET)
	public String workSchedulePage(final @ModelAttribute ScheduleForm scheduleForm, final ModelMap modelMap) {
		List<WorkingDayOfTheWeek> weekDays = databaseConnection.getWorkingDaysOfTheWeek();
		for(WorkingDayOfTheWeek day : weekDays){
			if(day.getDzien_tygodnia() == 1) {
				scheduleForm.setMonday(day);
			}else if(day.getDzien_tygodnia() == 2){
				scheduleForm.setTuesday(day);			
			}else if(day.getDzien_tygodnia() == 3){	
				scheduleForm.setWednesday(day);
			}else if(day.getDzien_tygodnia() == 4){	
				scheduleForm.setThursday(day);
			}else if(day.getDzien_tygodnia() == 5){	
				scheduleForm.setFriday(day);
			}else if(day.getDzien_tygodnia() == 6){	
				scheduleForm.setSaturday(day);
			}else if(day.getDzien_tygodnia() == 7){	
				scheduleForm.setSunday(day);
			}
		}
		return WORK_SCHEDULE;
	}
	@RequestMapping(value = WORK_SCHEDULE, method = RequestMethod.POST)
	public String saveWorkSchedule(final @ModelAttribute ScheduleForm scheduleForm, final ModelMap modelMap) {
		System.out.println(scheduleForm.getTuesday());
		databaseConnection.updateWorkingDayOfTheWeek(scheduleForm.getMonday());
		databaseConnection.updateWorkingDayOfTheWeek(scheduleForm.getTuesday());
		databaseConnection.updateWorkingDayOfTheWeek(scheduleForm.getWednesday());
		databaseConnection.updateWorkingDayOfTheWeek(scheduleForm.getThursday());
		databaseConnection.updateWorkingDayOfTheWeek(scheduleForm.getFriday());
		databaseConnection.updateWorkingDayOfTheWeek(scheduleForm.getSaturday());
		databaseConnection.updateWorkingDayOfTheWeek(scheduleForm.getSunday());
		return "redirect:" + HOME;
	}

}
