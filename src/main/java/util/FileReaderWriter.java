package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.uo.nlp4se.assg2.model.Friend;

public class FileReaderWriter {
	
	public static void main(String[] args) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		
		SmartCsvWriter smartCSVWriter = new SmartCsvWriter();
		SmartCsvReader smartCsvReader = new SmartCsvReader();
		
		try {
			doWrite();
			//smartCSVWriter.writeToFile();
//			List<Friend> friendsFromCSV = smartCsvReader.readFromCsv("dummypath");
//			
//			for( Friend friend: friendsFromCSV) {
//				System.out.println(friend);
//			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		  
	
	public List<String[]> readAll(Reader reader) throws Exception {
		
	    CSVReader csvReader = new CSVReader(reader);
	    List<String[]> list = new ArrayList<String[]>();
	    list = csvReader.readAll();
	    reader.close();
	    csvReader.close();
	    return list;
	}
	
	public static void doWrite() throws IOException{
		
		System.out.println("called");
		CSVWriter writer= new CSVWriter(new FileWriter("output.csv"));
		List<String[]>  therows = new ArrayList<String[]>();
		
		String[] header = new String[] {"id","name","dob"};
		therows.add(header);

		String[] row1 = new String[] {"1","hp","10-Aug-1992"};
		String[] row2 = new String[] {"2","maddy","22-Jul-1993"};

		therows.add(row1);
		therows.add(row2);
		
		System.out.println("startwriting");

		writer.writeAll(therows);
		System.out.println("donewriting");

		writer.close();
		
	}

}
