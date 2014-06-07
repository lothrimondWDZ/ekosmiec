package pl.ekosmiec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static pl.ekosmiec.navigation.Navigator.HOME;
import static pl.ekosmiec.navigation.Navigator.ROOT;

@Controller
public class HomeController {
	
	@RequestMapping(value = {ROOT, HOME}, method = RequestMethod.GET)
	public String homePage(final ModelMap modelMap) {
		return HOME;
	}
}