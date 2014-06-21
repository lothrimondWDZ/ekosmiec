package pl.ekosmiec.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ekosmiec.rodzaje_kontenerow")
public class ContainersTypes {
	@Id  
	@Column(name = "id")
	private Integer id;
	
	@Column(name="nazwa")
	private String nazwa;
	
	@Column(name="pojemnosc")
	private Integer pojemnosc;
	
	@Column(name="opis")
	private String opis;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public Integer getPojemnosc() {
		return pojemnosc;
	}

	public void setPojemnosc(Integer pojemnosc) {
		this.pojemnosc = pojemnosc;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	@Override
	public String toString() {
		return "ContainersTypes [id=" + id + ", nazwa=" + nazwa
				+ ", pojemnosc=" + pojemnosc + ", opis=" + opis + "]";
	}
	
	
}
