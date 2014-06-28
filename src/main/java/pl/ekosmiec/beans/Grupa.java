package pl.ekosmiec.beans;

public class Grupa {
	
	Integer id;
	
	private Historia historia;
	//private PrzewidywanaProdukcja przewidywanaProdukcja;
	
	private float pojemnoscPrzeliczona;
	private int czasWywozuMinuty;
	private Float minCzestotliwosc;
	private float wstepnaCzestotliwosc;
	private boolean autoharmonogram;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Historia getHistoria() {
		return historia;
	}

	public void setHistoria(Historia historia) {
		this.historia = historia;
	}

	public float getPojemnoscPrzeliczona() {
		return pojemnoscPrzeliczona;
	}

/*	public PrzewidywanaProdukcja getPrzewidywanaProdukcja() {
		return przewidywanaProdukcja;
	}

	public void setPrzewidywanaProdukcja(
			PrzewidywanaProdukcja przewidywanaProdukcja) {
		this.przewidywanaProdukcja = przewidywanaProdukcja;
	}*/

	public void setPojemnoscPrzeliczona(float pojemnoscPrzeliczona) {
		this.pojemnoscPrzeliczona = pojemnoscPrzeliczona;
	}

	public int getCzasWywozuMinuty() {
		return czasWywozuMinuty;
	}

	public void setCzasWywozuMinuty(int czasWywozuMinuty) {
		this.czasWywozuMinuty = czasWywozuMinuty;
	}

	public float getMinCzestotliwosc() {
		return minCzestotliwosc;
	}

	public void setMinCzestotliwosc(float minCzestotliwosc) {
		this.minCzestotliwosc = minCzestotliwosc;
	}

	public float getWstepnaCzestotliwosc() {
		return wstepnaCzestotliwosc;
	}

	public void setWstepnaCzestotliwosc(float wstepnaCzestotliwosc) {
		this.wstepnaCzestotliwosc = wstepnaCzestotliwosc;
	}

	public boolean isAutoharmonogram() {
		return autoharmonogram;
	}

	public void setAutoharmonogram(boolean autoharmonogram) {
		this.autoharmonogram = autoharmonogram;
	}

}
