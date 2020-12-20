package com.uottawa.nlp.model;

import com.opencsv.bean.CsvBindByPosition;
import com.uottawa.nlp.util.Util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MainClause {
	
	private String subject;
	private String subjectPhrase;
	private String modifier;
	private String verb;
	private String dependentVerb;
	private String dependentmodifier;
	private String comparedObj; 
	private String value;
	private String unit;
	private String obj;
	private String from;
	private String to;
	private String conj;
	
	@Override
	public String toString() {
		StringBuilder clause = new StringBuilder();
		
		clause.append("( ["+subjectPhrase+"] ");
		if(!Util.isNullOrEmpty(modifier)) {
			clause.append(modifier+" ");
		}
		clause.append(Util.getComparatorSymbol(verb)+" ");
		
		if(!Util.isNullOrEmpty(obj) && (Util.isNullOrEmpty(value))) {
			clause.append("["+obj+"]"+" ");
		}
		if(!Util.isNullOrEmpty(comparedObj) && (Util.isNullOrEmpty(value))) {
			clause.append("["+comparedObj+"]"+" ");
		}
		
		if(!Util.isNullOrEmpty(value)) {
			clause.append(value+" ");
		}
		if(!Util.isNullOrEmpty(unit)) {
			clause.append(unit+" ");
		}
		
//		if(!Util.isNullOrEmpty(subject.getControlledSubject())) {
//			clause.append(subject.getControlledSubject()+" ");
//		}
		
		
		
		if(!Util.isNullOrEmpty(from)) {
			clause.append(from+" ");
		}
		if(!Util.isNullOrEmpty(from) && !Util.isNullOrEmpty(to) ) {
			clause.append(" -> ");
		}
		if(!Util.isNullOrEmpty(to)) {
			clause.append(to+" ");
		}
		clause.append(" ) ");
		if(!Util.isNullOrEmpty(conj)) {
			clause.append(conj+" ");
		}
		
		return clause.toString();	
	}	
	
	

}
