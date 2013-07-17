package com.dmitrij.doberstein.spritfuchs.dataclasses;

import java.util.List;

import com.dmitrij.doberstein.spritfuchs.SortePreis;

public class TankstellenPosition {
	private String tankstelleId;
	private String tankstelleName;
	private String tankstelleStrasse;
	private String tankstelleHausnr;
	private String tankstellePlz;
	private String tankstelleOrt;
	private String tankstelleEntfernung;
	private String tankstelleDiesel;
	private String tankstelleE5;
	private String tankstelleE10;
	private String tankstelleMarke;
	private String tankstelleLatitude;
	private String tankstelleLongtitude;
	private String tankstelleGemeldet;
	
	private List<SortePreis> tankstellePreise;
		
	public List<SortePreis> getTankstellePreise() {
		return tankstellePreise;
	}
	public void setTankstellePreise(List<SortePreis> tankstellePreise) {
		this.tankstellePreise = tankstellePreise;
	}
	public String getTankstelleGemeldet() {
		return tankstelleGemeldet;
	}
	public void setTankstelleGemeldet(String tankstelleGemeldet) {
		this.tankstelleGemeldet = tankstelleGemeldet;
	}
	public String getTankstelleLatitude() {
		return tankstelleLatitude;
	}
	public void setTankstelleLatitude(String tankstelleLatitude) {
		this.tankstelleLatitude = tankstelleLatitude;
	}
	public String getTankstelleLongtitude() {
		return tankstelleLongtitude;
	}
	public void setTankstelleLongtitude(String tankstelleLongtitude) {
		this.tankstelleLongtitude = tankstelleLongtitude;
	}
	public String getTankstelleMarke() {
		return tankstelleMarke;
	}
	public void setTankstelleMarke(String tankstelleMarke) {
		this.tankstelleMarke = tankstelleMarke;
	}
	public String getTankstelleId() {
		return tankstelleId;
	}
	public void setTankstelleId(String tankstelleId) {
		this.tankstelleId = tankstelleId;
	}
	public String getTankstellePlz() {
		return tankstellePlz;
	}
	public void setTankstellePlz(String tankstellePlz) {
		this.tankstellePlz = tankstellePlz;
	}
	public String getTankstelleOrt() {
		return tankstelleOrt;
	}
	public void setTankstelleOrt(String tankstelleOrt) {
		this.tankstelleOrt = tankstelleOrt;
	}
	public String getTankstelleName() {
		return tankstelleName;
	}
	public void setTankstelleName(String tankstelleName) {
		this.tankstelleName = tankstelleName;
	}
	public String getTankstelleStrasse() {
		return tankstelleStrasse;
	}
	public void setTankstelleStrasse(String tankstelleStrasse) {
		this.tankstelleStrasse = tankstelleStrasse;
	}
	public String getTankstelleHausnr() {
		return tankstelleHausnr;
	}
	public void setTankstelleHausnr(String tankstelleHausnr) {
		this.tankstelleHausnr = tankstelleHausnr;
	}
	public String getTankstelleEntfernung() {
		return tankstelleEntfernung;
	}
	public void setTankstelleEntfernung(String tankstelleEntfernung) {
		this.tankstelleEntfernung = tankstelleEntfernung;
	}
	public String getTankstelleDiesel() {
		return tankstelleDiesel;
	}
	public void setTankstelleDiesel(String tankstelleDiesel) {
		this.tankstelleDiesel = tankstelleDiesel;
	}
	public String getTankstelleE5() {
		return tankstelleE5;
	}
	public void setTankstelleE5(String tankstelleE5) {
		this.tankstelleE5 = tankstelleE5;
	}
	public String getTankstelleE10() {
		return tankstelleE10;
	}
	public void setTankstelleE10(String tankstelleE10) {
		this.tankstelleE10 = tankstelleE10;
	}
	
	@Override
	public String toString() {
		return "[" +
					"tankstelleId=" + tankstelleId + 
					"tankstelleName=" + tankstelleName +
					",tankstelleStrasse=" + tankstelleStrasse + 
					",tankstelleHausnr=" + tankstelleHausnr + 
					",tankstellePlz=" + tankstellePlz + 
					",tankstelleOrt=" + tankstelleOrt + 
					",tankstelleEntfernung" + tankstelleEntfernung + 
					",tankstelleDiesel" + tankstelleDiesel + 
					",tankstelleE5" + tankstelleE5 + 
					",tankstelleE10" + tankstelleE10 +
				"]";
    }
}
