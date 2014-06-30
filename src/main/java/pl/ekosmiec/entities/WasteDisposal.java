package pl.ekosmiec.entities;

import java.util.Date;



public class WasteDisposal {
	
	private int id;
	private int ref_grupa;
	private Date data;
	
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
	public Date getData() {
		return data;
	}
	public void setData(Date date) {
		this.data = date;
	}
	
	

}
