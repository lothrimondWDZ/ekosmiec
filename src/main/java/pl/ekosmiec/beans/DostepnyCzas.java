package pl.ekosmiec.beans;

import java.util.HashMap;

import org.joda.time.LocalDate;

public class DostepnyCzas {
	
	private HashMap<LocalDate, Integer> dostepneMinuty = new HashMap<LocalDate, Integer>();
		
	
	public Integer dostepneMinuty(LocalDate data){
		
		Integer wynik = dostepneMinuty.get(data);
		
		if (wynik == null)
			return 0;
		else return wynik;
	}
	
	public void dodajMinuty (LocalDate data, int liczbaMinut){
		
		Integer i = dostepneMinuty.get(data);
		if (i == null)
			i = 0;
		
		dostepneMinuty.put(data, i+= liczbaMinut);
		
	}
	/*
	 * Zwraca true jesli wymagany czas jest dostepny
	 */
	public boolean odejmijMinuty(LocalDate data, int liczbaMinut) {

		Integer i = dostepneMinuty.get(data);
		if (i == null || i < liczbaMinut)
			return false;
		else {
			dostepneMinuty.put(data, i-= liczbaMinut);
			return true;
		}
	}

}
