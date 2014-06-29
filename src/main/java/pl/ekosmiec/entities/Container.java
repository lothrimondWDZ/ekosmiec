package pl.ekosmiec.entities;

public class Container {
	
    private int id;
    private int ref_grupa;
    private int ref_rodzaj_kontenera;
    private double lokalizacjaX;
    private double lokalizacjaY;
    private String opis;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRef_grupa() {
		return ref_grupa;
	}
	public void setRef_grupa(int ref_grupa) {
		this.ref_grupa = ref_grupa;
	}
	public int getRef_rodzaj_kontenera() {
		return ref_rodzaj_kontenera;
	}
	public void setRef_rodzaj_kontenera(int ref_rodzaj_kontenera) {
		this.ref_rodzaj_kontenera = ref_rodzaj_kontenera;
	}
	public double getLokalizacjaX() {
		return lokalizacjaX;
	}
	public void setLokalizacjaX(double lokalizacjaX) {
		this.lokalizacjaX = lokalizacjaX;
	}
	public double getLokalizacjaY() {
		return lokalizacjaY;
	}
	public void setLokalizacjaY(double lokalizacjaY) {
		this.lokalizacjaY = lokalizacjaY;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
    
    
}
