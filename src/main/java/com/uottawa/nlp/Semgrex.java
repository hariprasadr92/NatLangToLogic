package com.uottawa.nlp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.semgraph.semgrex.SemgrexMatcher;
import edu.stanford.nlp.semgraph.semgrex.SemgrexPattern;
import edu.stanford.nlp.simple.Sentence;

public class Semgrex {

	  public static void main(String[] args) {

	  	StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

	  	String text = "The angular velocity of the satellite should always be lower than 1.5 m/s.";
        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);

        List<CoreSentence> sentences = coreDocument.sentences(); 
        CoreSentence sentence= coreDocument.sentences().get(0);
        
        SemanticGraph depgraph = sentence.dependencyParse();
        System.out.println("Example: dependency parse");
        System.out.println(depgraph);
        System.out.println();
        
        //System.out.println(depgraph.edgeCount());
        
        
        List<SemanticGraphEdge> edges= depgraph.findAllRelns("nsubj");
        for(SemanticGraphEdge edge: edges){
        	
        	edge.getRelation();
        }
        
        String s = "{} > nummod {lemma:/1.5/}";
	  	SemgrexPattern p = SemgrexPattern.compile(s);
	  	SemgrexMatcher m = p.matcher(depgraph);
	  	
	  	while (m.find()) {
	  	   System.out.println(m.getMatch().word());
	  	}        
	  }
	  public static SemanticGraph getDependanycGraph (CoreSentence sentence) {
		  
		  return  sentence.dependencyParse();
	  }
	  
	  public static List<IndexedWord> applySemgrex ( String pattern,SemanticGraph graph) {
		  
		  List<IndexedWord> matchedwords = new ArrayList<IndexedWord>(Collections.EMPTY_LIST);
		  SemgrexPattern p = SemgrexPattern.compile(pattern);
	  	  SemgrexMatcher m = p.matcher(graph);
	  	
		  while (m.find()) {
			  matchedwords.add(m.getMatch());
		  	}
		  return matchedwords;
	  }
}
