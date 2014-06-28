package pl.ekosmiec.beans;

import java.util.Date;
import java.util.List;

public class PrzewidywanaProdukcja {

	private List<Float> dziennaProdukcja;
	private Date poczatek;
	
	public List<Float> getDziennaProdukcja() {
		return dziennaProdukcja;
	}
	public void setDziennaProdukcja(List<Float> dziennaProdukcja) {
		this.dziennaProdukcja = dziennaProdukcja;
	}
	public Date getPoczatek() {
		return poczatek;
	}
	public void setPoczatek(Date poczatek) {
		this.poczatek = poczatek;
	}
	
	
}
