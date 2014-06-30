package pl.ekosmiec.algorithms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;

import pl.ekosmiec.beans.DostepnyCzas;
import pl.ekosmiec.beans.Grupa;
import pl.ekosmiec.beans.Historia;
import pl.ekosmiec.beans.OdbiorSmieci;
import pl.ekosmiec.beans.PlanowanyOdbiorSmieci;
import pl.ekosmiec.beans.PrzewidywanaProdukcja;
import pl.ekosmiec.beans.RozpatrywanaGrupa;
import pl.ekosmiec.beans.WspolczynnikiAlgorytmu;
import pl.ekosmiec.services.GeneratorService;


public class GeneratorHarmonogramu {

	
	private GeneratorService generatorService;
	
	public class Tryb {
		
		public final static int NOWY = 0; //ekstrapolacja od ostatniej daty w historii
		public final static int DOPISZ = 1; //dopisuje daty do harmonogramu ktorych brakuje
		//public final static int NADPISZ = 2; //harmonogram w wybranym zakresie jest ukladany od nowa
		
	}
	
	private HashMap<Integer, PrzewidywanaProdukcja> przewidywaneProdukcje = new HashMap<Integer, PrzewidywanaProdukcja>();
	
	private Date poczatek;
	private Date koniec;
	private Date koniecHistorii;
	private List<Grupa> grupy;
	
	
	//private DAOGeneratora daoGeneratora;
	private int tryb;
	private List<PlanowanyOdbiorSmieci> staryHarmonogram;
	
	public GeneratorHarmonogramu(GeneratorService generatorService, int tryb){
		this.generatorService = generatorService;
		//daoGeneratora = new DAOGeneratoraImpl();
		this.tryb = tryb;
	}
	
	public List<PlanowanyOdbiorSmieci> nowyHarmonogram(Date poczatek, Date koniec, List<PlanowanyOdbiorSmieci> staryHarmonogram){
		
		this.poczatek = poczatek;
		this.koniec = koniec;
		this.staryHarmonogram = staryHarmonogram;
		
		this.grupy = generatorService.pobierzGrupyZHistoria();
		przewidywanieProdukcji();
		DostepnyCzas dostepnyCzas = generatorService.pobierzDostepnyCzas(new LocalDate(poczatek), new LocalDate(koniec));
		//TODO Pobieranie Harmonogramu akutalnego, wczesniej spr. trybu
		List<PlanowanyOdbiorSmieci> harmonogram = ulozHarmonogram(dostepnyCzas);
		
		Collections.sort(harmonogram);
		
		for (PlanowanyOdbiorSmieci pos : harmonogram){
			System.out.println(pos.getData() + "   " + dostepnyCzas.dostepneMinuty(pos.getData()) + "   " + pos.getIdGrupy() + "   " + pos.getData().dayOfWeek().getAsText());
		}

		return harmonogram;
	}

	private void przewidywanieProdukcji() {
		// okreslenie z jakiego okresu potrzebujemy historii
		Date teraz = new Date();
		Calendar cal = Calendar.getInstance();
		if (teraz.before(poczatek))
			cal.setTime(teraz);
		else
			cal.setTime(poczatek);
		this.koniecHistorii = cal.getTime();
		cal.add(Calendar.YEAR, -1);
		Date poczatekHistorii = cal.getTime();

		for (Grupa grupa : grupy) {
			// przewidywanie konieczne gdy jest autoharmonogram
			if (grupa.isAutoharmonogram()) {
				// przewidywanie konieczne gdy historia jest odpowiednio dluga
				Historia historia = grupa.getHistoria();
				if (historia.getPoczatek().before(poczatekHistorii)
						|| historia.getPoczatek().equals(poczatekHistorii)) {

					List<OdbiorSmieci> odbiory = historia.getOdbiory();
					List<Float> produkcjaHist = new LinkedList<Float>();
					Iterator<OdbiorSmieci> it = odbiory.iterator();
					OdbiorSmieci odbior;
					Date poprzedniaData = historia.getPoczatek();
					Date nastepnaData = null, poczKroku, koniecKroku;
					Float iloscSmieci;
					long iloscDni;
					boolean innaIloscDni = false;
					LocalDate aktualnaData = new LocalDate(poczatekHistorii);

					while (it.hasNext()) {
						
						odbior = it.next();
						nastepnaData = odbior.getData();
							
							// warunek spelniony jesli daty w obszarze
							// rozpatrywanego okresu historii
							if (nastepnaData.after(poczatekHistorii)
									|| poprzedniaData.before(koniecHistorii)) {
								
								
								iloscDni = roznicaDni(poprzedniaData, nastepnaData);
								iloscSmieci = new Float(odbior.getOdebrano()
										/ (float) iloscDni);
	
								// trzeba dopilnowac zeby wynikowy ciag miescil sie
								// w narzuconych granicach
								if (poprzedniaData.before(poczatekHistorii)) {
									innaIloscDni = true;
									poczKroku = poczatekHistorii;
								} else
									poczKroku = poprzedniaData;
	
								if (nastepnaData.after(koniecHistorii)) {
									innaIloscDni = true;
									koniecKroku = koniecHistorii;
								} else
									koniecKroku = nastepnaData;
								
								if (innaIloscDni) {
									iloscDni = roznicaDni(poczKroku, koniecKroku);
									innaIloscDni = false;
								}
										
								// dodawanie tylu elementow ile wynosi roznica dni
								for (int i = 0; i < (int) iloscDni; i++) {
									if (!(aktualnaData.getMonthOfYear() == 2 && aktualnaData.getDayOfMonth() == 29))
										produkcjaHist.add(iloscSmieci);
									aktualnaData = aktualnaData.plusDays(1);
								}
	

							}

						poprzedniaData = nastepnaData;
					}
					
					//dopelnienie ostanich dni pierwszymiy
					for (int i=produkcjaHist.size(); i<365; i++)
						produkcjaHist.add(produkcjaHist.get(0));
					
					//okreslenie od kiedy przewidujemy produkcje smieci - data ostaniego (nawet zaplanowanego) wywozu
					Date ostatniWywoz = dataOstaniegoWywozu(grupa);
					
					// TUTAJ MAMY PRZETWORZONA HISTORIE (w zmiennej
					// produkcjaHist) DLA BIEZACEJ GRUPY
					
					przewidywaneProdukcje.put(grupa.getId(), ekstrapolacja(
								ostatniWywoz,
								(int) roznicaDni(ostatniWywoz, koniec),
								poczatekHistorii,
								produkcjaHist));
					
/*					grupa.setPrzewidywanaProdukcja(
							ekstrapolacja(
								ostatniWywoz,
								(int) roznicaDni(ostatniWywoz, koniec),
								produkcjaHist)
							);*/

				}
			}
		}
	}
	
	private int roznicaDni(Date poprzedzajaca, Date nastepujaca){
		//return (int)(nastepujaca.getTime() - poprzedzajaca.getTime()) / (24 * 60 * 60 * 1000);
		return roznicaDni(new LocalDate(poprzedzajaca), new LocalDate(nastepujaca));
	}
	
	private int roznicaDni(LocalDate poprzedzajaca, LocalDate nastepujaca){
		
		return Days.daysBetween(poprzedzajaca, nastepujaca).getDays();
	}
	
	private PrzewidywanaProdukcja ekstrapolacja(Date poczatek, int iloscDni, Date poczatekHistorii, List<Float> produkcjaHist){
		PrzewidywanaProdukcja przewidywanaProdukcja = new PrzewidywanaProdukcja();
		przewidywanaProdukcja.setPoczatek(poczatek);
		List<Float> dziennaProdukcja = new ArrayList<Float>(iloscDni);
		//Iterator<Float> it = produkcjaHist.iterator();
		LocalDate aktualnaData = new LocalDate(poczatek);
		
		LocalDate tmpHis = new LocalDate(poczatekHistorii);
		LocalDate tmpPocz = new LocalDate(poczatek);
		int roznicaDni = 0;
		
		//roznica dni z pominieciem 29 lutego
		while (tmpHis.isBefore(tmpPocz)){
			if (!(aktualnaData.getMonthOfYear() == 2 && aktualnaData.getDayOfMonth() == 29))
				roznicaDni++;
			
			tmpHis =tmpHis.plusDays(1);
		}
		
		//tmpHis = new LocalDate(poczatekHistorii);
		// TODO Zrobic faktyczne przewidywanie produkcji odpadow
		for (int i=0; i<iloscDni; i++){
			if (aktualnaData.getMonthOfYear() == 2 && aktualnaData.getDayOfMonth() == 29)
				i--;
			
			dziennaProdukcja.add(produkcjaHist.get((i + roznicaDni)%365));
			//System.out.println(tmpPocz.plusDays(i) + "   " + tmpHis.plusDays((i + roznicaDni)%365));
			aktualnaData.plusDays(1);
		}
		
		przewidywanaProdukcja.setDziennaProdukcja(dziennaProdukcja);
		return przewidywanaProdukcja;
	}
	
	private List<PlanowanyOdbiorSmieci> ulozHarmonogram(DostepnyCzas dostepnyCzas){
	

		
		List<PlanowanyOdbiorSmieci> plan = new LinkedList<PlanowanyOdbiorSmieci>();
		PlanowanyOdbiorSmieci pos;
		
		//najpierw harmonogram ukladany jest dla grup bez przewidywania na podstawie historii
		for (Grupa g : grupy){
			
			if (!przewidywaneProdukcje.containsKey(g.getId())){
				
				LocalDate ostatniWywoz = new LocalDate(dataOstaniegoWywozu(g));
						
				int skok = (int) Math.round(7.0 / g.getWstepnaCzestotliwosc());
				int odchylenie = 0;
				
				while (ostatniWywoz.plusDays(skok).isBefore(new LocalDate(poczatek)))
					ostatniWywoz = ostatniWywoz.plusDays(skok);
				
				boolean koniecZakresu = false;
				boolean pozycjaDodana;

				int czasWywozu = g.getCzasWywozuMinuty();
				
				LocalDate rozpatrywanaData;
				
				
				while(!koniecZakresu){

					pozycjaDodana = false;
					
					while (!koniecZakresu && !pozycjaDodana){
						
						rozpatrywanaData = ostatniWywoz.plusDays(skok + odchylenie);
						
							if (dostepnyCzas.odejmijMinuty(rozpatrywanaData, czasWywozu)){

								pos = new PlanowanyOdbiorSmieci();
								pos.setData(rozpatrywanaData);
								pos.setIdGrupy(g.getId());
								plan.add(pos);
								ostatniWywoz = rozpatrywanaData;
								odchylenie = 0;
								pozycjaDodana = true;
								
							}
							else{
								
								if (odchylenie <= 0)
									odchylenie = -odchylenie + 1;
								else{
									if (odchylenie < skok)
										odchylenie = -odchylenie;
									else
										odchylenie++;
									
								}
								
								if (odchylenie > 666)
									koniecZakresu = true;
								
							}
							
							if (rozpatrywanaData.isAfter(new LocalDate(koniec)))
								koniecZakresu = true;
							
						}
				}
				
			}
			
		}
		
		//
		// i tu jedziemy z harmonogramem ukladanym na podstawie przewidywnej produkcji odpadow
		//
		
		List<RozpatrywanaGrupa> rozpatrywaneGrupy = new LinkedList<RozpatrywanaGrupa>();
		
		//wybieranie grup z automatycznym ukladaniem harmonogramu i dodawanie do listy rozpatrywanaGrupa
		for (Grupa g : grupy){
			
			if (przewidywaneProdukcje.containsKey(g.getId())){
				
				
				//ustalanie daty ostatniego odbioru smieci
				LocalDate ostatniWywoz = new LocalDate(dataOstaniegoWywozu(g));
				
				RozpatrywanaGrupa rg = new RozpatrywanaGrupa();
				rg.setGrupa(g);
				rg.setOstatniWywoz(ostatniWywoz);
				rg.setPrzewidywanaProdukcja(przewidywaneProdukcje.get(g.getId()));
				//rg.setRozpatrywanaData(ostatniWywoz);
				//rg.setProcentoweZapelnienie(0);
				
				rozpatrywaneGrupy.add(rg);
				
			}
			
		}
		
		LocalDate rozpatrywanaData = new LocalDate(poczatek);
		LocalDate koncowaData = new LocalDate(koniec);
		WspolczynnikiAlgorytmu wspolczynnikiAlgorytmu = generatorService.pobierzWspolczynniki();
		
		//ukladanie automatycznego harmonogramu
		while (rozpatrywanaData.isBefore(koncowaData)){
			
			for (RozpatrywanaGrupa rg : rozpatrywaneGrupy){
				
				rg.setRozpatrywanaData(rozpatrywanaData);
				
			}
			
			Collections.sort(rozpatrywaneGrupy);
			
			for (RozpatrywanaGrupa rg : rozpatrywaneGrupy){
				
				if (rg.getProcentoweZapelnienie() >=  wspolczynnikiAlgorytmu.getZalecane()){
					
					//tutaj wlasciwe dodawanie do planu (harmonogramu)
					boolean zapisano = false;
					boolean wZakresie = true;
					int licznik = 0;
					
					while (!zapisano && licznik < 666){
						
						zapisano = dostepnyCzas.odejmijMinuty(rg.getRozpatrywanaData(), rg.getGrupa().getCzasWywozuMinuty());
						if (zapisano){
							
							pos = new PlanowanyOdbiorSmieci();
							pos.setIdGrupy(rg.getGrupa().getId());
							pos.setData(rg.getRozpatrywanaData());
							plan.add(pos);
							System.out.println(rg.getProcentoweZapelnienie());
							rg.setOstatniWywoz(rozpatrywanaData);
							}
						else
						{
							if (wZakresie){
								
								rg.setRozpatrywanaData(rg.getRozpatrywanaData().minusDays(1));
								
								if (rg.getProcentoweZapelnienie() < wspolczynnikiAlgorytmu.getMinimum() || rg.getRozpatrywanaData().isBefore(new LocalDate(this.poczatek))){
									wZakresie = false;
									rg.setRozpatrywanaData(rozpatrywanaData.plusDays(1));
								}
							}
							else
							{
								rg.setRozpatrywanaData(rg.getRozpatrywanaData().plusDays(1));
								licznik++;
							}
						}
						
					}
					
					
					
				}
				
			}
			
			rozpatrywanaData = rozpatrywanaData.plusDays(1);
			
		}
		

		return plan;
	}
	
	//okreslenie od kiedy przewidujemy produkcje smieci - data ostaniego (nawet zaplanowanego) wywozu
	private Date dataOstaniegoWywozu(Grupa grupa){
		
		List<OdbiorSmieci> odbiory = grupa.getHistoria().getOdbiory();
		Date ostatniWywoz = grupa.getHistoria().getPoczatek();
		
		if (tryb == Tryb.DOPISZ){
			//TODO ustawic dlate dla dopisywania do harmonogramu
			for (PlanowanyOdbiorSmieci pos : staryHarmonogram){
				if (pos.getIdGrupy() == grupa.getId() && (ostatniWywoz.before(pos.getData().toDate())))
					ostatniWywoz = pos.getData().toDate();
			}
			
			
		}
		//else if (tryb == Tryb.NADPISZ){
			//TODO ustawic data dla nadpisywania harmongramu
		//}
		else //if (tryb == Tryb.NOWY)
		{
			//ostatnia znana data z historii o ile takowa wystapila
			if (odbiory != null && odbiory.size()>0){
				ostatniWywoz = odbiory.get(0).getData();
				for (OdbiorSmieci os : odbiory){
					if (os.getData().before(koniecHistorii) && os.getData().after(ostatniWywoz))
						ostatniWywoz = os.getData();
				}
			}
			else
			{
				ostatniWywoz = grupa.getHistoria().getPoczatek();
			}
			
		}
		System.out.println(ostatniWywoz);
		return ostatniWywoz;
	}
}
