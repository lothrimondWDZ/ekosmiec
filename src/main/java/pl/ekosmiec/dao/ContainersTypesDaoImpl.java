package pl.ekosmiec.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.ekosmiec.entities.ContainersTypes;

@Repository
public class ContainersTypesDaoImpl implements ContainersTypesDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<ContainersTypes> getAllContainersTypes() {
		return (List<ContainersTypes>) this.sessionFactory.getCurrentSession().createQuery("from ContainersTypes").list();
	}

}
