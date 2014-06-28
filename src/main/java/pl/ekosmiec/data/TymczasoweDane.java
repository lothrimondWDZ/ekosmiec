package pl.ekosmiec.data;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import pl.ekosmiec.beans.DostepnyCzas;
import pl.ekosmiec.beans.Grupa;
import pl.ekosmiec.beans.Historia;
import pl.ekosmiec.beans.OdbiorSmieci;
import pl.ekosmiec.beans.WspolczynnikiAlgorytmu;

@Component
public class TymczasoweDane {


	
	public static List<Grupa> listaGrup(){
		/*
		 5 grup z historia od Pn, 1.06.2009 do teraz
		 grupa1 - PN
		 grupa2 - PN + CZW
		 grupa3 - WT
		 grupa4 - CZW
		 grupa5 - PT (na stałe)
		 
		 */
		
		List<Grupa> LISTA_GRUP = new LinkedList<Grupa>();
		
		Grupa grupa1, grupa2, grupa3, grupa4, grupa5;
		Historia his1, his2, his3, his4, his5;
		List<OdbiorSmieci> odb1, odb2, odb3, odb4, odb5;
		OdbiorSmieci odbior;
		
		grupa1 = new Grupa();
		grupa1.setId(1);
		grupa1.setAutoharmonogram(true);
		grupa1.setCzasWywozuMinuty(4*60);
		grupa1.setMinCzestotliwosc(0);
		grupa1.setWstepnaCzestotliwosc(1);
		grupa1.setPojemnoscPrzeliczona(5700);
		
		grupa2 = new Grupa();
		grupa2.setId(2);
		grupa2.setAutoharmonogram(true);
		grupa2.setCzasWywozuMinuty(4*60);
		grupa2.setMinCzestotliwosc(0);
		grupa2.setWstepnaCzestotliwosc(2);
		grupa2.setPojemnoscPrzeliczona(4900);
		
		grupa3 = new Grupa();
		grupa3.setId(3);
		grupa3.setAutoharmonogram(true);
		grupa3.setCzasWywozuMinuty(8*60);
		grupa3.setMinCzestotliwosc(0);
		grupa3.setWstepnaCzestotliwosc(1);
		grupa3.setPojemnoscPrzeliczona(13700);
		
		grupa4 = new Grupa();
		grupa4.setId(4);
		grupa4.setAutoharmonogram(true);
		grupa4.setCzasWywozuMinuty(4*60);
		grupa4.setMinCzestotliwosc(0);
		grupa4.setWstepnaCzestotliwosc(1);
		grupa4.setPojemnoscPrzeliczona(6700);
		
		grupa5 = new Grupa();
		grupa5.setId(5);
		grupa5.setAutoharmonogram(false);
		grupa5.setCzasWywozuMinuty(8*60);
		grupa5.setMinCzestotliwosc(0);
		grupa5.setWstepnaCzestotliwosc(1);
		grupa5.setPojemnoscPrzeliczona(16000);
		
		Calendar cal = Calendar.getInstance();
		cal.set(2009, 6-1, 1); //1 cz 2009 (Pn)
		Date teraz = new Date();
		
		his1 = new Historia();
		his1.setPoczatek(cal.getTime());
		
		his2 = new Historia();
		his2.setPoczatek(cal.getTime());
		
		his3 = new Historia();
		his3.setPoczatek(cal.getTime());
		
		his4 = new Historia();
		his4.setPoczatek(cal.getTime());
		
		his5 = new Historia();
		his5.setPoczatek(cal.getTime());
		
		odb1 = new LinkedList<OdbiorSmieci>();
		odb2 = new LinkedList<OdbiorSmieci>();
		odb3 = new LinkedList<OdbiorSmieci>();
		odb4 = new LinkedList<OdbiorSmieci>();
		odb5 = new LinkedList<OdbiorSmieci>();
		
		while (teraz.after(cal.getTime())){
			//poniedzialek
			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa1.getPojemnoscPrzeliczona() * 0.8f);
			odbior.setPojemnosc(grupa1.getPojemnoscPrzeliczona());
			odb1.add(odbior);
			
			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa2.getPojemnoscPrzeliczona() * 0.7f);
			odbior.setPojemnosc(grupa2.getPojemnoscPrzeliczona());
			odb2.add(odbior);
			
			//wtorek
			cal.add(Calendar.DATE, 1);
			
			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa3.getPojemnoscPrzeliczona() * 0.85f);
			odbior.setPojemnosc(grupa3.getPojemnoscPrzeliczona());
			odb3.add(odbior);
			
			//czwartek
			cal.add(Calendar.DATE, 2);
			
			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa2.getPojemnoscPrzeliczona() * 0.62f);
			odbior.setPojemnosc(grupa2.getPojemnoscPrzeliczona());
			odb2.add(odbior);
			
			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa4.getPojemnoscPrzeliczona() * 0.55f);
			odbior.setPojemnosc(grupa4.getPojemnoscPrzeliczona());
			odb4.add(odbior);
			
			//piatek
			cal.add(Calendar.DATE, 1);
			
			odbior = new OdbiorSmieci();
			odbior.setData(cal.getTime());
			odbior.setOdebrano(grupa5.getPojemnoscPrzeliczona() * 0.8f);
			odbior.setPojemnosc(grupa5.getPojemnoscPrzeliczona());
			odb5.add(odbior);

			cal.add(Calendar.DATE, 3);
		
		}
		
		
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
		
		return LISTA_GRUP;
	}
	public static DostepnyCzas dostepnyCzas(LocalDate poczatek, LocalDate koniec){
		

		LocalDate data = poczatek;
		data = data.minusYears(1);
		
		// zaladowanie 8h dla kolejnych 5*12 tygodni
		DostepnyCzas DOSTEPNY_CZAS = new DostepnyCzas();


		while (data.isBefore(koniec)) {
			for (int i = 0; i < 5 && data.isBefore(koniec); i++) {
				
				if (data.isEqual(poczatek.minusYears(1))){
					
					i = data.getDayOfWeek() - 1;
					if (i>=5)
						continue;
				}
					
				
				DOSTEPNY_CZAS.dodajMinuty(data, 8 * 60);
				data = data.plusDays(1);

			}
			data = data.plusDays(2);
		}
		
		return DOSTEPNY_CZAS;
	}

	public static WspolczynnikiAlgorytmu Wspolczynniki(){
		
		WspolczynnikiAlgorytmu wsp = new WspolczynnikiAlgorytmu();
		wsp.setMinimum(30);
		wsp.setZalecane(80);
		
		return wsp;

	}
}
