package com.uottawa.nlp;

import java.util.ArrayList;
import java.util.List;

import com.uottawa.nlp.model.ConditionClause;
import com.uottawa.nlp.model.ExtractedResult;
import com.uottawa.nlp.model.MainClause;
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


		IndexedWord from1 = new IndexedWord();
		IndexedWord to1 = new IndexedWord();
		
		List<ConditionClause> conditions = new ArrayList<ConditionClause>();
		List<MainClause> actions= new ArrayList<MainClause>();
		List<TypedDependency> mainClauses = new ArrayList<TypedDependency>();
		
		for (TypedDependency dependency : dependencies) {
			
			// checks for nsubj relation
			if((AppConstants.SUBJ_DEP_REL.contains(dependency.reln().toString()))) {
				
				IndexedWord tempGov = dependency.gov();
				boolean isSubClauseSubject=false;
				// checks if nsubj relation is a main clause. Main clause verb wont be dependent
				for (TypedDependency someDependency : dependencies) {
					if(someDependency.dep().equals(tempGov) && (!someDependency.reln().toString().equals("root"))) {
						isSubClauseSubject = true; break;
					}
				}
				if(!isSubClauseSubject) {
					mainClauses.add(dependency);					
				}
			}
		}
		
		for(TypedDependency mainClauseDepcy: mainClauses) {
			
			IndexedWord nsubjDep = mainClauseDepcy.dep();
			IndexedWord nsubjGov = mainClauseDepcy.gov();
					
			MainClause action= new MainClause();
			
			//extract the subject 
			if(nsubjDep!=null) {
									
				List <Tree> subjects = ConstituencyParsing.applyTregex( "NP [ !<< NP ]  << "+nsubjDep.word()  , ctree);
				if(!subjects.isEmpty()) {
					result.setSubject(subjects.get(0).spanString());
					action.setSubject(subjects.get(0).spanString());
				}else {
					result.setSubject(nsubjDep.word());
					action.setSubject(nsubjDep.word());
				}					
			}
			
			
	
			/*
			 * extract the comparator/relation's individual values of action /rule/transition
			 * extracting transition's from to and object
			 * extracting conditions/ conditional clauses
			 */			
			result.setCondVerb(nsubjGov.word());
			action.setVerb(nsubjGov.word());
			
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
						action.setModifier(dependency.dep().word());
				}
				if((dependency.reln().toString().contains("conj")) &&
						((dependency.gov().equals(nsubjGov)))) {
						action.setConj(dependency.reln().toString().substring(5));
				}				
				
				// extracting transition's object
				if(dependency.gov().equals(nsubjGov) && dependency.reln().toString().equals("obj") ) {
					result.setObj1(dependency.dep().word());
					action.setObj(dependency.dep().word());
				}
				
				// extracting transition's from and to values
				if(dependency.gov().equals(nsubjGov) && 
						((dependency.reln().toString().equals("obl:from")) || (dependency.reln().toString().equals("advcl:from")))){
					from1 = dependency.dep();
					result.setFrom1(from1.word());
					action.setFrom(from1.word());
					for (TypedDependency typedDep : dependencies) {
						if((typedDep.gov().equals(nsubjGov)|| typedDep.gov().equals(from1)) && 
								((typedDep.reln().toString().equals("obl:to")) || (typedDep.reln().toString().equals("advcl:to")))){
							to1 = typedDep.dep();
							result.setTo1(to1.word());
							action.setTo(to1.word());
						}						
					}
				}
				
				
				
				//extracting the conditional clauses
				if(dependency.gov().equals(nsubjGov) && 
						(AppConstants.COND_CLAUSE_DEP_REL.contains(dependency.reln().toString())) ){		
					
					ConditionClause condCl = new ConditionClause();
					
					IndexedWord depClauseVerb = dependency.dep();
					
					// sets conditional clause's verb
					result.setCondClause1Verb(dependency.gov().word());
					condCl.setVerb(dependency.dep().word());
					
					for (TypedDependency typDep : dependencies) {
						
						// sets conditional clause's modifier
						if((typDep.reln().toString().equals("advmod")) &&
								((typDep.gov().equals(depClauseVerb)))) {
								if(result.getCondClause1Modifier()!=null) {
									result.setCondClause1Modifier(result.getCondClause1Modifier()+" "+typDep.dep().word());
									condCl.setModifier(result.getCondClause1Modifier()+" "+typDep.dep().word());
								} else {
									result.setCondClause1Modifier(typDep.dep().word());
									condCl.setModifier(typDep.dep().word());
								}
								
						}
						
						// sets conditional clause's subject
						if((AppConstants.SUBJ_DEP_REL.contains(typDep.reln().toString())) &&
								((typDep.gov().equals(depClauseVerb)))) {
								result.setCondClause1Subj(typDep.dep().word());
								condCl.setSubj(typDep.dep().word());
						}
						
						// sets conditional clause's conjunction relation
						if((typDep.reln().toString().contains("conj")) &&
								((typDep.gov().equals(depClauseVerb)))) {
								result.setCondClause1Conj(typDep.reln().toString().substring(5));
								condCl.setConj(typDep.reln().toString().substring(5));
						}
						// sets conditional clause's object
						if((typDep.reln().toString().contains("obj")) &&
								((typDep.gov().equals(depClauseVerb)))) {
								result.setCondClause1Obj(typDep.dep().word());
								condCl.setObj(typDep.dep().word());
						}					
					}
				conditions.add(condCl);					
				}				
			}
			
		
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
					action.setValue(t.spanString());
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
			action.setUnit(unit+unit_second_part+unit_third_part);
			
			actions.add(action);
		}
		
		result.setActions(actions);
		result.setConditions(conditions);
		return result;
	}	
}
