package com.hari.nlp;

import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.util.Properties;

public class Pipeline {

    private static StanfordCoreNLP stanfordCoreNLP;
    private static Properties properties;
    private static String propertiesName = "tokenize,ssplit,pos,parse";
    //"tokenize,ssplit,pos,lemma, ner,parse, sentiment";

    
    // for dependency parsing 
    private static String modelPath = DependencyParser.DEFAULT_MODEL;
    private static String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words-distsim.tagger";

    private static    MaxentTagger tagger = new MaxentTagger(taggerPath);
    private static    DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);
    
    private Pipeline() {

    }

    static {
        properties = new Properties();
        properties.setProperty("annotators", propertiesName);
    }

    public static StanfordCoreNLP getPipeline() {

        if(stanfordCoreNLP == null ) {
            stanfordCoreNLP = new StanfordCoreNLP(properties);
        }

        return stanfordCoreNLP;
    }
    public static MaxentTagger getTagger() {

        if(tagger == null ) {
        	tagger = new MaxentTagger(taggerPath);
        }

        return tagger;
    }
    public static DependencyParser getDepParser() {

        if(parser == null ) {
        	parser = DependencyParser.loadFromModelFile(modelPath);
        }

        return parser;
    }

}
