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
	private String modifier;
	private String verb;
	private String dependentVerb;
	private String dependentmodifier;
	private String value;
	private String unit;
	private String obj;
	private String from;
	private String to;
	private String conj;
	
	@Override
	public String toString() {
		StringBuffer clause = new StringBuffer();
		
		clause.append("( "+subject+" ");
		if(!Util.isNullOrEmpty(modifier)) {
			clause.append(modifier+" ");
		}
		clause.append(verb+" ");
		if(!Util.isNullOrEmpty(value)) {
			clause.append(value+" ");
		}
		if(!Util.isNullOrEmpty(unit)) {
			clause.append(unit+" ");
		}
		if(!Util.isNullOrEmpty(obj)) {
			clause.append(obj+" ");
		}
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