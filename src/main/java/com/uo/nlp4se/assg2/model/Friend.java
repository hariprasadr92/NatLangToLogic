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
public class Friend {
	
	@CsvBindByPosition(position = 0)
	private Integer id;
	
	@CsvBindByPosition(position = 2)
	private String name;
	
	@CsvBindByPosition(position = 1)
	private String dob;

}
