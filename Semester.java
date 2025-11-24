package edu.ccrm.domain;

public enum Semester {
	SPRING("Spring"),
	SUMMER("Summer"),
	FALL("Fall");
	
	private final String label;
	
	Semester(String label){
		this.label = label;
	}
	public String getLabel() {
		return label;
	}

}
