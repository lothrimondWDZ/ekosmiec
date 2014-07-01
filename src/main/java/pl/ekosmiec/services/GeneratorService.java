package pl.ekosmiec.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
import pl.ekosmiec.entities.Container;
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
		this.usunHarmonogram();
		Date poczatek = new Date();
		Date koniec = new LocalDate(new Date()).plusYears(1).toDate();
		GeneratorHarmonogramu gh = new GeneratorHarmonogramu(this, GeneratorHarmonogramu.Tryb.NOWY);
		List<PlanowanyOdbiorSmieci> harmonogram = gh.nowyHarmonogram( poczatek, koniec, null);
		this.dodajDoHarmonogramu(harmonogram, new LocalDate(koniec));
		System.out.println("NOWY");
		return harmonogram;

	}
	
	private void uzupelnianieHarmonogramu(){
		
		Date poczatek = this.pobierzKoniecHarmonogramu().toDate();
		Date koniec = new LocalDate(new Date()).plusYears(1).toDate();
		if (poczatek.before(koniec)){
			GeneratorHarmonogramu gh = new GeneratorHarmonogramu(this, GeneratorHarmonogramu.Tryb.DOPISZ);
			List<PlanowanyOdbiorSmieci> harmonogram = gh.nowyHarmonogram( this.pobierzKoniecHarmonogramu().toDate(), koniec, this.pobierzHarmonogram());
			this.dodajDoHarmonogramu(harmonogram, new LocalDate(koniec));
			System.out.println("DODAWANIE");
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
			Float f = databaseConnection.getTotalCapacity(g.getId());
			if (f == null)
				f = new Float(0);
			grupa.setPojemnoscPrzeliczona(f * databaseConnection.getWasteType(g.getRef_rodzaj_odpadow()).getPrzelicznik());
			
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
	
	public void dodajDoHarmonogramu(List<PlanowanyOdbiorSmieci> posl, LocalDate koniecHarmonogramu){
		
		
		for (PlanowanyOdbiorSmieci pos : posl){
			WasteDisposal wd = new WasteDisposal();
			wd.setData(pos.getData().toDate());
			wd.setRef_grupa(pos.getIdGrupy());
			databaseConnection.addToSchedule(wd);
		}
		
		ustawKoniecHarmonogramu(koniecHarmonogramu);

	}
	
	public void usunHarmonogram(){
		
		databaseConnection.deleteSchedule();
		this.ustawKoniecHarmonogramu(new LocalDate(0));
		
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

	
	public void generowanieTymczasowychDanych(){
		
		Group g1, g2, g3, g4, g5, g6;
		
		List<Double> lokalizazacje1 = Arrays.asList(
				51.80458138255066, 19.432754516601562,
				51.799061450056385, 19.439620971679688,
				51.81073743304608, 19.446144104003906,
				51.8024584115718, 19.449920654296875,
				51.79014320832648, 19.4293212890625,
				51.78865666323306, 19.44202423095703,
				51.78228520075525, 19.426918029785156,
				51.779099132045545, 19.439620971679688,
				51.775912838347296, 19.428977966308594,
				51.790567926497296, 19.457130432128906,
				51.790780284082906, 19.472923278808594,
				51.78207280317411, 19.45850372314453,
				51.795027225829145, 19.481163024902344
				);
		
		List<Double> lokalizazacje2 = Arrays.asList(
				51.76550271093894, 19.435157775878906,
				51.76550271093894, 19.449234008789062,
				51.75870296431738, 19.44202423095703,
				51.7731512018134, 19.461936950683594,
				51.76699001900172, 19.476356506347656,
				51.763165413548066, 19.46502685546875,
				51.74807630995209, 19.445457458496094,
				51.73872278568433, 19.447860717773438,
				51.75402754444698, 19.45781707763672,
				51.74488782605038, 19.458160400390625,
				51.73149373601245, 19.465370178222656,
				51.75041438844966, 19.478416442871094,
				51.745100398645434, 19.485626220703125,
				51.737021936852905, 19.47772979736328
				);

		
		/*
		 6 grup z historia od Pn, 1.06.2009 do teraz
		 grupa1 - PN
		 grupa2 - PN + CZW
		 grupa3 - WT
		 grupa4 - CZW
		 grupa5 - PT (na stałe)
		 grupa6 - PT
		 
		 */
		
/*		List<Grupa> LISTA_GRUP = new LinkedList<Grupa>();
		
		Grupa grupa1, grupa2, grupa3, grupa4, grupa5;
		Historia his1, his2, his3, his4, his5;
		List<OdbiorSmieci> odb1, odb2, odb3, odb4, odb5;
		OdbiorSmieci odbior;*/
		
		Calendar cal = Calendar.getInstance();
		cal.set(2009, 6-1, 1); //1 cz 2009 (Pn)
		Date teraz = new Date();
		
		Container c;
		
		g1 = new Group();
		g1.setAutoharmonogram(true);
		g1.setCzas_wywozu(4*60);
		g1.setMin_czestotliwosc(0);
		g1.setWstepna_czestotliwosc(1);
		g1.setNazwa("Sektor 1 - Surowce");
		g1.setOpis("");
		g1.setRef_rodzaj_odpadow(1);
		g1.setPoczatek_historii(cal.getTime());
		g1.setId(databaseConnection.addGroup(g1));
		
		
		
/*		grupa1 = new Grupa();
		grupa1.setId(1);
		grupa1.setAutoharmonogram(true);
		grupa1.setCzasWywozuMinuty(4*60);
		grupa1.setMinCzestotliwosc(0);
		grupa1.setWstepnaCzestotliwosc(1);
		grupa1.setPojemnoscPrzeliczona(5700);*/
		
		g2 = new Group();
		g2.setAutoharmonogram(true);
		g2.setCzas_wywozu(4*60);
		g2.setMin_czestotliwosc(0);
		g2.setWstepna_czestotliwosc(2);
		g2.setNazwa("Sektor 1 - BIO");
		g2.setOpis("");
		g2.setRef_rodzaj_odpadow(2);
		g2.setPoczatek_historii(cal.getTime());
		g2.setId(databaseConnection.addGroup(g2));
		
		

		
/*		grupa2 = new Grupa();
		grupa2.setId(2);
		grupa2.setAutoharmonogram(true);
		grupa2.setCzasWywozuMinuty(4*60);
		grupa2.setMinCzestotliwosc(0);
		grupa2.setWstepnaCzestotliwosc(2);
		grupa2.setPojemnoscPrzeliczona(4900);*/
		
		
		g3 = new Group();
		g3.setAutoharmonogram(true);
		g3.setCzas_wywozu(8*60);
		g3.setMin_czestotliwosc(0);
		g3.setWstepna_czestotliwosc(1);
		g3.setNazwa("Sektor 1 - Pozostałe");
		g3.setOpis("");
		g3.setRef_rodzaj_odpadow(3);
		g3.setPoczatek_historii(cal.getTime());
		g3.setId(databaseConnection.addGroup(g3));	
		
		

		
		
/*		grupa3 = new Grupa();
		grupa3.setId(3);
		grupa3.setAutoharmonogram(true);
		grupa3.setCzasWywozuMinuty(8*60);
		grupa3.setMinCzestotliwosc(0);
		grupa3.setWstepnaCzestotliwosc(1);
		grupa3.setPojemnoscPrzeliczona(13700);*/
		
		
		g4 = new Group();
		g4.setAutoharmonogram(true);
		g4.setCzas_wywozu(4*60);
		g4.setMin_czestotliwosc(0);
		g4.setWstepna_czestotliwosc(1);
		g4.setNazwa("Sektor 2 - Surowce");
		g4.setOpis("");
		g4.setRef_rodzaj_odpadow(1);
		g4.setPoczatek_historii(cal.getTime());
		g4.setId(databaseConnection.addGroup(g4));
		
		
/*		grupa4 = new Grupa();
		grupa4.setId(4);
		grupa4.setAutoharmonogram(true);
		grupa4.setCzasWywozuMinuty(4*60);
		grupa4.setMinCzestotliwosc(0);
		grupa4.setWstepnaCzestotliwosc(1);
		grupa4.setPojemnoscPrzeliczona(6700);*/
		
		g5 = new Group();
		g5.setAutoharmonogram(false);
		g5.setCzas_wywozu(4*60);
		g5.setMin_czestotliwosc(0);
		g5.setWstepna_czestotliwosc(1);
		g5.setNazwa("Sektor 2 - BIO");
		g5.setOpis("");
		g5.setRef_rodzaj_odpadow(2);
		g5.setPoczatek_historii(cal.getTime());
		g5.setId(databaseConnection.addGroup(g5));
		
/*		grupa5 = new Grupa();
		grupa5.setId(5);
		grupa5.setAutoharmonogram(false);
		grupa5.setCzasWywozuMinuty(8*60);
		grupa5.setMinCzestotliwosc(0);
		grupa5.setWstepnaCzestotliwosc(1);
		grupa5.setPojemnoscPrzeliczona(16000);*/
		
		g6 = new Group();
		g6.setAutoharmonogram(true);
		g6.setCzas_wywozu(4*60);
		g6.setMin_czestotliwosc(0);
		g6.setWstepna_czestotliwosc(1);
		g6.setNazwa("Sektor 2 - Pozostałe");
		g6.setOpis("");
		g6.setRef_rodzaj_odpadow(3);
		g6.setPoczatek_historii(cal.getTime());
		g6.setId(databaseConnection.addGroup(g6));
		
		c = new Container();
		c.setRef_rodzaj_kontenera(5);
		
		for (int i=0; i<lokalizazacje1.size()/2; i++){
		
			c.setLokalizacjaX(lokalizazacje1.get(2*i));
			c.setLokalizacjaY(lokalizazacje1.get(2*i + 1));
				
			c.setRef_grupa(g1.getId());
			databaseConnection.addContainer(c);
			c.setRef_grupa(g2.getId());
			databaseConnection.addContainer(c);
			c.setRef_grupa(g3.getId());
			databaseConnection.addContainer(c);

		}
		

		
		for (int i=0; i<lokalizazacje2.size()/2; i++){
		
			c.setLokalizacjaX(lokalizazacje2.get(2*i));
			c.setLokalizacjaY(lokalizazacje2.get(2*i + 1));
				
			c.setRef_grupa(g4.getId());
			databaseConnection.addContainer(c);
			c.setRef_grupa(g5.getId());
			databaseConnection.addContainer(c);
			c.setRef_grupa(g6.getId());
			databaseConnection.addContainer(c);

		}

		Float poj1 = databaseConnection.getTotalCapacity(g1.getId());
		Float poj2 = databaseConnection.getTotalCapacity(g2.getId());
		Float poj3 = databaseConnection.getTotalCapacity(g3.getId());
		Float poj4 = databaseConnection.getTotalCapacity(g4.getId());
		Float poj5 = databaseConnection.getTotalCapacity(g5.getId());
		Float poj6 = databaseConnection.getTotalCapacity(g6.getId());
		
		
		GroupHistory gh = new GroupHistory();
		

		while (teraz.after(cal.getTime())){
			//poniedzialek
			
			gh.setRef_grupa(g1.getId());
			gh.setLaczna_pojemnosc(poj1);
			gh.setOdebrano(poj1 * 0.5f);
			gh.setData(cal.getTime());
			databaseConnection.addGroupHistory(gh);
			
/*			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa1.getPojemnoscPrzeliczona() * 0.8f);
			odbior.setPojemnosc(grupa1.getPojemnoscPrzeliczona());
			odb1.add(odbior);*/
			
			gh.setRef_grupa(g2.getId());
			gh.setLaczna_pojemnosc(poj2);
			gh.setOdebrano(poj2 * 0.45f);
			databaseConnection.addGroupHistory(gh);
			
/*			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa2.getPojemnoscPrzeliczona() * 0.7f);
			odbior.setPojemnosc(grupa2.getPojemnoscPrzeliczona());
			odb2.add(odbior);*/
			
			//wtorek
			cal.add(Calendar.DATE, 1);
			
			gh.setRef_grupa(g3.getId());
			gh.setLaczna_pojemnosc(poj3);
			gh.setOdebrano(poj3 * 0.8f);
			gh.setData(cal.getTime());
			databaseConnection.addGroupHistory(gh);
			
/*			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa3.getPojemnoscPrzeliczona() * 0.85f);
			odbior.setPojemnosc(grupa3.getPojemnoscPrzeliczona());
			odb3.add(odbior);*/
			
			//czwartek
			cal.add(Calendar.DATE, 2);
			
			gh.setRef_grupa(g2.getId());
			gh.setLaczna_pojemnosc(poj2);
			gh.setOdebrano(poj2 * 0.5f);
			gh.setData(cal.getTime());
			databaseConnection.addGroupHistory(gh);
			
/*			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa2.getPojemnoscPrzeliczona() * 0.62f);
			odbior.setPojemnosc(grupa2.getPojemnoscPrzeliczona());
			odb2.add(odbior);
			
*/
			gh.setRef_grupa(g4.getId());
			gh.setLaczna_pojemnosc(poj4);
			gh.setOdebrano(poj4 * 0.4f);
			databaseConnection.addGroupHistory(gh);
			
			/*
			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa4.getPojemnoscPrzeliczona() * 0.55f);
			odbior.setPojemnosc(grupa4.getPojemnoscPrzeliczona());
			odb4.add(odbior);*/
			
			//piatek
			cal.add(Calendar.DATE, 1);
			
			gh.setRef_grupa(g5.getId());
			gh.setLaczna_pojemnosc(poj5);
			gh.setOdebrano(poj5 * 0.5f);
			gh.setData(cal.getTime());
			databaseConnection.addGroupHistory(gh);
			
/*			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa5.getPojemnoscPrzeliczona() * 0.8f);
			odbior.setPojemnosc(grupa5.getPojemnoscPrzeliczona());
			odb5.add(odbior);*/
			
			gh.setRef_grupa(g6.getId());
			gh.setLaczna_pojemnosc(poj6);
			gh.setOdebrano(poj6 * 0.5f);
			databaseConnection.addGroupHistory(gh);

			cal.add(Calendar.DATE, 3);
		
		}
		
/*		
		his1.setOdbiory(odb1);
		his2.setOdbiory(odb2);
		his3.setOdbiory(odb3);
		his4.setOdbiory(odb4);
		his5.setOdbiory(odb5);
		
		grupa1.setHistoria(his1);
		grupa2.setHistoria(his2);
		grupa3.setHistoria(his3);
		grupa4.setHistoria(his4);
		grupa5.setHistoria(his5);
		
		LISTA_GRUP.add(grupa1);
		LISTA_GRUP.add(grupa2);
		LISTA_GRUP.add(grupa3);
		LISTA_GRUP.add(grupa4);
		LISTA_GRUP.add(grupa5);
		
		//return LISTA_GRUP;
*/
	}
}
