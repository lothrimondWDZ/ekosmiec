package pl.ekosmiec.controllers;

import static pl.ekosmiec.navigation.Navigator.ALL_SECTORS;
import static pl.ekosmiec.navigation.Navigator.EDIT_SECTORS;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SectorsControllers {
	@RequestMapping(value = EDIT_SECTORS, method = RequestMethod.GET)
	public String biowasteSectorsPage(final ModelMap modelMap) {
		return EDIT_SECTORS;
	}
	@RequestMapping(value = ALL_SECTORS, method = RequestMethod.GET)
	public String allSectorsPage(final ModelMap modelMap) {
		return ALL_SECTORS;
	}
}
