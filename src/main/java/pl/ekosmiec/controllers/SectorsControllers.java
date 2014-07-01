package pl.ekosmiec.controllers;

import static pl.ekosmiec.navigation.Navigator.ALL_SECTORS;
import static pl.ekosmiec.navigation.Navigator.EDIT_SECTORS;
import static pl.ekosmiec.navigation.Navigator.NEW_SECTOR;
import static pl.ekosmiec.navigation.Navigator.DELETE_SECTOR;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.ekosmiec.data.DatabaseConnection;
import pl.ekosmiec.entities.Group;

@Controller
public class SectorsControllers {
	
	@Autowired
	private DatabaseConnection databaseConnection;
	
	@RequestMapping(value = EDIT_SECTORS + "/{sectorId}", method = RequestMethod.GET)
	public String biowasteSectorsPage(final @PathVariable Integer sectorId, final ModelMap modelMap) {
		modelMap.addAttribute("currentSector", databaseConnection.getGroupById(sectorId));
		modelMap.addAttribute("wasteTypes", databaseConnection.getWasteTypes());
		modelMap.addAttribute("containers", databaseConnection.getContainers(sectorId));
		modelMap.addAttribute("containerTypes", databaseConnection.getContainerTypes());
		return EDIT_SECTORS;
	}
	@RequestMapping(value = ALL_SECTORS, method = RequestMethod.GET)
	public String allSectorsPage(final ModelMap modelMap) {
		modelMap.addAttribute("sectors", databaseConnection.getGroups());
		return ALL_SECTORS;
	}
	
	@RequestMapping(value = NEW_SECTOR, method = RequestMethod.GET)
	public String newSectorsPage(@ModelAttribute Group group, final ModelMap modelMap) {
		modelMap.addAttribute("wasteTypes", databaseConnection.getWasteTypes());
		return NEW_SECTOR;
	}
	
	@RequestMapping(value = NEW_SECTOR, method = RequestMethod.POST)
	public String saveSector(@ModelAttribute Group group, final ModelMap modelMap) {
		//modelMap.addAttribute("sectors", databaseConnection.getGroups());
		
		group.setPoczatek_historii(new Date());
		databaseConnection.addGroup(group);
		
		return "redirect:" + ALL_SECTORS;
	}
	
	@RequestMapping(value = DELETE_SECTOR + "/{id}", method = RequestMethod.GET)
	public String deleteSector(final @PathVariable Integer id, final ModelMap modelMap) {
		//modelMap.addAttribute("sectors", databaseConnection.getGroups());
		
		databaseConnection.deleteGroup(id);
		
		return "redirect:" + ALL_SECTORS;
	}
}
