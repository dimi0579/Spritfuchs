package com.dmitrij.doberstein.spritfuchs.dataclasses;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class BussinessHours implements Serializable{
	private static final long serialVersionUID = 1L;
	private Day day;
	private long from;
	private long to;

	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	public BussinessHours(Day day, long from, long to) {
		super();
		this.day = day;
		this.from = from;
		this.to = to;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public long getTo() {
		return to;
	}

	public void setTo(long to) {
		this.to = to;
	}
	
	public String getFromToString(){
		String ret = "";
		
		Time t = new Time(this.from);
		
		ret = sdf.format(t);
		return ret;
	}
	
	public String getToToString(){
		String ret = "";
		
		Time t = new Time(this.to);
		
		ret = sdf.format(t);
		
		return ret;
	}
}