package pl.ekosmiec.beans;

import java.util.Date;

public class OdbiorSmieci implements Comparable<OdbiorSmieci>{
	private Date data;
	private float odebrano;
	private float pojemnosc;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public float getOdebrano() {
		return odebrano;
	}
	public void setOdebrano(float odebrano) {
		this.odebrano = odebrano;
	}
	public float getPojemnosc() {
		return pojemnosc;
	}
	public void setPojemnosc(float pojemnosc) {
		this.pojemnosc = pojemnosc;
	}
	@Override
	public int compareTo(OdbiorSmieci o) {
		return this.data.compareTo(o.getData());
	}
	
	
}
