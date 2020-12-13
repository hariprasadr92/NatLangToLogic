package com.uottawa.nlp;

import java.util.List;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.dictionary.Dictionary;


import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;

public class WordnetUtil {
	
	public static final POS VERB = POS.VERB;

	  
    public static void main(String[] args) {
    	
    	
    	  
    	try {
    		
			Dictionary d = Dictionary.getDefaultResourceInstance();
			

			IndexWord indexWord1 = d.lookupIndexWord(VERB, "ransacking");
			List<Synset> senses = indexWord1.getSenses();
			if (!senses.isEmpty()) {
				for (Synset s:  senses) {		
					System.out.println(s.getLexFileName());
//					if(s.getLexFileName().equals("verb.cognition")) {
//						break;
//					}	
				}
			}			
		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    
    }
    
    public static boolean checkIfWeatherVerb(String verb) {
    	
    	boolean isWeatherVerb = false;

    	try {    		
			Dictionary d = Dictionary.getDefaultResourceInstance();	

			IndexWord indexWord = d.lookupIndexWord(VERB, verb);
			if(indexWord!=null) {
				List<Synset> senses = indexWord.getSenses();
				if (!senses.isEmpty()) {
					for (Synset s:  senses) {
						if(s.getLexFileName().equals("verb.weather")) {
							isWeatherVerb=true;
							break;
						}
					}
				}	
			}
					
			return isWeatherVerb;
			
		} catch (JWNLException e) {

			e.printStackTrace();
			return isWeatherVerb;

		}
    }
    
    public static boolean checkIfCognitionVerb(String verb) {
    	
    	boolean isCognitionVerb = false;

    	try {    		
			Dictionary d = Dictionary.getDefaultResourceInstance();	

			IndexWord indexWord = d.lookupIndexWord(VERB, verb);
			if(indexWord!=null) {
				List<Synset> senses = indexWord.getSenses();
				if (!senses.isEmpty()) {
					for (Synset s:  senses) {
						if(s.getLexFileName().equals("verb.cognition")) {
							isCognitionVerb=true;
							break;
						}
					}
				}	
			}
					
			return isCognitionVerb;
			
		} catch (JWNLException e) {

			e.printStackTrace();
			return isCognitionVerb;

		}
    }

}
