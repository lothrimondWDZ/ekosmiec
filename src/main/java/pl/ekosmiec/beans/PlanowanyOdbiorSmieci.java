package pl.ekosmiec.beans;

import org.joda.time.LocalDate;

public class PlanowanyOdbiorSmieci implements Comparable<PlanowanyOdbiorSmieci>{
	
	private LocalDate data;
	private Integer idGrupy;
	
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public Integer getIdGrupy() {
		return idGrupy;
	}
	public void setIdGrupy(Integer idGrupy) {
		this.idGrupy = idGrupy;
	}
	
	/*
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * sorotwanie najpierw po dacie, pozniej po numerze grupy
	 */
	@Override
	public int compareTo(PlanowanyOdbiorSmieci o) {
		
		int wynik = this.data.compareTo(o.getData());
		
		if (wynik == 0)
			return this.idGrupy.compareTo(o.getIdGrupy());
		else
			return wynik;
	}
	
	

}
