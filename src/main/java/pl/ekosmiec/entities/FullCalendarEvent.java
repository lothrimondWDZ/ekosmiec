package pl.ekosmiec.entities;

public class FullCalendarEvent {

	String title;
	String start;
	boolean allDay = true;
	
	public String getTilte() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	
	
	
	
}
