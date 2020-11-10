package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.uo.nlp4se.assg2.model.Friend;
import com.uo.nlp4se.assg2.model.PAAResult;;

public class SmartCsvWriter {
	
	public static void main(String[] args) {
		
		writeToFile(Collections.EMPTY_LIST);
	} 
	
	public static void writeToFile(List<PAAResult> results)  {
		
		try {
			Writer writer= new FileWriter("tgt.csv");
			
			StatefulBeanToCsv<PAAResult> beanToCSV = 
					new StatefulBeanToCsvBuilder<PAAResult>(writer).build();
			
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
