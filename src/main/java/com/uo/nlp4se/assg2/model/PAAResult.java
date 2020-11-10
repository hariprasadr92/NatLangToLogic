package com.uo.nlp4se.assg2.model;

import com.opencsv.bean.CsvBindByPosition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

/**
 * 
 * @author Hari
 * PAA stands for Pro-nominal Anaphora Analysis. This is a class thats is equivalent to once instance of 
 * analyzed output data of our NLP pipeline
 * 
 */
public class PAAResult {
	
	@CsvBindByPosition(position = 0)
	private String label;
	
	@CsvBindByPosition(position = 1)
	private	String text; 
	
	@CsvBindByPosition(position = 2)
	private Integer	f1Position;
	
	@CsvBindByPosition(position = 3)
	private Integer	f2NTokens;
	
	@CsvBindByPosition(position = 4)
	private Integer	f3NPunc;
	
	@CsvBindByPosition(position = 5)
	private Integer	f4NPrecNP;
	
	@CsvBindByPosition(position = 6)
	private Integer	f5NSuccNP;
	
	@CsvBindByPosition(position = 7)
	private Boolean	f6IsFollowsPP;
	
	@CsvBindByPosition(position = 8)
	private String f7POSNeighboursB1;
	
	@CsvBindByPosition(position = 9)
	private String f7POSNeighboursB2;
	
	@CsvBindByPosition(position = 10)
	private String f7POSNeighboursB3;
	
	@CsvBindByPosition(position = 11)
	private String f7POSNeighboursB4;
	
	@CsvBindByPosition(position = 12)
	private String f7POSNeighboursA1;
	
	@CsvBindByPosition(position = 13)
	private String f7POSNeighboursA2;
	
	@CsvBindByPosition(position = 14)
	private String f7POSNeighboursA3;
	
	@CsvBindByPosition(position = 15)
	private String f7POSNeighboursA4;
	
	@CsvBindByPosition(position = 16)
	private Boolean	f8IsFollByGerund;
	
	@CsvBindByPosition(position = 17)
	private Boolean	f9IsFollByPrep;
	
	@CsvBindByPosition(position = 18)
	private Integer	f10NFollAdj;
	
	@CsvBindByPosition(position = 19)
	private Boolean	f11IsPrecByVerb;
	
	@CsvBindByPosition(position = 20)
	private Boolean	f12IsFollByVerb;
	
	@CsvBindByPosition(position = 21)
	private Boolean	f13IsFollByAdj;
	
	@CsvBindByPosition(position = 22)
	private Boolean	f14IsFollByNPAdj;
	
	@CsvBindByPosition(position = 23)
	private Integer	f15NTkPrecInfVerb;
	
	@CsvBindByPosition(position = 24)
	private Integer	f16NtkBtwPrep;
	
	@CsvBindByPosition(position = 25)
	private Boolean	f17IsFollByAdjNP;
	
	@CsvBindByPosition(position = 26)
	private String f18DepRel;
	
	@CsvBindByPosition(position = 27)
	private Boolean	f19IsFollByWeathAdj;
	
	@CsvBindByPosition(position = 28)
	private Boolean	f20IsFollByCognitiveVerbs;
}
