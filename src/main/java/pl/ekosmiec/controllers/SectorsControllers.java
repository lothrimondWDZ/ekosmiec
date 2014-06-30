package pl.ekosmiec.controllers;

import static pl.ekosmiec.navigation.Navigator.ALL_SECTORS;
import static pl.ekosmiec.navigation.Navigator.EDIT_SECTORS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.ekosmiec.data.DatabaseConnection;

@Controller
public class SectorsControllers {
	
	@Autowired
	private DatabaseConnection databaseConnection;
	
	@RequestMapping(value = EDIT_SECTORS, method = RequestMethod.GET)
	public String biowasteSectorsPage(final ModelMap modelMap) {
		modelMap.addAttribute("wasteTypes", databaseConnection.getWasteTypes());
		modelMap.addAttribute("containerTypes", databaseConnection.getContainerTypes());
		return EDIT_SECTORS;
	}
	@RequestMapping(value = ALL_SECTORS, method = RequestMethod.GET)
	public String allSectorsPage(final ModelMap modelMap) {
		modelMap.addAttribute("sectors", databaseConnection.getGroups());
		return ALL_SECTORS;
	}
}
