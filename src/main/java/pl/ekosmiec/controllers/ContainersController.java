package pl.ekosmiec.controllers;

import static pl.ekosmiec.navigation.Navigator.BIOWASTE_CONTAINER;
import static pl.ekosmiec.navigation.Navigator.RECYCLABLE_CONTAINER;
import static pl.ekosmiec.navigation.Navigator.OTHER_CONTAINER;
import static pl.ekosmiec.navigation.Navigator.ALL_CONTAINER;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContainersController {
	
	@RequestMapping(value = BIOWASTE_CONTAINER, method = RequestMethod.GET)
	public String biowasteContainersPage(final ModelMap modelMap) {
		return BIOWASTE_CONTAINER;
	}
	@RequestMapping(value = RECYCLABLE_CONTAINER, method = RequestMethod.GET)
	public String recyclableContainersPage(final ModelMap modelMap) {
		return RECYCLABLE_CONTAINER;
	}
	@RequestMapping(value = OTHER_CONTAINER, method = RequestMethod.GET)
	public String otherContainersPage(final ModelMap modelMap) {
		return OTHER_CONTAINER;
	}
	@RequestMapping(value = ALL_CONTAINER, method = RequestMethod.GET)
	public String allContainersPage(final ModelMap modelMap) {
		return ALL_CONTAINER;
	}
}
