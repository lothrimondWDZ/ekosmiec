package pl.ekosmiec.view;

import pl.ekosmiec.entities.WorkingDayOfTheWeek;

public class ScheduleForm {
	private WorkingDayOfTheWeek monday;
	private WorkingDayOfTheWeek tuesday;
	private WorkingDayOfTheWeek wednesday;
	private WorkingDayOfTheWeek thursday;
	private WorkingDayOfTheWeek friday;
	private WorkingDayOfTheWeek saturday;
	private WorkingDayOfTheWeek sunday;
	
	public WorkingDayOfTheWeek getMonday() {
		return monday;
	}
	public void setMonday(WorkingDayOfTheWeek monday) {
		this.monday = monday;
	}
	public WorkingDayOfTheWeek getTuesday() {
		return tuesday;
	}
	public void setTuesday(WorkingDayOfTheWeek tuesday) {
		this.tuesday = tuesday;
	}
	public WorkingDayOfTheWeek getWednesday() {
		return wednesday;
	}
	public void setWednesday(WorkingDayOfTheWeek wednesday) {
		this.wednesday = wednesday;
	}
	public WorkingDayOfTheWeek getThursday() {
		return thursday;
	}
	public void setThursday(WorkingDayOfTheWeek thursday) {
		this.thursday = thursday;
	}
	public WorkingDayOfTheWeek getFriday() {
		return friday;
	}
	public void setFriday(WorkingDayOfTheWeek friday) {
		this.friday = friday;
	}
	public WorkingDayOfTheWeek getSaturday() {
		return saturday;
	}
	public void setSaturday(WorkingDayOfTheWeek saturday) {
		this.saturday = saturday;
	}
	public WorkingDayOfTheWeek getSunday() {
		return sunday;
	}
	public void setSunday(WorkingDayOfTheWeek sunday) {
		this.sunday = sunday;
	}
}
