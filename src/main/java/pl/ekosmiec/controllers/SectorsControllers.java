package pl.ekosmiec.controllers;

import static pl.ekosmiec.navigation.Navigator.ALL_SECTORS;
import static pl.ekosmiec.navigation.Navigator.BIOWASTE_SECTORS;
import static pl.ekosmiec.navigation.Navigator.OTHER_SECTORS;
import static pl.ekosmiec.navigation.Navigator.RECYCLABLE_SECTORS;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SectorsControllers {
	@RequestMapping(value = BIOWASTE_SECTORS, method = RequestMethod.GET)
	public String biowasteSectorsPage(final ModelMap modelMap) {
		return BIOWASTE_SECTORS;
	}
	@RequestMapping(value = RECYCLABLE_SECTORS, method = RequestMethod.GET)
	public String recyclableSectorsPage(final ModelMap modelMap) {
		return RECYCLABLE_SECTORS;
	}
	@RequestMapping(value = OTHER_SECTORS, method = RequestMethod.GET)
	public String otherSectorsPage(final ModelMap modelMap) {
		return OTHER_SECTORS;
	}
	@RequestMapping(value = ALL_SECTORS, method = RequestMethod.GET)
	public String allSectorsPage(final ModelMap modelMap) {
		return ALL_SECTORS;
	}
}
