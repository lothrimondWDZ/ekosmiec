package pl.ekosmiec.beans;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Historia {

	private List<OdbiorSmieci> odbiory;
	private Date poczatek;
	
	
	public List<OdbiorSmieci> getOdbiory() {
		return odbiory;
	}
	public void setOdbiory(List<OdbiorSmieci> odbiory) {
		this.odbiory = odbiory;
	}
	public Date getPoczatek() {
		return poczatek;
	}
	public void setPoczatek(Date poczatek) {
		this.poczatek = poczatek;
	}

	public Date getKoniec(){
		
		if (odbiory.size() == 0)
			return this.poczatek;
		else if	(odbiory instanceof LinkedList)
			return ((LinkedList<OdbiorSmieci>) odbiory).getLast().getData();
		else
			return odbiory.get(odbiory.size() - 1).getData();
		
	}
	
	
}
