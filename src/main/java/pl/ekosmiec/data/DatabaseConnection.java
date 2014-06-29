package pl.ekosmiec.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import pl.ekosmiec.entities.Container;
import pl.ekosmiec.entities.ContainerType;
import pl.ekosmiec.entities.Group;
import pl.ekosmiec.entities.GroupHistory;
import pl.ekosmiec.entities.WasteType;

@Repository
public class DatabaseConnection extends JdbcDaoSupport{
	
	
    @Autowired
	public
    DatabaseConnection(DataSource dataSource){
        setDataSource(dataSource);
    }


	
	public Integer test(){
		
		return getJdbcTemplate().queryForInt("select count(*) from ekosmiec.rodzaje_kontenerow");
	}
	
	public List<GroupHistory> getGroupHistory(int groupId){
		String sql = "select * from ekosmiec.historia where ref_grupa = ?";
		RowMapper<GroupHistory> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(GroupHistory.class);
		
		return getJdbcTemplate().query(sql,new Object[]{groupId},rm);
	}
	
	public List<WasteType> getWasteTypes(){
		
		String sql = "select * from ekosmiec.rodzaje_odpadow";
		RowMapper<WasteType> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(WasteType.class);
		
		return getJdbcTemplate().query(sql,rm);
	}
	
	public WasteType getWasteType(int id){
		
		String sql = "select * from ekosmiec.rodzaje_odpadow where id = ?";
		RowMapper<WasteType> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(WasteType.class);
		return getJdbcTemplate().queryForObject(sql,new Object[]{id}, rm);
	}
	
	public List<Group> getGroups(){
		String sql = "select * from ekosmiec.grupy";
		RowMapper<Group> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(Group.class);
		return getJdbcTemplate().query(sql, rm);
	}

	public List<Group> getGroups(int wasteId){
		String sql = "select * from ekosmiec.grupy where ref_rodzaj_odpadow = ?";
		RowMapper<Group> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(Group.class);
		return getJdbcTemplate().query(sql,new Object[]{wasteId}, rm);
	}
	
	public List<ContainerType> getContnatinerTypes(){
		
		String sql = "select * from ekosmiec.rodzaje_kontenerow";
		RowMapper<ContainerType> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(ContainerType.class);
		return getJdbcTemplate().query(sql, rm);
		
		
	}
	
	
	public List<ContainerType> getContnatinerType(int id){
		
		String sql = "select * from ekosmiec.rodzaje_kontenerow where id = ?";
		RowMapper<ContainerType> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(ContainerType.class);
		return getJdbcTemplate().query(sql, new Object[]{id}, rm);
		
		
	}
	
	public List<Container> getContainers(int groupId){
		
		String sql = "select id, ref_grupa, ref_rodzaj_kontenera, ST_X(lokalizacja) lokalizacja_x, ST_Y(lokalizacja) lokalizacja_y from ekosmiec.kontenery where ref_grupa = ?"; 
		RowMapper<Container> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(Container.class);
		return getJdbcTemplate().query(sql, new Object[]{groupId}, rm);
	}
	
}
