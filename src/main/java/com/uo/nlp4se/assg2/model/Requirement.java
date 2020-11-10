package com.uo.nlp4se.assg2.model;

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
@AllArgsConstructor
@NoArgsConstructor
public class Requirement {
	
	@CsvBindByPosition(position = 0)
	private Integer sno;
	
	@CsvBindByPosition(position = 1)
	private String modelName;
	
	@CsvBindByPosition(position = 2)
	private String path;	
	
	@CsvBindByPosition(position = 3)
	private String id;
	
	@CsvBindByPosition(position = 4)
	private String signalChunk;
	
	@CsvBindByPosition(position = 5)
	private List<String> signals;
	
	@CsvBindByPosition(position = 6)
	private String constants;
	
	@CsvBindByPosition(position = 7)
	private String descriptonModelName;	
	
	@CsvBindByPosition(position = 8)
	private String descripton;
	
	@CsvBindByPosition(position = 9)
	private String requirement;
	
	@CsvBindByPosition(position = 10)
	private String bucket; 

}
