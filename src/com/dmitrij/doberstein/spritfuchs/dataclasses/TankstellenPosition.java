package com.dmitrij.doberstein.spritfuchs.dataclasses;

public class TankstellenPosition {
	private String tankstelleName;
	private String tankstelleStrasse;
	private String tankstelleHausnr;
	private String tankstellePlz;
	private String tankstelleOrt;
	private String tankstelleEntfernung;
	private String tankstelleDiesel;
	private String tankstelleE5;
	private String tankstelleE10;
	
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
