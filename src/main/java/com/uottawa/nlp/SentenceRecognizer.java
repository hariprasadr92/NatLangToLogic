package com.uottawa.nlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Sentence;

import java.util.List;

public class SentenceRecognizer {

    public static void main(String[] args) {

        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        String text = "I  couldn't  rightly  say,  sir;  it  was  shut  but  I  couldn't  say  whether  it  was  bolted  or  not.";
        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        System.out.println(coreDocument);

        List<CoreSentence> sentences = coreDocument.sentences();
        for (CoreSentence sentence: sentences){
        	
            System.out.println(sentence);

        }
    }

}
