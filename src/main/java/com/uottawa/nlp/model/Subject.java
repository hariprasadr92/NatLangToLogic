package com.uottawa.nlp.model;

import com.uottawa.nlp.util.Util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Subject {
	
	private String rootSubj;
	private String compound;
	private String amod;
	private String nmod;
	private String controlledSubject;
		
	@Override
	public String toString() {
		StringBuilder clause = new StringBuilder();
		

		clause.append("[ ");
		clause.append(" ");
		clause.append(controlledSubject);
		clause.append(" ]");

		
		return clause.toString();
	}
	
	
	
	
}
