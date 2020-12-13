package com.uottawa.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class Lemmatization {

    public static void main(String[] args) {

        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String text = "The Angular velocities of the satellite should always be lower than 1.5 m/s";
        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);

        List<CoreLabel> coreLabelList = coreDocument.tokens();

        for (CoreLabel coreLabel : coreLabelList) {

            System.out.println(coreLabel.originalText() + "-" + coreLabel.lemma());

        }
    }


}
