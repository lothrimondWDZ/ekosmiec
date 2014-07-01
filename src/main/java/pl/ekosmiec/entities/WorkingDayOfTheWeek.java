package pl.ekosmiec.entities;

public class WorkingDayOfTheWeek {

	private int id;
	private int dzien_tygodnia;
	private int ilosc;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDzien_tygodnia() {
		return dzien_tygodnia;
	}
	public void setDzien_tygodnia(int dzien_tygodnia) {
		this.dzien_tygodnia = dzien_tygodnia;
	}
	public int getIlosc() {
		return ilosc;
	}
	public void setIlosc(int ilosc) {
		this.ilosc = ilosc;
	}
	@Override
	public String toString() {
		return "WorkingDayOfTheWeek [id=" + id + ", dzien_tygodnia="
				+ dzien_tygodnia + ", ilosc=" + ilosc + "]";
	}
	
}
