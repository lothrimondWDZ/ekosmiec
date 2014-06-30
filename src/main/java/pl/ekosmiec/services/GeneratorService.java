package pl.ekosmiec.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.ekosmiec.algorithms.GeneratorHarmonogramu;
import pl.ekosmiec.beans.DostepnyCzas;
import pl.ekosmiec.beans.Grupa;
import pl.ekosmiec.beans.Historia;
import pl.ekosmiec.beans.OdbiorSmieci;
import pl.ekosmiec.beans.PlanowanyOdbiorSmieci;
import pl.ekosmiec.beans.WspolczynnikiAlgorytmu;
import pl.ekosmiec.data.DatabaseConnection;
import pl.ekosmiec.data.TymczasoweDane;
import pl.ekosmiec.entities.FreeDay;
import pl.ekosmiec.entities.Group;
import pl.ekosmiec.entities.GroupHistory;
import pl.ekosmiec.entities.WasteDisposal;
import pl.ekosmiec.entities.WasteType;
import pl.ekosmiec.entities.WorkingDayOfTheWeek;

@Service
@Transactional
public class GeneratorService {

	@Autowired
	DatabaseConnection databaseConnection;

	public void test(){
		
		List<WorkingDayOfTheWeek> dniPracujace = databaseConnection.getWorkingDaysOfTheWeek();
	
	}
	
	private List<PlanowanyOdbiorSmieci> nowyHarmonogram(){
		//TODO usuwanie harmonogramu
		Date poczatek = new Date();
		Date koniec = new LocalDate(new Date()).plusYears(1).toDate();
		GeneratorHarmonogramu gh = new GeneratorHarmonogramu(this, GeneratorHarmonogramu.Tryb.NOWY);
		List<PlanowanyOdbiorSmieci> harmonogram = gh.nowyHarmonogram( poczatek, koniec, null);
		//TODO dodawanie harmonogramu
		//TODO akutalizacja zmiennej konca harmonogramu
		return harmonogram;
	}
	
	private void uzupelnianieHarmonogramu(){
		
		Date poczatek = this.pobierzKoniecHarmonogramu().toDate();
		Date koniec = new LocalDate(new Date()).plusYears(1).toDate();
		if (poczatek.before(koniec)){
			GeneratorHarmonogramu gh = new GeneratorHarmonogramu(this, GeneratorHarmonogramu.Tryb.DOPISZ);
			List<PlanowanyOdbiorSmieci> harmonogram = gh.nowyHarmonogram( this.pobierzKoniecHarmonogramu().toDate(), koniec, this.pobierzHarmonogram());
			//TODO dodawanie harmonogramu
			//TODO akutalizacja zmiennej konca harmonogramu
		}
	}
	
	public List<PlanowanyOdbiorSmieci> zaktualizujIZwrocHarmonogram(){
		
		List<PlanowanyOdbiorSmieci> harmonogram;
		
		if (databaseConnection.isScheduleEmpty()){
			harmonogram = nowyHarmonogram();
		}else{
			
			uzupelnianieHarmonogramu();
			harmonogram = this.pobierzHarmonogram();
		}
		
		return harmonogram;
	}
	
	public List<Grupa> pobierzGrupyZHistoria(){
		
		List<Group> grupyZBazy = databaseConnection.getGroups();
		List<Grupa> grupy = new ArrayList<Grupa>(grupyZBazy.size());
		Historia historia;
		List<OdbiorSmieci> odbiory;
		OdbiorSmieci odbior;
		List<GroupHistory> history;
		Grupa grupa;
		
		for (Group g : grupyZBazy){
			
			grupa = new Grupa();
			grupa.setId(g.getId());
			grupa.setAutoharmonogram(g.isAutoharmonogram());
			grupa.setCzasWywozuMinuty(g.getCzas_wywozu());
			grupa.setMinCzestotliwosc(g.getMin_czestotliwosc());
			grupa.setWstepnaCzestotliwosc(g.getWstepna_czestotliwosc());
			grupa.setPojemnoscPrzeliczona((float)databaseConnection.getTotalCapacity(g.getId()) * databaseConnection.getWasteType(g.getId()).getPrzelicznik());
			
			historia = new Historia();
			historia.setPoczatek(g.getPoczatek_historii());
			history = databaseConnection.getGroupHistory(g.getId());
			odbiory = new ArrayList<OdbiorSmieci>(history.size());
			
			for (GroupHistory gs : history){
				
				odbior = new OdbiorSmieci();
				odbior.setData(gs.getData());
				odbior.setOdebrano(gs.getOdebrano());
				odbior.setPojemnosc(gs.getLaczna_pojemnosc());
				odbiory.add(odbior);
				
			}
			
			historia.setOdbiory(odbiory);
			grupa.setHistoria(historia);
			grupy.add(grupa);
			
			
		}
		return grupy;
		//return TymczasoweDane.listaGrup();
	}
	
	public DostepnyCzas pobierzDostepnyCzas(LocalDate poczatek, LocalDate koniec){

		List<WorkingDayOfTheWeek> dniPracujace = databaseConnection.getWorkingDaysOfTheWeek();
		List<FreeDay> dniWolne = databaseConnection.getFreeDays();
		
		HashMap<Integer, Integer> dzienMinuty = new HashMap<Integer, Integer>();
		
		for (int i=1; i<=7; i++){
			dzienMinuty.put(i, 0);
		}
		
		for (WorkingDayOfTheWeek dzienPracujacy : dniPracujace){
			dzienMinuty.put(dzienPracujacy.getDzien_tygodnia(), dzienPracujacy.getIlosc());
		}
		

		
		LocalDate data = poczatek;

		DostepnyCzas DOSTEPNY_CZAS = new DostepnyCzas();
		
		boolean dzienWolny;
		LocalDate tmp;

		while (data.isBefore(koniec)) {
			
			dzienWolny = false;
			
			for (FreeDay freeDay : dniWolne){
				tmp = new LocalDate(freeDay.getData());
				if (tmp.getDayOfMonth() == data.getDayOfMonth() && tmp.getMonthOfYear() == data.getMonthOfYear())
					dzienWolny = true;
			}
			if (dzienWolny == false)
				DOSTEPNY_CZAS.dodajMinuty(data, dzienMinuty.get(data.getDayOfWeek()));
			
			data = data.plusDays(1);

		}
		
		return DOSTEPNY_CZAS;
	}
	
	public WspolczynnikiAlgorytmu pobierzWspolczynniki(){
		
		WspolczynnikiAlgorytmu wsp = new WspolczynnikiAlgorytmu();
		wsp.setMinimum(Integer.parseInt(databaseConnection.getVariable("MINIMUM")));
		wsp.setZalecane(Integer.parseInt(databaseConnection.getVariable("ZALECANE")));
		
		return wsp;
	}
	
	public void ustawKoniecHarmonogramu(LocalDate data){
		databaseConnection.setVariable("KONIEC_HARMONOGRAMU", data.toString());
	}
	
	public LocalDate pobierzKoniecHarmonogramu(){
		LocalDate d = null;
		
		String s = databaseConnection.getVariable("KONIEC_HARMONOGRAMU");
		if (s != null && s.length() > 0)
			d = new LocalDate(s);

		return d;
	}
	
	public List<PlanowanyOdbiorSmieci> pobierzHarmonogram(){
		
		List<WasteDisposal> wdl = databaseConnection.getSchedule();
		List<PlanowanyOdbiorSmieci> posl = new ArrayList<PlanowanyOdbiorSmieci>(wdl.size());
		
		for (WasteDisposal wd : wdl){
			PlanowanyOdbiorSmieci pos = new PlanowanyOdbiorSmieci();
			pos.setData(new LocalDate(wd.getData()));
			pos.setIdGrupy(wd.getRef_grupa());
			posl.add(pos);
		}
		
		return posl;
	}

	
}
