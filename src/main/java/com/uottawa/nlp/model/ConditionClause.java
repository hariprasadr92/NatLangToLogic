package com.uottawa.nlp.model;

import java.util.Map;

import com.uottawa.nlp.util.Util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ConditionClause {

	private String subj;		
	private String modifier;
	private String verb;
	private String obj;	
	private String conj;
	private String subjectPhrase;
	private String objectPhrase;
	private String comparedObj; 
	private String comparedObjPhrase; 


	
	@Override
	public String toString() {		
		
		StringBuilder clause = new StringBuilder();
		clause.append("( ["+subjectPhrase+"] ");
		if(!Util.isNullOrEmpty(modifier)) {
			clause.append(modifier+" ");
		}
		
		clause.append(Util.getComparatorSymbol(verb)+" ");
		
		if(!Util.isNullOrEmpty(objectPhrase)) {
			clause.append("["+objectPhrase+"]"+" ");
		}		
		if(!Util.isNullOrEmpty(comparedObjPhrase)) {
			clause.append("["+comparedObjPhrase+"]"+" ");
		}		
		
		clause.append(" ) ");
		
		if(!Util.isNullOrEmpty(conj)) {
			clause.append(conj+" ");
		}
		
		return clause.toString();				
				
	}
	
	
	
}
