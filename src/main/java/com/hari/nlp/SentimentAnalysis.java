package com.hari.nlp;

import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class SentimentAnalysis {
	
	 public static void main(String[] args) {
	        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
	        String text = "My name is Hari Prasad. I hate Cananda so much.";

	        CoreDocument coreDocument = new CoreDocument(text);
	        stanfordCoreNLP.annotate(coreDocument);
	        
	        List<CoreSentence> sentences = coreDocument.sentences();
	        
	        for(CoreSentence sentence : sentences) {

	            System.out.println(sentence.sentiment());

	        }
	    }

}
