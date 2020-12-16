package com.uottawa.nlp.util;

import java.util.HashMap;
import java.util.Map;

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
	
}
