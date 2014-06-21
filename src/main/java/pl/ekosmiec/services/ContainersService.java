package pl.ekosmiec.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.ekosmiec.dao.ContainersTypesDao;
import pl.ekosmiec.entities.ContainersTypes;

@Service
public class ContainersService {
	
	@Autowired
	private ContainersTypesDao containersTypesDao;
	
	@Transactional
	public List<ContainersTypes> getAllContainersTypes()
	{
		return containersTypesDao.getAllContainersTypes();
	}
}
