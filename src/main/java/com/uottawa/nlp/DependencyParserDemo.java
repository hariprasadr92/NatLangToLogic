package com.uottawa.nlp;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.logging.Redwood;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates how to first use the tagger, then use the NN dependency
 * parser. Note that the parser will not work on untagged text.
 *
 */
public class DependencyParserDemo  {

  /** A logger for this class */
  private static final Redwood.RedwoodChannels log = Redwood.channels(DependencyParserDemo.class);

  private DependencyParserDemo() {} // static main method only

  public static void main(String[] args) {
	  
    String modelPath = DependencyParser.DEFAULT_MODEL;
    String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words-distsim.tagger";

    String text = "Sue asked George to respond to her offer.";

    MaxentTagger tagger = new MaxentTagger(taggerPath);
    DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);
    DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));
    for (List<HasWord> sentence : tokenizer) {

      List<TaggedWord> tagged = tagger.tagSentence(sentence);
      GrammaticalStructure gs = parser.predict(tagged);
      
      // Print typed dependencies
      log.info(gs);
      List<TypedDependency> dependencies = gs.typedDependenciesEnhancedPlusPlus();
      
      for (TypedDependency dep: dependencies) {
    	  
    	  System.out.println(dep.toString()+" "+ dep.gov().index()+" "+dep.dep().word().toString()+"---"+dep.reln().toString());
      }
      
    }
  }
  
  public static List<TypedDependency> getDependencies (String text) {
	  
	  List<TypedDependency> dependencies = new ArrayList<TypedDependency>();
	  
//	  String modelPath = DependencyParser.DEFAULT_MODEL;
//	  String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words-distsim.tagger";

	    MaxentTagger tagger = Pipeline.getTagger();
	    DependencyParser parser = Pipeline.getDepParser();
	    DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));
	    
		/*
		 * input should mostly be one lexical sentence. If more than once sentence is
		 * given, then the dependencies will be appended and returned as a single list
		 */
	    for (List<HasWord> sentence : tokenizer) {
	
	      List<TaggedWord> tagged = tagger.tagSentence(sentence);
	      GrammaticalStructure gs = parser.predict(tagged); // main step where depparse takes place	      
	      // Print typed dependencies
	      dependencies.addAll(gs.typedDependenciesEnhancedPlusPlus());	      
	      
	    }
	    return dependencies;
  }

}
