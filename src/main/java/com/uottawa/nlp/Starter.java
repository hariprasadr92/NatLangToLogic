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
			String filepath = "src/main/resources/Reqm_valid.csv";
			List<Requirement> data = SmartCsvReader.readRequirementsFromCsv(filepath);
						
			Integer counter = -1;
			
			List<ExtractedResult> results =  new ArrayList<ExtractedResult>(Collections.EMPTY_LIST); 
			
			for (Requirement reqm: data ) {
				System.out.println(reqm.getSno());
				ExtractedResult result= new ExtractedResult();
				//for debugging purpose
				//if(reqm.getSno()==56) { result = ComponentExtractor.extractComponents(reqm.getRequirement());
				
				
				result = ComponentExtractor.extractComponents(reqm.getRequirement());	
				result.setSno(reqm.getSno());
				FormatterPrinter.printResult(result);
				results.add(result);
				//}
			}			
			
			SmartCsvWriter.writeToFile(results);
			
		}
		catch (Exception e) {
		}
		
	}
	
	
	
	
	
}	
