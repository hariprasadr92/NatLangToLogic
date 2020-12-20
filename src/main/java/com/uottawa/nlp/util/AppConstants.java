package com.uottawa.nlp.util;

import java.util.Arrays;
import java.util.List;

public class AppConstants {	
	
	 public static final List<String> CORE_NLP_PUNCT_POS_TAGS = Arrays.asList(",",".",":","-RRB-","-LRB-","``","''");
	 public static final List<String> VERB_POS_TAGS = Arrays.asList("VB","VBP","VBZ","VBG","VBD","VBN");
	 public static final List<String> ADJECTIVE_POS_TAGS = Arrays.asList("JJ","JJR","JJS");
	 public static final List<String> SUBJ_DEP_REL = Arrays.asList("nsubj","nsubj:pass");
	 public static final List<String> COND_CLAUSE_DEP_REL = Arrays.asList("advcl","advcl:in","advcl:if");
	 public static final List<String> COMPARED_OBJ_OBL = Arrays.asList("obl","obl:than","obl:equal");

	 // Tregex patterns	 
	 public static final String TREGEX_ATOMIC_NP = "NP !< NP";
	 public static final String  NP_COV_ADJ_NN = "NP < (JJ $. /NN.?/ )";
	 public static final String  PP = "PP";
	 public static final String  NP_COV_JJ = "NP < JJ";
	 public static final String  SUBJECT = "NP  [ !<< NP ]  <<";
	 
	 
}
