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
import pl.ekosmiec.entities.FreeDay;
import pl.ekosmiec.entities.Group;
import pl.ekosmiec.entities.GroupHistory;
import pl.ekosmiec.entities.Statistics;
import pl.ekosmiec.entities.WasteDisposal;
import pl.ekosmiec.entities.WasteType;
import pl.ekosmiec.entities.WorkingDayOfTheWeek;

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
	
	public Integer addGroupHistory(GroupHistory gh){
		String sql = "insert into ekosmiec.historia (ref_grupa, data, laczna_pojemnosc, odebrano, opis) values (?,?,?,?,?) returning id";

		return getJdbcTemplate().queryForInt(sql,new Object[]{gh.getRef_grupa(), gh.getData(), gh.getLaczna_pojemnosc(), gh.getOdebrano(), gh.getOpis()});
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
	
	public Integer addWasteType(WasteType wt){
		
		String sql = "insert into ekosmiec.rodzaje_odpadow(nazwa, przelicznik, opis) values (?,?,?) returning id";
		
		return getJdbcTemplate().queryForInt(sql, new Object[]{wt.getNazwa(), wt.getPrzelicznik(), wt.getOpis()});
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
	
	public Integer addGroup(Group g){
		
		String sql = "insert into ekosmiec.grupy (nazwa, ref_rodzaj_odpadow, czas_wywozu, wstepna_czestotliwosc, min_czestotliwosc, autoharmonogram, poczatek_historii, opis) values (?,?,?,?,?,?,?,?) returning id";
		
		return getJdbcTemplate().queryForInt(sql, new Object[]{g.getNazwa(), g.getRef_rodzaj_odpadow(), g.getCzas_wywozu(), g.getWstepna_czestotliwosc(), g.getMin_czestotliwosc(), g.isAutoharmonogram(), g.getPoczatek_historii(), g.getOpis()});
		
	}
	
	public void deleteGroup(int id){
		
		Object[] args = new Object[]{id};
		
		getJdbcTemplate().update("delete from ekosmiec.kontenery where ref_grupa = ?", args);
		getJdbcTemplate().update("delete from ekosmiec.harmonogram where ref_grupa = ?", args);
		getJdbcTemplate().update("delete from ekosmiec.grupy where id = ?", args);
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
	
	public Integer addContainerType(ContainerType ct){
		
		String sql = "insert into ekosmiec.rodzaje_kontenerow (nazwa, pojemnosc, opis) values (?,?,?) returning id";
		return getJdbcTemplate().queryForInt(sql, new Object[]{ct.getNazwa(), ct.getPojemnosc(), ct.getOpis()});
		
	}
	
	public List<Container> getContainers(int groupId){
		
		String sql = "select id, ref_grupa, ref_rodzaj_kontenera, ST_X(lokalizacja) lokalizacja_x, ST_Y(lokalizacja) lokalizacja_y from ekosmiec.kontenery where ref_grupa = ?"; 
		RowMapper<Container> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(Container.class);
		return getJdbcTemplate().query(sql, new Object[]{groupId}, rm);
	}
	
	public Integer addContainer(Container c){
		
		String sql = "insert into ekosmiec.kontenery(ref_grupa, ref_rodzaj_kontenera, lokalizacja, opis) values (?,?,?,ST_GeomFromText('Point(? ?)'),?) returning id";
		
		return getJdbcTemplate().queryForInt(sql, new Object[]{c.getRef_grupa(), c.getRef_rodzaj_kontenera(), c.getLokalizacjaX(), c.getLokalizacjaY(), c.getOpis()});
	}
	
	public Integer getTotalCapacity(int groupId){
		
		String sql = "select count(r.pojemnosc) from ekosmiec.kontenery k, ekosmiec.rodzaje_kontenerow r where r.id = k.ref_rodzaj_kontenera and k.ref_grupa = ?";
		
		return getJdbcTemplate().queryForInt(sql, new Object[]{groupId});
		
	}
	
	public List<WasteDisposal> getSchedule(){
		String sql = "select * from ekosmiec.harmonogram";
		RowMapper<WasteDisposal> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(WasteDisposal.class);
		return getJdbcTemplate().query(sql, rm);
	}
	
	public void deleteSchedule(int groupId){
		
		getJdbcTemplate().update("delete from ekosmiec.harmonogram");
		
	}
	
	public boolean isScheduleEmpty(){
		
		int i = getJdbcTemplate().queryForInt("select count(*) from ekosmiec.harmonogram where data > now()");
		
		if (i==0)
			return true;
		else
			return false;
		
	}
	
	public Integer addToSchedule(WasteDisposal wd){
		
		String sql = "insert into ekosmiec.harmonogram (ref_grupa, data) values (?,?) returning id";
		return getJdbcTemplate().queryForInt(sql, new Object[]{wd.getRef_grupa(), wd.getData()});
		
	}
	
	public List<WorkingDayOfTheWeek> getWorkingDaysOfTheWeek(){
		
		String sql = "select * from ekosmiec.dni_robocze";
		RowMapper<WorkingDayOfTheWeek> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(WorkingDayOfTheWeek.class);
		return getJdbcTemplate().query(sql, rm);
	}
	
	public Integer updateWorkingDayOfTheWeek(WorkingDayOfTheWeek wdotf){
		
		getJdbcTemplate().update("delete from ekosmiec.dni_robocze where dzien_tygodnia =?",new Object[]{wdotf.getDzien_tygodnia()});
		
		String sql = "insert into ekosmiec.dni_robocze (dzien_tygodnia, ilosc) values (?,?) returning id";
		return getJdbcTemplate().queryForInt(sql, new Object[]{wdotf.getDzien_tygodnia(), wdotf.getIlosc()});
		
	}
	
	public List<FreeDay> getFreeDays(){
		
		String sql = "select * from ekosmiec.dni_wolne";
		RowMapper<FreeDay> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(FreeDay.class);
		return getJdbcTemplate().query(sql, rm);
		
	}
	
	public Integer addFreeDay(FreeDay fd){
		
		String sql = "insert into ekosmiec.dni_wolne (data) values (?) returning id";
		
		return getJdbcTemplate().queryForInt(sql, new Object[]{fd.getData()});
	
	}
	
	public List<Statistics> getAnnualReport(int wasteTypeId){
		
		String sql = "select date_part('year', h.data) okres, sum(g.czas_wywozu) czas_pracy, sum(h.odebrano) ilosc_odpadow from ekosmiec.historia h, ekosmiec.grupy g where g.id = h.ref_grupa and g.ref_rodzaj_odpadow = ? group by okres";
		RowMapper<Statistics> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(Statistics.class);
		return getJdbcTemplate().query(sql, new Object[]{wasteTypeId}, rm);
		
	}
	
	public List<Statistics> getAnnualReport(){
		
		String sql = "select date_part('year', h.data) okres, sum(g.czas_wywozu) czas_pracy, sum(h.odebrano) ilosc_odpadow from ekosmiec.historia h, ekosmiec.grupy g where g.id = h.ref_grupa group by okres";
		RowMapper<Statistics> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(Statistics.class);
		return getJdbcTemplate().query(sql, rm);
		
	}
	
	public List<Statistics> getMonthlyReport(int wasteTypeId, int year){
		
		String sql = "select date_part('month', h.data) okres, sum(g.czas_wywozu) czas_pracy, sum(h.odebrano) ilosc_odpadow from ekosmiec.historia h, ekosmiec.grupy g where g.id = h.ref_grupa and g.ref_rodzaj_odpadow = ? and date_part('year', h.data) = ? group by okres";
		RowMapper<Statistics> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(Statistics.class);
		return getJdbcTemplate().query(sql, new Object[]{wasteTypeId, year}, rm);
		
	}
	
	public List<Statistics> getMonthlyReport(int year){
		
		String sql = "select date_part('month', h.data) okres, sum(g.czas_wywozu) czas_pracy, sum(h.odebrano) ilosc_odpadow from ekosmiec.historia h, ekosmiec.grupy g where g.id = h.ref_grupa and date_part('year', h.data) = ? group by okres";
		RowMapper<Statistics> rm = ParameterizedBeanPropertyRowMapper
				.newInstance(Statistics.class);
		return getJdbcTemplate().query(sql, new Object[]{year}, rm);
		
	}
	
	public String getVariable(String name){
		
		return getJdbcTemplate().queryForObject("select wartosc from ekosmiec.zmienne where nazwa = ?", new Object[]{name}, String.class);
	}
	
	public void setVariable(String name, String value){
		
		getJdbcTemplate().update("update ekosmiec.zmienne set wartosc = ? where nazwa = ?", new Object[]{value, name});
	}

	
}
