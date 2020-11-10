package com.hari.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class NamedEntityRecognizer {

    public static void main(String[] args) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String text = "My name is Hari Prasad and I have a friend Robert who has ten friends.";

        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);

        List<CoreLabel> coreLabelList = coreDocument.tokens();

        for(CoreLabel coreLabel : coreLabelList) {

            String ner = coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);
            System.out.println(coreLabel.originalText() + "-" + ner);

        }
    }



}
