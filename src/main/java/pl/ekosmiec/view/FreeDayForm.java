package pl.ekosmiec.view;

import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pl.ekosmiec.entities.FreeDay;

public class FreeDayForm {
	
	private int day;
	private int month;
	
	public FreeDayForm(){}
	public FreeDayForm(FreeDay fd){
		
		this.day = fd.getData().getDate();
		this.month = fd.getData().getMonth() + 1;
		
	}
	
	public FreeDay toFreeDay(){
		FreeDay fd = new FreeDay();
		fd.setData(new Date(2014 - 1900, this.month -1, this.day));
		return fd;
	}
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int mounth) {
		this.month = mounth;
	}
	@Override
	public String toString() {
		
        LocalDate date = new LocalDate(2014, month, day);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMM");
        String out = formatter.print(date);
		
		return out;
	}
	
	

}
