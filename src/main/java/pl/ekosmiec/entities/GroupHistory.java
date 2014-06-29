package pl.ekosmiec.entities;

import java.util.Date;

public class GroupHistory {
    private Integer id;
    //ref_grupa int references ekosmiec.grupy(id),
    private Date data;
    private float laczna_pojemnosc;
    private float odebrano;
    private String opis;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public float getLaczna_pojemnosc() {
		return laczna_pojemnosc;
	}
	public void setLaczna_pojemnosc(float laczna_pojemnosc) {
		this.laczna_pojemnosc = laczna_pojemnosc;
	}
	public float getOdebrano() {
		return odebrano;
	}
	public void setOdebrano(float odebrano) {
		this.odebrano = odebrano;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	@Override
	public String toString() {
		return "GroupHistory [id=" + id + ", data=" + data
				+ ", laczna_pojemnosc=" + laczna_pojemnosc + ", odebrano="
				+ odebrano + ", opis=" + opis + "]";
	}
    
    
}
