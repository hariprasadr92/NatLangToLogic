package com.uottawa.nlp.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Parameter;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.uottawa.nlp.model.LabelTextPair;
import com.uottawa.nlp.model.Requirement;

public class SmartCsvReader {
	
	/**
	 * Reads a 2 column CSV file into a java list of label text pairs
	 * @return List of label text pair 
	 * @throws FileNotFoundException
	 * @param filepath (from the root of project folder)
	 * 
	 */
	public static List<LabelTextPair> readFromCsv(String filepath) throws FileNotFoundException {
		
		FileReader reader = new FileReader(filepath);
		
		CsvToBean<LabelTextPair> csvToBean= new  CsvToBeanBuilder<LabelTextPair>(reader)
				.withType(LabelTextPair.class)
				.withSeparator(',')
				.build();
		
		return csvToBean.parse();
	}
	
	/**
	 * Reads CSV file into a java list of requirement objects
	 * @return List of label text pair 
	 * @throws FileNotFoundException
	 * @param filepath (from the root of project folder)
	 * 
	 */
	public static List<Requirement> readRequirementsFromCsv(String filepath) throws FileNotFoundException {
		
		FileReader reader = new FileReader(filepath);
		
		CsvToBean<Requirement> csvToBean= new  CsvToBeanBuilder<Requirement>(reader)
				.withType(Requirement.class)
				.withSeparator(',')
				.build();
		
		return csvToBean.parse();
	}

}
