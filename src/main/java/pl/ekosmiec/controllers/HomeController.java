package pl.ekosmiec.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.ekosmiec.algorithms.GeneratorHarmonogramu;
import pl.ekosmiec.beans.Harmonogram;
import pl.ekosmiec.data.DatabaseConnection;
import pl.ekosmiec.entities.WasteType;
import static pl.ekosmiec.navigation.Navigator.HOME;
import static pl.ekosmiec.navigation.Navigator.ROOT;
import static pl.ekosmiec.navigation.Navigator.RAPORT;

@Controller
public class HomeController {
	
	@Autowired
	private DatabaseConnection databaseConnection;
	
	@RequestMapping(value = HOME, method = RequestMethod.GET)
	public String homePage(final ModelMap modelMap) {
		return HOME;
	}
	@RequestMapping(value = RAPORT, method = RequestMethod.GET)
	public String raportPage(final ModelMap modelMap) {
		return RAPORT;
	}
	
	@RequestMapping(value = ROOT, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {


		
/*		Date poczatek = new Date(2014 - 1900, 1-1, 1);
		Date koniec = new Date(2015 - 1900, 12-1, 30);
		GeneratorHarmonogramu gh = new GeneratorHarmonogramu(GeneratorHarmonogramu.Tryb.NOWY);
		Harmonogram harmonogram = gh.nowyHarmonogram(poczatek, koniec, null);
		*/
		
/*		
		System.out.println(databaseConnection.test());
		System.out.println(databaseConnection.getGroupHistory(1));
		System.out.println(databaseConnection.getGroups());
		System.out.println(databaseConnection.getGroups(1));
		System.out.println(databaseConnection.getWasteType(1));
		System.out.println(databaseConnection.getWasteTypes());
		System.out.println(databaseConnection.getContnatinerTypes());
		System.out.println(databaseConnection.getContnatinerType(1));
		System.out.println(databaseConnection.getContainers(1));
		System.out.println(databaseConnection.getWorkingDaysOfTheWeek());
		System.out.println(databaseConnection.getFreeDays());
		System.out.println(databaseConnection.getSchedule());
		*/
		
/*		WasteType wt = new WasteType();
		wt.setNazwa("Harnaś");
		wt.setPrzelicznik(2);
		wt.setOpis("");
		System.out.println(databaseConnection.addWasteType(wt));*/
		
		return "home";
	}
}