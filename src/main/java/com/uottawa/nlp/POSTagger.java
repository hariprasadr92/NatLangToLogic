package com.uottawa.nlp;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class POSTagger {

    public static void main(String[] args) {

        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String text = "But  I  still  can't  understand  why  Inglethorp  was  such  a  fool  as  to  leave  it  there  when  he  had  plenty  of  opportunity  to  destroy  it.";
        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);

        List<CoreLabel> coreLabelList = coreDocument.tokens();

        for (CoreLabel coreLabel : coreLabelList) {
        	if(coreLabel.originalText().equals("it")) {
        		String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                System.out.println(coreLabel.index()+" "+coreLabel.originalText() + " " + coreLabel.endPosition());
        	}
             
        }
        System.out.println(coreLabelList.get(10-1));
        System.out.println(coreLabelList.get(12-1));

    }
    
    public static List<CoreLabel> getPosTags (String text) {
    	
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline(); 

    	CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);

        /**
         * return List<CoreLabel>
         */
        return coreDocument.tokens();         
    	
    }
}
