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
		

		clause.append("[");
		if(!Util.isNullOrEmpty(amod)) {
			clause.append(amod+" ");
		}
		if(!Util.isNullOrEmpty(compound)) {
			clause.append(compound+" ");
		}
		if(!Util.isNullOrEmpty(rootSubj)) {
			clause.append(rootSubj+" ");
		}
		if(!Util.isNullOrEmpty(nmod)) {
			clause.append(nmod+" ");
		}
		clause.append("]");

		
		return clause.toString();
	}
	
	
	
	
}
