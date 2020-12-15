package com.uottawa.nlp.model;

import java.util.List;

import com.opencsv.bean.CsvBindByPosition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class ExtractedResult {
	
	@CsvBindByPosition(position = 0)    //a
	private String sno;
	
	@CsvBindByPosition(position = 1)    //b
	private String subject;
	
	@CsvBindByPosition(position = 2)    //c
	private String relation;
	
	@CsvBindByPosition(position = 3)    //d
	private String condAux;
	
	@CsvBindByPosition(position = 4)    //e
	private String condModifier;
	
	@CsvBindByPosition(position = 5)    //f
	private String condTense;
	
	@CsvBindByPosition(position = 6)    //g
	private String condVerb;
	
	@CsvBindByPosition(position = 7)    //h
	private String value;
	
	@CsvBindByPosition(position = 8)    //i
	private String unit;
	
	@CsvBindByPosition(position = 9)	//j
	private String obj1;
	
	@CsvBindByPosition(position = 10)    //k
	private String from1;
	
	@CsvBindByPosition(position = 11)    //l
	private String to1;	
	
	@CsvBindByPosition(position = 12)    //m
	private String condClause1Subj;	
	
	@CsvBindByPosition(position = 13)    //n
	private String  condClause1Modifier;
	
	@CsvBindByPosition(position = 14)    //o
	private String  condClause1Obj;

	@CsvBindByPosition(position = 15)    //p
	private String  condClause1Conj;
	
	@CsvBindByPosition(position = 16)    //q
	private String  condClause1Verb;	
	
	private List<ConditionClause> conditions;	
	private List<MainClause> actions;	

	
}
