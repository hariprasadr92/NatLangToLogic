package com.uottawa.nlp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Util {
	
	public static void main(String[] args) {

		getComparatorSymbol("test");
	}

	public static boolean isNullOrEmpty (Object obj) {        
	       
	      return (obj==null || obj=="");  
	}
	
	public static String getComparatorSymbol (String comparatorPhrase) {
		
		Map<String,String> comparators = new HashMap<String, String>();
		comparators.put("equal", "=");
		comparators.put("less than", "<");
		comparators.put("less", "<");
		comparators.put("lower", "<");
		comparators.put("greater than", ">");
		comparators.put("lesser", "<");
		comparators.put("lesser than", "<");
		comparators.put("greater", ">");
		comparators.put("exceed", ">");
		comparators.put("more", ">");
		comparators.put("not", "!");

		String comparator = comparators.get(comparatorPhrase);					
		return (isNullOrEmpty(comparator))?comparatorPhrase:comparator;		
	}
	
	public static void printMap(Map<String,String> map) {
	    Set s = map.entrySet();
	    Iterator it = s.iterator();
	    while ( it.hasNext() ) {
	       Map.Entry entry = (Map.Entry) it.next();
	       String key = (String) entry.getKey();
	       String value = (String) entry.getValue();
	       System.out.print(key + " => " + value+" | ");
	    }//while
	    System.out.println("========================");
	}//printMap
	
	public static String getMapValues(Map<String,String> map) {
	    Set s = map.entrySet();
	    Iterator it = s.iterator();
       StringBuilder values = new StringBuilder();
	    while ( it.hasNext() ) {
	       Map.Entry entry = (Map.Entry) it.next();
	       values.append(" ");
	       values.append((String) entry.getValue());

	    }//while
	    
	    return values.toString();
	}
	
}
