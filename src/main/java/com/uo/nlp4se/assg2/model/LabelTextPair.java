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
public class LabelTextPair {
	
	@CsvBindByPosition(position = 0)
	private String label;
	
	@CsvBindByPosition(position = 1)
	private String text;

}
