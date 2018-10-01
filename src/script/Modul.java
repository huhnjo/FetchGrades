package script;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Modul {
	
	private int pruefNr;
	private String semester;
	private Calendar date;
	private int pruefTermin;
	private String modulName;
	private String prof;
	private String typ;
	private double grade;
	private int ects;
	private boolean passed;
	private int NrOfTries;
	
	public Modul(String modulName, double grade, int ects) {
		
		this.modulName = modulName;
		
		this.grade = grade;
		this.ects = ects;
		
	}
	
	public Modul(int pruefNr, String semester, Calendar date, int pruefTermin, String modulName, String prof,
			String typ, double grade, int ects, boolean passed, int  NrOfTries) {
		this.pruefNr = pruefNr;
		this.semester = semester;
		this.date = date;
		this.pruefTermin = pruefTermin;
		this.modulName = modulName;
		this.prof = prof;
		this.typ = typ;
		this.grade = grade;
		this.ects = ects;
		this.passed = passed;
		this.NrOfTries = NrOfTries;
	}

	public int getPruefNr() {
		return pruefNr;
	}

	public String getSemester() {
		return semester;
	}

	public Calendar getDate() {
		return date;
	}

	public int getPruefTermin() {
		return pruefTermin;
	}

	public String getModulName() {
		return modulName;
	}

	public String getProf() {
		return prof;
	}

	public String getTyp() {
		return typ;
	}

	public double getGrade() {
		return grade;
	}

	public int getEcts() {
		return ects;
	}

	public boolean isPassed() {
		return passed;
	}

	public int getNrOfTries() {
		return NrOfTries;
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("MODUL:\n");
		buf.append("PrüfungsNr:" + this.pruefNr + "\n");
		buf.append("Semester: " + this.semester + "\n");
		buf.append("Date: " + new SimpleDateFormat("dd.MM.yyyy").format(new Date(this.date.getTimeInMillis())) + "\n");
		buf.append("Prüftermin: " + this.pruefTermin + "\n");
		buf.append("Modulname: " + this.modulName + "\n");
		buf.append("Prof: " + this.prof + "\n");
		buf.append("Prüfungstyp: " + this.typ + "\n");
		buf.append("Note: " + this.grade + "\n");
		buf.append("ECTS: " + this.ects + "\n");
		buf.append("Bestanden" + this.passed + "\n");
		buf.append("Versuche: " + this.NrOfTries + "\n");
		buf.append("-----------------------------------------------------------------------------------------------\n");
		return  buf.toString();
	}
}
