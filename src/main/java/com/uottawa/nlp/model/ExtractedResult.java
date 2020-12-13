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
	
	@CsvBindByPosition(position = 0)
	private String sno;
	
	@CsvBindByPosition(position = 1)
	private String subject;
	
	@CsvBindByPosition(position = 2)
	private String relation;
	
	@CsvBindByPosition(position = 3)
	private String value;
	
	@CsvBindByPosition(position = 4)
	private String unit;
	
	@CsvBindByPosition(position = 5)
	private String condAux;
	
	@CsvBindByPosition(position = 6)
	private String condModifier;
	
	@CsvBindByPosition(position = 7)
	private String condTense;
	
	@CsvBindByPosition(position = 8)
	private String condVerb;
	
	@CsvBindByPosition(position = 9)
	private String obj1;
	
	@CsvBindByPosition(position = 10)
	private String from1;
	
	@CsvBindByPosition(position = 11)
	private String to1;
	
	@CsvBindByPosition(position = 12)
	private String cond1;	
	
	@CsvBindByPosition(position = 13)
	private String cond2;	

}
