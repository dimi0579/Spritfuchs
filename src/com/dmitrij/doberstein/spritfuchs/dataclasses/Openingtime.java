package com.dmitrij.doberstein.spritfuchs.dataclasses;

import java.io.Serializable;

public class Openingtime implements Serializable{
	private static final long serialVersionUID = 1L;
	
		private Day day;
        private String from;
        private String till;

		public Openingtime(Day day, String from, String till) {
			super();
			this.day = day;
			this.from = from;
			this.till = till;
		}
		
		public Day getDay() {
			return day;
		}
		public void setDay(Day day) {
			this.day = day;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getTill() {
			return till;
		}
		public void setTill(String till) {
			this.till = till;
		}
}
