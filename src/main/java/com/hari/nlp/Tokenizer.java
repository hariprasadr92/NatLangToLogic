package com.hari.nlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class Tokenizer {

	
    public static void main(String[] args) {

        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        String text = "I am good.You are good.";

        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);

        List<CoreLabel> coreLabelList= coreDocument.tokens();
        System.out.println(coreLabelList);

        for (CoreLabel coreLabel: coreLabelList) {
            System.out.println(coreLabel.beginPosition()+" "+ coreLabel.endPosition());
        }

    }
    
    public static List<CoreLabel> getTokens(String text) {
    	
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline(); 

    	CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);

        /**
         * return List<CoreLabel>
         */
        return coreDocument.tokens();         
    	
    }
}
