package com.hari.nlp;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.uo.nlp4se.assg2.model.LabelTextPair;
import com.uo.nlp4se.assg2.model.PAAResult;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import util.AppConstants;
import util.SmartCsvReader;
import util.SmartCsvWriter;

public class Starter {

	public static void main(String[] args) {
		
		String filepath = "src/main/resources/assg2.csv";

		List<LabelTextPair> data = readCSVintoList(filepath);
		
		List <PAAResult> finalList = new ArrayList<PAAResult>(Collections.EMPTY_LIST);
				
		Integer counter = 0;
		
		for (LabelTextPair datum: data ) {
			counter++;
			System.out.println(counter);
			
			finalList.addAll(TokenizePOSAndAnalyze (datum));
			//finalList.addAll(test (datum));

			if (counter==20) {break;}
		}
		
		//generating the csv		
		writeListtoCSV(finalList);		
	}
	
	public static List<LabelTextPair> readCSVintoList (String filepath) {
		
		SmartCsvReader smartCsvReader = new SmartCsvReader();
		List<LabelTextPair> data = new ArrayList<LabelTextPair>();
		try {
			data= smartCsvReader.readFromCsv(filepath);
			
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static void writeListtoCSV (List<PAAResult> results) {
		
		SmartCsvWriter smartCsvWriter = new SmartCsvWriter();
		try {
			smartCsvWriter.writeToFile(results);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<PAAResult> test(LabelTextPair labelTextPair) {
		
		List<CoreLabel> coreLabelList= POSTagger.getPosTags(labelTextPair.getText());
		List<String> anaphoraWords = Arrays.asList("it","its","it's");
		List<PAAResult> rowstoBeInserted = new ArrayList<PAAResult>(Collections.EMPTY_LIST);

		for (CoreLabel token: coreLabelList) {
			PAAResult result = new PAAResult();

			if(anaphoraWords.contains(token.originalText().toLowerCase())){
			
				result.setF6IsFollowsPP(FeatureExtractor.calculateF6(labelTextPair.getText(), token));
			}
			rowstoBeInserted.add(result);					

		}

		return rowstoBeInserted;
	}
	
	public static List<PAAResult> TokenizePOSAndAnalyze(LabelTextPair labelTextPair) {
		
		try {
			List<CoreLabel> coreLabelList= POSTagger.getPosTags(labelTextPair.getText());
			List<String> anaphoraWords = Arrays.asList("it","its","it's");
			
			boolean isRowCreated = false;
			Integer punctCount=0;
			
			List<PAAResult> rowstoBeInserted = new ArrayList<PAAResult>(Collections.EMPTY_LIST);
					
			for (CoreLabel token: coreLabelList) {
				
	            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

	            // for calculating f3
				if(AppConstants.CORE_NLP_PUNCT_POS_TAGS.contains(pos)) {
					punctCount++;
				}
				
				// checking for presence of "if"
				if(anaphoraWords.contains(token.originalText().toLowerCase())){					
					
					isRowCreated= true;
					PAAResult result = new PAAResult();
					result.setF1Position(token.index());  // Setting f1
					result.setF2NTokens(coreLabelList.size()); // setting f2
					
					// calculating and setting f4 and f5
					PAAResult temp = FeatureExtractor.calcPrecdingAndSuccNPCount(token,labelTextPair.getText());
					result.setF4NPrecNP(temp.getF4NPrecNP());
					result.setF5NSuccNP(temp.getF5NSuccNP());
					
					//f6 
					result.setF6IsFollowsPP(FeatureExtractor.calculateF6(labelTextPair.getText(), token));
					
					//f7 
					
					result.setF7POSNeighboursB4(
							(token.index()>4)?coreLabelList.get(token.index()-5).get(CoreAnnotations.PartOfSpeechAnnotation.class):"ABS");
					result.setF7POSNeighboursB3(
							(token.index()>3)?coreLabelList.get(token.index()-4).get(CoreAnnotations.PartOfSpeechAnnotation.class):"ABS");

					result.setF7POSNeighboursB2(
							(token.index()>2)?coreLabelList.get(token.index()-3).get(CoreAnnotations.PartOfSpeechAnnotation.class):"ABS");
					result.setF7POSNeighboursB1(
							(token.index()>2)?coreLabelList.get(token.index()-2).get(CoreAnnotations.PartOfSpeechAnnotation.class):"ABS");
					
					result.setF7POSNeighboursA1(
							((coreLabelList.size()-token.index())>1)?coreLabelList.get(token.index()).get(CoreAnnotations.PartOfSpeechAnnotation.class):"ABS");
					result.setF7POSNeighboursA2(
							((coreLabelList.size()-token.index())>2)?coreLabelList.get(token.index()+1).get(CoreAnnotations.PartOfSpeechAnnotation.class):"ABS");
					result.setF7POSNeighboursA3(
							((coreLabelList.size()-token.index())>3)?coreLabelList.get(token.index()+2).get(CoreAnnotations.PartOfSpeechAnnotation.class):"ABS");
					result.setF7POSNeighboursA4(
							((coreLabelList.size()-token.index())>4)?coreLabelList.get(token.index()+3).get(CoreAnnotations.PartOfSpeechAnnotation.class):"ABS");
					
					//Setting the previous and next token of 'it' 
					String previousPOS =null;
					String nextPOS = null;
					
					if(token.index()!=1) {
						previousPOS = coreLabelList.get(token.index()-2).get(CoreAnnotations.PartOfSpeechAnnotation.class);
					}
					
					if(token.index()!=coreLabelList.size()) {
						nextPOS = coreLabelList.get(token.index()).get(CoreAnnotations.PartOfSpeechAnnotation.class);
					}
					
					//f8
					if(nextPOS.equalsIgnoreCase("VBG") && (nextPOS != null)) {
						result.setF8IsFollByGerund(true);
					} else {
						result.setF8IsFollByGerund(false);
					}
					//f9
					if(nextPOS.equalsIgnoreCase("IN") && (nextPOS != null)) {
						result.setF9IsFollByPrep(true);
					} else {
						result.setF9IsFollByPrep(false);
					}
					//f11
					if(AppConstants.VERB_POS_TAGS.contains(previousPOS) && (previousPOS != null)) {
						result.setF11IsPrecByVerb(true);
					} else {
						result.setF11IsPrecByVerb(false);
					}
					//f12
					if(AppConstants.VERB_POS_TAGS.contains(nextPOS) && (nextPOS != null)) {
						result.setF12IsFollByVerb(true);
					} else {
						result.setF12IsFollByVerb(false);
					}
					//f13
					if(AppConstants.ADJECTIVE_POS_TAGS.contains(nextPOS) && (nextPOS != null)) {
						result.setF13IsFollByAdj(true);
					} else {
						result.setF13IsFollByAdj(false);
					}
					
					//f15 
					result.setF14IsFollByNPAdj(FeatureExtractor.calculateF14(labelTextPair.getText(), token));

					//f17
					result.setF17IsFollByAdjNP(FeatureExtractor.calculateF17(labelTextPair.getText(), token));
					
					// after the special token occurrence 
					// before the special token occurrence
					
					//Flags and counters for f15 f10 f16 f19 f20 
					
					Integer f10FollAdjCounter=0; // counter for f10
					
					//f15
					boolean isF15InfinitiveWordFound = false;
					Integer f15Value=0;
					
					// f16
					boolean isF16Prepositionfound = false;	
					Integer f16Value=0;
					
					//f18
					result.setF18DepRel(FeatureExtractor.calculateF18(labelTextPair.getText(), token));

					
					//f19 and f20
					boolean isImmediatelyFollowingVerbFound = false;	// flag for f19 and f20
					boolean f19Value = false;
					boolean f20Value = false;
					
					for (CoreLabel tkn: coreLabelList) {
						
						//f10
						if (AppConstants.ADJECTIVE_POS_TAGS.contains(tkn.get(CoreAnnotations.PartOfSpeechAnnotation.class)) && (tkn.index()> token.index())) {							
							f10FollAdjCounter+=1;
						}
						
						// f15- checks if infinitive verb and occurs after "if" (token)
						if (tkn.originalText()=="to" 
								&& (tkn.index()> token.index())
								&& isF15InfinitiveWordFound == false) {	
							
							if(coreLabelList.get(tkn.index()+2).get(CoreAnnotations.PartOfSpeechAnnotation.class).equals("VB")) {
								f15Value = tkn.index()-token.index()-1;
								isF15InfinitiveWordFound=true;
							}
							
						}
						
						// f16 checks if infinitive verb and occurs after "if" (token)
						if (tkn.get(CoreAnnotations.PartOfSpeechAnnotation.class).equals("VB") 
								&& (tkn.index()> token.index())
								&& isF16Prepositionfound==false){		
							
							//sets f16
							f16Value=tkn.index()-token.index(); // the vb token is inlusive here.
							isF16Prepositionfound=true;
						}				
						
						// f19 and f20
						if (AppConstants.VERB_POS_TAGS.contains(tkn.get(CoreAnnotations.PartOfSpeechAnnotation.class)) 
								&& (tkn.index()> token.index())
								&& (isImmediatelyFollowingVerbFound==false)){
							
							isImmediatelyFollowingVerbFound=true;
							f19Value = WordnetUtil.checkIfWeatherVerb(tkn.originalText());
							f20Value = WordnetUtil.checkIfCognitionVerb(tkn.originalText());
						}
					}
					
					// sets counters and values to result object after the iteration is completed.
					result.setF10NFollAdj(f10FollAdjCounter);
					result.setF15NTkPrecInfVerb(f15Value);
					result.setF16NtkBtwPrep(f16Value);
					result.setF19IsFollByWeathAdj(f19Value);
					result.setF20IsFollByCognitiveVerbs(f20Value);					
					
					//append the result analysis object for this instance of 'if' into the list for the entire sentence
					rowstoBeInserted.add(result);					
				}				
			}
			
			if(isRowCreated) {
				
				for (PAAResult result : rowstoBeInserted) {					
					result.setLabel(labelTextPair.getLabel());
					result.setText(labelTextPair.getText());
					result.setF3NPunc(punctCount);  // setting f3
				}
			}
			
			return rowstoBeInserted;	

			
		}catch (Exception e) {
			
			e.printStackTrace();
			return null;

		}
		
	}
	
	

}
