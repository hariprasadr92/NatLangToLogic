package com.uottawa.nlp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uottawa.nlp.model.ExtractedResult;
import com.uottawa.nlp.model.Requirement;
import com.uottawa.nlp.util.FormatterPrinter;
import com.uottawa.nlp.util.SmartCsvReader;
import com.uottawa.nlp.util.SmartCsvWriter;



public class Starter {

	public static void main(String[] args) {
		try {
			String filepath = "src/main/resources/rerm1-allgood.csv";
			List<Requirement> data = SmartCsvReader.readRequirementsFromCsv(filepath);
						
			Integer counter = 0;
			
			List<ExtractedResult> results =  new ArrayList<ExtractedResult>(Collections.EMPTY_LIST);
			
			for (Requirement reqm: data ) {
				counter++;
				System.out.println(counter);
				ExtractedResult result= new ExtractedResult();
				//for debugging purpose
				//if(counter==1) { result = ComponentExtractor.extractComponents(reqm.getRequirement());}
				
				
				result = ComponentExtractor.extractComponents(reqm.getRequirement());	
				result.setSno(reqm.getSno());
				FormatterPrinter.printResult(result);
				results.add(result);
			}			
			
			SmartCsvWriter.writeToFile(results);
			
		}
		catch (Exception e) {
		}
		
	}
	
	
	
	
	
}	
