package pl.ekosmiec.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FreeDay {
	
	private int id;
	private Date data;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return new SimpleDateFormat("dd MMMMM").format(data);
	}

	

}
