package pl.ekosmiec.dao;

import java.util.List;

import org.joda.time.LocalDate;

import pl.ekosmiec.beans.DostepnyCzas;
import pl.ekosmiec.beans.Grupa;
import pl.ekosmiec.beans.WspolczynnikiAlgorytmu;
import pl.ekosmiec.data.TymczasoweDane;

public class DAOGeneratoraImpl implements DAOGeneratora {

	public List<Grupa> pobierzGrupyZHistoria(){
		// TODO Faktyczne pobieranie obiektow z bazy zgodnie z data
		
		return TymczasoweDane.listaGrup();
	}
	
	public DostepnyCzas pobierzDostepnyCzas(LocalDate poczatek, LocalDate koniec){
		// TODO Faktyczne pobieranie grafiku
		return TymczasoweDane.dostepnyCzas(poczatek, koniec);
	}
	
	public WspolczynnikiAlgorytmu pobierzWspolczynniki(){
		// TODO Faktyczne pobieranie wspolczynnikow algorytmu
		
		return TymczasoweDane.Wspolczynniki();
	}
}
