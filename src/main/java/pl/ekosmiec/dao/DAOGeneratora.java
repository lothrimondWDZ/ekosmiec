package pl.ekosmiec.dao;

import java.util.List;

import org.joda.time.LocalDate;

import pl.ekosmiec.beans.DostepnyCzas;
import pl.ekosmiec.beans.Grupa;
import pl.ekosmiec.beans.WspolczynnikiAlgorytmu;

public interface DAOGeneratora {
	
	public List<Grupa> pobierzGrupyZHistoria();
	public DostepnyCzas pobierzDostepnyCzas(LocalDate poczatek, LocalDate koniec);
	public WspolczynnikiAlgorytmu pobierzWspolczynniki();

}
