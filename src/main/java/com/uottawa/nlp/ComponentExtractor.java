package com.uottawa.nlp;

import java.util.ArrayList;
import java.util.List;

import com.uottawa.nlp.model.ConditionClause;
import com.uottawa.nlp.model.ExtractedResult;
import com.uottawa.nlp.model.MainClause;
import com.uottawa.nlp.model.Subject;
import com.uottawa.nlp.util.AppConstants;
import com.uottawa.nlp.util.Util;


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
			
			result.setCondVerb(nsubjGov.word());
			action.setVerb(nsubjGov.word());
			
			/**
			 * 
			//extract the subject using tregex
			if(nsubjDep!=null) {
									
				List <Tree> subjects = ConstituencyParsing.applyTregex( "NP [ !<< NP ]  << "+nsubjDep.word()  , ctree);
				if(!subjects.isEmpty()) {
					result.setSubject(subjects.get(0).spanString());
					action.setSubject(subjects.get(0).spanString());
				}else {
					result.setSubject(nsubjDep.word());
					action.setSubject(nsubjDep.word());
				}					
			} **/
			Subject subject= new Subject();			
			subject.setRootSubj(nsubjDep.word());
			
			for (TypedDependency dependency : dependencies) {
				
				// extract subject component for main clause
				
				
				if( (dependency.gov().equals(nsubjDep)) && 
						(dependency.reln().toString().equals("amod"))) {
					if(!Util.isNullOrEmpty(subject.getAmod())) {
						subject.setAmod(subject.getAmod()+" "+dependency.dep().word());
					}else {
						subject.setAmod(dependency.dep().word());
					}
				}
				if( (dependency.gov().equals(nsubjDep)) && 
						(dependency.reln().toString().equals("compound"))) {
					if(!Util.isNullOrEmpty(subject.getCompound())) {
						subject.setCompound(subject.getCompound()+" "+dependency.dep().word());
					}else {
						subject.setCompound(dependency.dep().word());
					}
				}
				if( (dependency.gov().equals(nsubjDep)) && 
						(dependency.reln().toString().contains("nmod"))) {
					if(!Util.isNullOrEmpty(subject.getNmod())) {
						subject.setNmod(subject.getNmod()+" "+dependency.dep().word());
					}else {
						subject.setNmod(dependency.dep().word());
					}
				}
				if( ((dependency.gov().equals(nsubjDep))) && 
						(dependency.reln().toString().contains("xsubj"))) {
					if(!Util.isNullOrEmpty(subject.getControlledSubject())) {
						subject.setControlledSubject(subject.getControlledSubject()+" "+dependency.dep().word());
					}else {
						subject.setControlledSubject(dependency.dep().word());
					}
				}				
				
				//extract the comparator/relation's individual values of action /rule/transition				
				
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
					
					// extracts conditional clause's verb
					result.setCondClause1Verb(dependency.gov().word());
					condCl.setVerb(dependency.dep().word());
					
					for (TypedDependency typDep : dependencies) {
						
						// sets conditional clause's modifier
						if((typDep.reln().toString().equals("advmod")) &&
								((typDep.gov().equals(depClauseVerb)))) {
								if(!Util.isNullOrEmpty(condCl.getModifier())) {
									result.setCondClause1Modifier(result.getCondClause1Modifier()+" "+typDep.dep().word());
									condCl.setModifier(condCl.getModifier()+" "+typDep.dep().word());
								} else {
									result.setCondClause1Modifier(typDep.dep().word());
									condCl.setModifier(typDep.dep().word());
								}								
						}
						
						// find and sets conditional clause's subject

						if((AppConstants.SUBJ_DEP_REL.contains(typDep.reln().toString())) &&
								((typDep.gov().equals(depClauseVerb)))) {
							
							IndexedWord nsubjDepCl = typDep.dep();
							IndexedWord nsubjGovCl = typDep.gov();
							
							Subject condClauseSubj = new Subject();
							condClauseSubj.setRootSubj(nsubjDepCl.word());
							for (TypedDependency depp: dependencies) {
								if( ((depp.gov().equals(nsubjDepCl))) && 
										(depp.reln().toString().equals("amod"))) {
									if(!Util.isNullOrEmpty(condClauseSubj.getAmod())) {
										condClauseSubj.setAmod(condClauseSubj.getAmod()+" "+depp.dep().word());
									}else {
										condClauseSubj.setAmod(depp.dep().word());
									}
								}
								if( ((depp.gov().equals(nsubjDepCl))) && 
										(depp.reln().toString().equals("compound"))) {
									if(!Util.isNullOrEmpty(condClauseSubj.getCompound())) {
										condClauseSubj.setCompound(condClauseSubj.getCompound()+" "+depp.dep().word());
									}else {
										condClauseSubj.setCompound(depp.dep().word());
									}
								}
							
								if( ((depp.gov().equals(nsubjDepCl))) && 
										(depp.reln().toString().contains("nmod"))) {
									if(!Util.isNullOrEmpty(condClauseSubj.getNmod())) {
										condClauseSubj.setNmod(condClauseSubj.getNmod()+" "+depp.dep().word());
									}else {
										condClauseSubj.setNmod(depp.dep().word());
									}
								}
								if( ((depp.gov().equals(nsubjDepCl))) && 
										(depp.reln().toString().contains("xsubj"))) {
									if(!Util.isNullOrEmpty(condClauseSubj.getControlledSubject())) {
										condClauseSubj.setControlledSubject(condClauseSubj.getControlledSubject()+" "+depp.dep().word());
									}else {
										condClauseSubj.setControlledSubject(depp.dep().word());
									}
								}
							}
							result.setCondClause1Subj(typDep.dep().word());
							condCl.setSubj(condClauseSubj.toString());
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
			action.setSubject(subject.toString());	
		
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
