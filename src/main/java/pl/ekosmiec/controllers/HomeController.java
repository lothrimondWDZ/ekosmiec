package pl.ekosmiec.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.ekosmiec.algorithms.GeneratorHarmonogramu;
import pl.ekosmiec.data.DatabaseConnection;
import pl.ekosmiec.entities.WasteType;
import pl.ekosmiec.services.GeneratorService;
import static pl.ekosmiec.navigation.Navigator.HOME;
import static pl.ekosmiec.navigation.Navigator.ROOT;
import static pl.ekosmiec.navigation.Navigator.RAPORT;

@Controller
public class HomeController {
	
	@Autowired
	private DatabaseConnection databaseConnection;
	
	@Autowired
	private GeneratorService generatorService;
	
	@RequestMapping(value = HOME, method = RequestMethod.GET)
	public String homePage(final ModelMap modelMap) {
		return HOME;
	}
	@RequestMapping(value = RAPORT, method = RequestMethod.GET)
	public String raportPage(@RequestParam(required = false) Integer rok, @RequestParam(required = false) Integer rodzaj_odpadow,final ModelMap modelMap) {
		
		modelMap.addAttribute("wasteTypes", databaseConnection.getWasteTypes());
		
		if (rok!= null && rodzaj_odpadow ==null)
			modelMap.addAttribute("reports", databaseConnection.getMonthlyReport(rok));
		else if (rok== null && rodzaj_odpadow != null)
			modelMap.addAttribute("reports", databaseConnection.getAnnualReport(rodzaj_odpadow));
		else if (rok!= null && rodzaj_odpadow !=null)
			modelMap.addAttribute("reports", databaseConnection.getMonthlyReport(rodzaj_odpadow, rok));
		else
			modelMap.addAttribute("reports", databaseConnection.getAnnualReport());
		
		modelMap.addAttribute("rok", rok);
		modelMap.addAttribute("rodzaj_odpadow", rodzaj_odpadow);
		
		return RAPORT;
	}
	
	@RequestMapping(value = "/generowanieDanych", method = RequestMethod.GET)
	public String generowanieDanych( final ModelMap modelMap) {
		
		databaseConnection.deleteAllGroups();
		generatorService.generowanieTymczasowychDanych();
		
		return "redirect:" + ROOT;
	}
	
	@RequestMapping(value = ROOT, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println(databaseConnection.getAnnualReport(1));
		System.out.println(databaseConnection.getAnnualReport());
		System.out.println(databaseConnection.getMonthlyReport(1,2014));
		System.out.println(databaseConnection.getMonthlyReport(2014));

		
		return "home";
	}
}