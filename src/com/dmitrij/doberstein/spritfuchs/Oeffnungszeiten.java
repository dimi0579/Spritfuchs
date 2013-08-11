package com.dmitrij.doberstein.spritfuchs;

public class Oeffnungszeiten {
	private String ozMontag = "";
	private String ozDienstag = "";
	private String ozMittwoch = "";
	private String ozDonnerstag = "";
	private String ozFreitag = "";
	private String ozSamstag = "";
	private String ozSonntagFeiertag = "";
	
	public Oeffnungszeiten() {
		super();
		this.ozMontag = "00:00";
		this.ozDienstag = "00:00";
		this.ozMittwoch = "00:00";
		this.ozDonnerstag = "00:00";
		this.ozFreitag = "00:00";
		this.ozSamstag = "00:00";
		this.ozSonntagFeiertag = "00:00";
	}

	public Oeffnungszeiten(String ozMontag, String ozDienstag,
			String ozMittwoch, String ozDonnerstag, String ozFreitag,
			String ozSamstag, String ozSonntagFeiertag) {
		super();
		this.ozMontag = ozMontag;
		this.ozDienstag = ozDienstag;
		this.ozMittwoch = ozMittwoch;
		this.ozDonnerstag = ozDonnerstag;
		this.ozFreitag = ozFreitag;
		this.ozSamstag = ozSamstag;
		this.ozSonntagFeiertag = ozSonntagFeiertag;
	}

	public String getOzMontag() {
		if(this.ozMontag.isEmpty()){
			return "geschlossen";
		}
		return ozMontag;
	}

	public void setOzMontag(String ozMontag) {
		this.ozMontag = ozMontag;
	}

	public String getOzDienstag() {
		if(this.ozDienstag.isEmpty()){
			return "geschlossen";
		}
		return ozDienstag;
	}

	public void setOzDienstag(String ozDienstag) {
		this.ozDienstag = ozDienstag;
	}

	public String getOzMittwoch() {
		if(this.ozMittwoch.isEmpty()){
			return "geschlossen";
		}
		return ozMittwoch;
	}

	public void setOzMittwoch(String ozMittwoch) {
		this.ozMittwoch = ozMittwoch;
	}

	public String getOzDonnerstag() {
		if(this.ozDonnerstag.isEmpty()){
			return "geschlossen";
		}
		return ozDonnerstag;
	}

	public void setOzDonnerstag(String ozDonnerstag) {
		this.ozDonnerstag = ozDonnerstag;
	}

	public String getOzFreitag() {
		if(this.ozFreitag.isEmpty()){
			return "geschlossen";
		}
		return ozFreitag;
	}

	public void setOzFreitag(String ozFreitag) {
		this.ozFreitag = ozFreitag;
	}

	public String getOzSamstag() {
		if(this.ozSamstag.isEmpty()){
			return "geschlossen";
		}
		return ozSamstag;
	}

	public void setOzSamstag(String ozSamstag) {
		this.ozSamstag = ozSamstag;
	}

	public String getOzSonntagFeiertag() {
		if(this.ozSonntagFeiertag.isEmpty()){
			return "geschlossen";
		}
		return ozSonntagFeiertag;
	}

	public void setOzSonntagFeiertag(String ozSonntagFeiertag) {
		this.ozSonntagFeiertag = ozSonntagFeiertag;
	}
	
	public String getTagString(){
		String ret = "Montag" +
				"\nDienstag" + 
				"\nMittwoch" + 
				"\nDonnerstag" + 
				"\nFreitag" + 
				"\nSamstag" + 
				"\nSonntag" + 
				"\n und Feiertage";
		
		return ret;
	}
	public String getVonBisString(){
		String ret = this.getOzMontag() + 
				"\n" + this.getOzDienstag() + 
				"\n" + this.getOzMittwoch() + 
				"\n" + this.ozDonnerstag + 
				"\n" + this.ozFreitag + 
				"\n" + this.ozSamstag + 
				"\n\n" + this.ozSonntagFeiertag ;
		
		return ret;
	}
}
