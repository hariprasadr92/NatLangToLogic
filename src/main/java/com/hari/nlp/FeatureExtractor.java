package com.hari.nlp;

import java.util.List;

import org.apache.xerces.parsers.IntegratedParserConfiguration;

import com.uo.nlp4se.assg2.model.PAAResult;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import util.AppConstants;

public class FeatureExtractor {
	
	public static void main(String[] args) {
		
		//calculateF6("He  fears  that  if  he  remains  in  the  room  he  may  have  to  open  it  again,  and  that  Mr.s.  Inglethorp  might  catch  sight  of  the  letter  before  he  could  snatch  it  up.");
	}
	
	
	public static PAAResult calcPrecdingAndSuccNPCount (CoreLabel token,String text) {
		
		Integer tokenBeginPosition = token.beginPosition();
		Integer tokenEndPosition = token.endPosition();
		
		Integer npBefore=0; // f4 counter
		Integer npAfter=0;  //f5 counter
		
		PAAResult result = new PAAResult();
		Tree tree= ConstituencyParsing.getConstituencyParseTree(text);
		List <Tree> atomicNPs = ConstituencyParsing.applyTregex(AppConstants.TREGEX_ATOMIC_NP, tree);
		
			
		for (Tree npTree: atomicNPs) {
			
			
			Integer treeStart = tree.leftCharEdge(npTree);
			Integer treeEnd = tree.rightCharEdge(npTree);
			
			
			if ( treeEnd < tokenBeginPosition ) {
				npBefore++;
			}
			if ( treeStart > tokenEndPosition ) {
				npAfter++;
			}
		}
		
		result.setF4NPrecNP(npBefore);
		result.setF5NSuccNP(npAfter);
		
		return result;
	}
	
	public static boolean calculateF14 ( String text, CoreLabel token ) {
		
		Integer tokenEndPosition = token.endPosition();
		boolean nPEnclosingJJExists = false;
		
		Tree tree= ConstituencyParsing.getConstituencyParseTree(text);
		List <Tree> adjNPs = ConstituencyParsing.applyTregex(AppConstants.NP_COV_JJ, tree);
		
		for (Tree adjNP : adjNPs) {			
			
			Integer treeStart = tree.leftCharEdge(adjNP);			
			
			if ( treeStart > tokenEndPosition ) {
				nPEnclosingJJExists=true;
				break;
			}
		}
		return nPEnclosingJJExists;
	}
	
	public static boolean calculateF17 ( String text, CoreLabel token ) {
		
		Integer tokenEndPosition = token.endPosition();
		boolean adjNPExistsAfterpronounIndex = false;
		
		Tree tree= ConstituencyParsing.getConstituencyParseTree(text);
		List <Tree> adjNPs = ConstituencyParsing.applyTregex(AppConstants.NP_COV_ADJ_NN, tree);
		
		for (Tree adjNP : adjNPs) {			
			
			Integer treeStart = tree.leftCharEdge(adjNP);			
			
			if ( treeStart > tokenEndPosition ) {
				adjNPExistsAfterpronounIndex=true;
				break;
			}
		}
		return adjNPExistsAfterpronounIndex;
	}
	
	public static boolean calculateF6 ( String text, CoreLabel label ) {
		
		Integer tokenBeginPosition = label.beginPosition();
		Boolean prepPhraseExistImmAftertoken = false;
		Tree tree= ConstituencyParsing.getConstituencyParseTree(text);
		List <Tree> pptrees = ConstituencyParsing.applyTregex(AppConstants.PP, tree);
		for (Tree pptree : pptrees) {			
			
			Integer treeEnd = tree.rightCharEdge(pptree);
			
			if ( treeEnd+1 == tokenBeginPosition ) {
				
				prepPhraseExistImmAftertoken=true;
				// debug comments
				// System.out.println(treeEnd+" "+tokenBeginPosition);
				// System.out.println(pptree.pennString());
				break;
			}
		}
		return prepPhraseExistImmAftertoken;
	}
	
	public static String calculateF18 ( String text, CoreLabel label ) {
		
		Integer smallestGovDepDistance = 99999;
		String dependencyType = "NA";
		
		List<TypedDependency> dependencies = DependencyParserDemo.getDependencies(text);
		for (TypedDependency dependency : dependencies) {			

			if(dependency.dep().word().toString().equals(label.originalText())) {
				
				Integer distance =  java.lang.Math.abs(dependency.gov().index() - dependency.dep().index());
				if(distance <= smallestGovDepDistance) {
					smallestGovDepDistance = distance;
					dependencyType = dependency.reln().toString();
				}
				
			}
			
		}
		return dependencyType;
	}
}
