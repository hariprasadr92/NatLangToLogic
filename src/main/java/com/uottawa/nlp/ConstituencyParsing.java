package com.uottawa.nlp;

import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations.DependencyAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.patterns.dep.DepPatternFactory;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;

import java.util.*;

public class ConstituencyParsing {

  public static void main(String[] args) {
  
    StanfordCoreNLP pipeline = Pipeline.getPipeline();
     //build annotation for a review
    Annotation annotation =
        new Annotation("I am a good boy.");
    // annotate
    pipeline.annotate(annotation);

//    System.out.println("--------------");
//    String text= "It  is  retarded  under  certain  conditions,  none  of  which,  however,  appear  to  have  been  present  in  this  case.";
//    System.out.println(getConstituencyParseTree(text).pennString());
//    for ( Tree subtree: applyTregex("NP !< NP",getConstituencyParseTree(text))) {
//    	subtree.pennPrint();
//    }

 //   System.out.println("--------------");
   
    // get tree
    Tree tree =
        annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0).get(TreeCoreAnnotations.TreeAnnotation.class);
    System.out.println(tree.pennString());
    
    //Set<Dependency<Label, Label, Object>> taggedwords = tree.dependencies();
    
    
    Set<Constituent> treeConstituents = tree.constituents(new LabeledScoredConstituentFactory());
   // Set<Dependency<Label, Label, Object>> dependancies = tree.dependencies();
    System.out.println("*******************************************************");	

//    for(Dependency dep: dependancies) {
//    	
//    	dep.toString();
//    }
    	
    System.out.println("*******************************************************");	
    for (Constituent constituent : treeConstituents) {
      if (constituent.label() != null 
    		  && (constituent.label().toString().equals("VP") || constituent.label().toString().equals("NP"))
    		  ) {
        //System.err.println("found constituent: "+constituent.toString());
        //System.err.println(tree.getLeaves().subList(constituent.start(), constituent.end()+1));
      }
    }
    
    //tregex code 
    String regex = "NP !< NP";
    TregexPattern p = TregexPattern.compile(regex);
    TregexMatcher m = p.matcher(tree);
    
    while (m.find()) {
    	m.getMatch().pennPrint();

    	Tree subtree = m.getMatch();
        Set<Constituent> treeConstituentss = subtree.constituents(new LabeledScoredConstituentFactory());

//    	System.out.println(subtree.getLeaves());
//    	
    	System.out.println(tree.leftCharEdge(subtree));
    	System.out.println(tree.rightCharEdge(subtree));
    	

//        Set<Constituent> treeConstituentss = subtree.constituents(new LabeledScoredConstituentFactory());
//        for (Constituent constituent : treeConstituentss) {
//            if (constituent.label() != null 
//          		  //&& (constituent.label().toString().equals("VP") || constituent.label().toString().equals("NP"))
//          		  ) {
//              //System.err.println("found constituent: "+constituent.toString());
//              System.err.println(tree.getLeaves());
//            }
//          }
        
    	  // System.out.println(m.getMatch().constituents());
    }  
  }
  
  public static List<Tree> applyTregex(String tregexExpr, Tree tree ){
	  
	  	String regex = tregexExpr;
	    TregexPattern p = TregexPattern.compile(tregexExpr);
	    TregexMatcher m = p.matcher(tree);
	    List<Tree> matchedSubtrees = new ArrayList<Tree>();
	    while (m.find()) {
	    	matchedSubtrees.add(m.getMatch());
	    }
  	return matchedSubtrees;
  }
  
  public static Tree getConstituencyParseTree ( String text) {
	  
	  StanfordCoreNLP pipeline = Pipeline.getPipeline();
	  Annotation annotation =
	        new Annotation(text);
	    // annotate
	    pipeline.annotate(annotation);
	    // return  tree
	    return  annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0).get(TreeCoreAnnotations.TreeAnnotation.class);
	   
  }
  
 

}