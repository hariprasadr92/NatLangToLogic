package com.uottawa.nlp;

import java.util.List;

import com.uottawa.nlp.model.ExtractedResult;
import com.uottawa.nlp.util.AppConstants;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;

public class ComponentExtractor {
	
	public static void main(String[] args) {
		String text= "Response to roll step commands shall not exceed 10% overshoot in calm air";
		//extractComponents(text);
	}
	
	public static ExtractedResult extractComponents (String text) {
		
		System.out.println(text);
	  	
		StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        

        List<CoreSentence> sentences = coreDocument.sentences(); 
        CoreSentence sentence= coreDocument.sentences().get(0);
        List<CoreLabel> coreLabelList = coreDocument.tokens();

        SemanticGraph dependencyGraph= Semgrex.getDependanycGraph(sentence);
        
        Tree ctree= ConstituencyParsing.getConstituencyParseTree(text);
		List<TypedDependency> dependencies = DependencyParserDemo.getDependencies(text);
		
		//Extracting the nsubj dep relation
		ExtractedResult result = new ExtractedResult();
		String nsubj_dep="";
		IndexedWord nsubjGov = new IndexedWord();
		IndexedWord nsubjDep = new IndexedWord();

		
		for (TypedDependency dependency : dependencies) {
		
			if((AppConstants.SUBJ_DEP_REL.contains(dependency.reln().toString()))) {
				
				IndexedWord tempGov = dependency.gov();
				boolean isSubClauseSubject=false;
				for (TypedDependency someDependency : dependencies) {
					if(someDependency.dep().equals(tempGov) && (!someDependency.reln().toString().equals("root"))) {
						isSubClauseSubject = true; break;
					}
				}
				if(!isSubClauseSubject) {
					nsubj_dep = dependency.dep().word();
					nsubjGov = dependency.gov();
					nsubjDep = dependency.dep();
				}
			}
		}
		//extract the subject 
		if(nsubj_dep!="") {
			List <Tree> subjects = ConstituencyParsing.applyTregex( "NP [ !<< NP ]  << "+nsubj_dep  , ctree);
			if(!subjects.isEmpty()) {
				result.setSubject(subjects.get(0).spanString());
			}else {
				result.setSubject(nsubj_dep);
			}
				
		}
		
		IndexedWord from1 = new IndexedWord();
		IndexedWord to1 = new IndexedWord();

		// extract the comparator/relation's individual values
		if(true/*AppConstants.ADJECTIVE_POS_TAGS.contains(nsubjGov.tag())*/) {
			
			result.setCondVerb(nsubjGov.word());
			for (TypedDependency dependency : dependencies) {
				
				if((dependency.reln().toString().equals("cop")) &&
						((dependency.gov().equals(nsubjGov)))) {
						result.setCondTense(dependency.dep().word());
				}
				if((dependency.reln().toString().equals("aux")) &&
						((dependency.gov().equals(nsubjGov)))) {
						result.setCondAux(dependency.dep().word());
				}
				if((dependency.reln().toString().equals("advmod")) &&
						((dependency.gov().equals(nsubjGov)))) {
						result.setCondModifier(dependency.dep().word());
				}
				if(dependency.gov().equals(nsubjGov) && dependency.reln().toString().equals("obj") ) {
					result.setObj1(dependency.dep().word());
				}
				if(dependency.gov().equals(nsubjGov) && 
						((dependency.reln().toString().equals("obl:from")) || (dependency.reln().toString().equals("advcl:from")))){
					from1 = dependency.dep();
					result.setFrom1(from1.word());
					for (TypedDependency typedDep : dependencies) {
						if((typedDep.gov().equals(nsubjGov)|| typedDep.gov().equals(from1)) && 
								((typedDep.reln().toString().equals("obl:to")) || (typedDep.reln().toString().equals("advcl:to")))){
							to1 = typedDep.dep();
							result.setTo1(to1.word());
						}						
					}
				}
			}
		}
		
		
//		if(AppConstants.VERB_POS_TAGS.contains(nsubjGov.tag())) {
//			result.setCondVerb(nsubjGov.word());
//			for (TypedDependency dependency : dependencies) {
//				
//				if((dependency.reln().toString().equals("cop")) &&
//						((dependency.gov().equals(nsubjGov)))) {
//						result.setCondTense(dependency.dep().word());
//				}
//				if((dependency.reln().toString().equals("aux")) &&
//						((dependency.gov().equals(nsubjGov)))) {
//						result.setCondAux(dependency.dep().word());
//				}
//				if((dependency.reln().toString().equals("advmod")) &&
//						((dependency.gov().equals(nsubjGov)))) {
//						result.setCondModifier(dependency.dep().word());
//				}
//				
//			}
//		}
		
		
		// extract the comparator using constituency parsing
		List <Tree> comparators = ConstituencyParsing.applyTregex( "QP  >>  (NP !<< NP ) >> (VP !<<VP)", ctree);
		for ( Tree t: comparators) {
			result.setRelation(t.spanString());
			
		}
		
		// extract the value 
		
		List <Tree> values = ConstituencyParsing.applyTregex( "CD >> ( VP !<< VP)", ctree);
		
		if(!values.isEmpty()) {
			for ( Tree t: values) {
				result.setValue(t.spanString());			
			}
		}
		
		
		// extract the unit 
		
		String unit = "";
		String unit_second_part="";
		String unit_third_part="";
		
		if(!values.isEmpty()) {
			//List<IndexedWord> unitmatches = Semgrex.applySemgrex("{} >nummod{lemma:/"+result.getValue()+"/} ", dependencyGraph);
			
			
			for (TypedDependency d : dependencies) {
				

				if(d.reln().toString().equals("nummod") &&
						((d.dep().word()).equals(result.getValue()))) {				
					unit= d.gov().word();				
				}						
			}
			for (TypedDependency d : dependencies) {
				if(unit.length()>0 && d.reln().toString().equals("nmod") &&
						((d.gov().word()).equals(unit))) {
					unit_third_part=d.dep().word();				
				}
			}
			for (TypedDependency d : dependencies) {
				if(unit.length()>0 && unit_third_part.length()>0 && 
						d.reln().toString().equals("dep") &&
						((d.gov().word()).equals(unit_third_part))) {
					unit_second_part=d.dep().word();				
				}	
			}
			
		}		
		result.setUnit(unit+unit_second_part+unit_third_part);	
		
		// TRANSITION 
		// extract the from and to of the transition 
		
		
		
		return result;
	}	
}
