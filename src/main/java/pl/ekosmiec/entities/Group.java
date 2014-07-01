package pl.ekosmiec.entities;

import java.util.Date;



public class Group {
    private int id;
    private String nazwa;
    private int ref_rodzaj_odpadow;
    private int czas_wywozu = 480;
    private float wstepna_czestotliwosc = 1;
    private float min_czestotliwosc = 0;
    private boolean autoharmonogram = true;
    private Date poczatek_historii;
    private String opis;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public int getRef_rodzaj_odpadow() {
		return ref_rodzaj_odpadow;
	}
	public void setRef_rodzaj_odpadow(int ref_rodzaj_odpadow) {
		this.ref_rodzaj_odpadow = ref_rodzaj_odpadow;
	}
	public int getCzas_wywozu() {
		return czas_wywozu;
	}
	public void setCzas_wywozu(int czas_wywozu) {
		this.czas_wywozu = czas_wywozu;
	}
	public float getWstepna_czestotliwosc() {
		return wstepna_czestotliwosc;
	}
	public void setWstepna_czestotliwosc(float wstepna_czestotliwosc) {
		this.wstepna_czestotliwosc = wstepna_czestotliwosc;
	}
	public float getMin_czestotliwosc() {
		return min_czestotliwosc;
	}
	public void setMin_czestotliwosc(float min_czestotliwosc) {
		this.min_czestotliwosc = min_czestotliwosc;
	}
	public boolean isAutoharmonogram() {
		return autoharmonogram;
	}
	public void setAutoharmonogram(boolean autoharmonogram) {
		this.autoharmonogram = autoharmonogram;
	}
	public Date getPoczatek_historii() {
		return poczatek_historii;
	}
	public void setPoczatek_historii(Date date) {
		this.poczatek_historii = date;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", nazwa=" + nazwa + ", ref_rodzaj_odpadow="
				+ ref_rodzaj_odpadow + ", czas_wywozu=" + czas_wywozu
				+ ", wstepna_czestotliwosc=" + wstepna_czestotliwosc
				+ ", min_czestotliwosc=" + min_czestotliwosc
				+ ", autoharmonogram=" + autoharmonogram
				+ ", poczatek_historii=" + poczatek_historii + ", opis=" + opis
				+ "]";
	}

    
    
}
