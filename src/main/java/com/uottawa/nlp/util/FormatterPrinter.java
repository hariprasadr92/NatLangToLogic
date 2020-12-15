package com.uottawa.nlp.util;

import com.uottawa.nlp.model.ConditionClause;
import com.uottawa.nlp.model.ExtractedResult;
import com.uottawa.nlp.model.MainClause;

public class FormatterPrinter {

	/**
	 * for printing the extracted results in console.
	 */
	
	public static void printResult(ExtractedResult result) {
		System.out.println("----------------------------------------------");
		
		// print the conditions
		if(!result.getConditions().isEmpty()) {
			System.out.print("IF (");
			for(ConditionClause cond:result.getConditions() ) {
				System.out.print(cond.toString());
			}
			System.out.print(" )\n{\n\t");
		}	
		// print the actions
		for(MainClause action:result.getActions() ) {
			System.out.print(action.toString());
		}	
		
		if(!result.getConditions().isEmpty()) {
			System.out.print("\n}");
		}	
		System.out.print("\n----------------------------------------------");
		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

	}
	
}
