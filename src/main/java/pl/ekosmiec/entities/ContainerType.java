package pl.ekosmiec.entities;

public class ContainerType {

    private int id;
    private String nazwa;
    private float pojemnosc;
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
	public float getPojemnosc() {
		return pojemnosc;
	}
	public void setPojemnosc(float pojemnosc) {
		this.pojemnosc = pojemnosc;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
    
    
}
