package pl.ekosmiec.beans;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Harmonogram {
	private List<PlanowanyOdbiorSmieci> plan;
	private Date poczatek;
	private Date koniec;
	
	public List<PlanowanyOdbiorSmieci> getPlan() {
		return plan;
	}
	public void setPlan(List<PlanowanyOdbiorSmieci> plan) {
		this.plan = plan;
	}
	public Date getPoczatek() {
		return poczatek;
	}
	public void setPoczatek(Date poczatek) {
		this.poczatek = poczatek;
	}
	public Date getKoniec() {
		return koniec;
	}
	public void setKoniec(Date koniec) {
		this.koniec = koniec;
	}
	
	public void sortuj(){
		
		Collections.sort(plan);
		
	}
	
}
