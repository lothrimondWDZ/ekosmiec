package pl.ekosmiec.beans;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class RozpatrywanaGrupa implements Comparable<RozpatrywanaGrupa>{
	
	//public static WspolczynnikiAlgorytmu wspolczynnikiAlgorytmu;
		
	private Grupa grupa;
	private LocalDate ostatniWywoz;
	private LocalDate rozpatrywanaData;
	private Float procentoweZapelnienie;
	private PrzewidywanaProdukcja przewidywanaProdukcja;
	
	public Grupa getGrupa() {
		return grupa;
	}
	public void setGrupa(Grupa grupa) {
		this.grupa = grupa;
	}
	public LocalDate getOstatniWywoz() {
		return ostatniWywoz;
	}
	public void setOstatniWywoz(LocalDate ostatniWywoz) {
		this.ostatniWywoz = ostatniWywoz;
	}
	public LocalDate getRozpatrywanaData() {
		return rozpatrywanaData;
	}
	public void setRozpatrywanaData(LocalDate rozpatrywanaData) {
		this.rozpatrywanaData = rozpatrywanaData;
		this.procentoweZapelnienie = obliczProcentoweZapelnienie();
	}
	public float getProcentoweZapelnienie() {
		return procentoweZapelnienie;
	}
	

	public PrzewidywanaProdukcja getPrzewidywanaProdukcja() {
		return przewidywanaProdukcja;
	}
	public void setPrzewidywanaProdukcja(PrzewidywanaProdukcja przewidywanaProdukcja) {
		this.przewidywanaProdukcja = przewidywanaProdukcja;
	}
	@Override
	public int compareTo(RozpatrywanaGrupa o) {
		return -this.procentoweZapelnienie.compareTo(o.getProcentoweZapelnienie());
	}
	
	private float obliczProcentoweZapelnienie(){

		float suma = 0;
		int indeksPoczatkowy = Days.daysBetween(new LocalDate(this.przewidywanaProdukcja.getPoczatek()), this.ostatniWywoz).getDays();
		int indeksKoncowy = Days.daysBetween(new LocalDate(this.przewidywanaProdukcja.getPoczatek()), this.rozpatrywanaData).getDays();
		
		for (int i=indeksPoczatkowy; i<indeksKoncowy && i<this.przewidywanaProdukcja.getDziennaProdukcja().size(); i++){
			suma += this.przewidywanaProdukcja.getDziennaProdukcja().get(i);
		}
		
		return 100* suma / this.grupa.getPojemnoscPrzeliczona();
	}
	

}
