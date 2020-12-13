package com.uottawa.nlp.util;

import java.io.FileWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.uottawa.nlp.model.ExtractedResult;;

public class SmartCsvWriter {
	
	public static void main(String[] args) {
		
		writeToFile(Collections.EMPTY_LIST);
	} 
	
	public static void writeToFile(List<ExtractedResult> results)  {
		
		try {
			Writer writer= new FileWriter("output2.csv");
			
			StatefulBeanToCsv<ExtractedResult> beanToCSV = 
					new StatefulBeanToCsvBuilder<ExtractedResult>(writer).build();
			
//			List<Friend> friends = new ArrayList<Friend>();
//			friends.add(new Friend(10,"hp","10-Aug"));
//			friends.add(new Friend(10,"maddy","22-Jul"));
			
			
			beanToCSV.write(results);
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	 

}
