package util;

import java.util.Arrays;
import java.util.List;

public class AppConstants {	
	
	 public static final List<String> CORE_NLP_PUNCT_POS_TAGS = Arrays.asList(",",".",":","-RRB-","-LRB-","``","''");
	 public static final List<String> VERB_POS_TAGS = Arrays.asList("VB","VBP","VBZ","VBG","VBD","VBN");
	 public static final List<String> ADJECTIVE_POS_TAGS = Arrays.asList("JJ","JJR","JJS");
	 
	 // Tregex pattern	 
	 public static final String TREGEX_ATOMIC_NP = "NP !< NP";
	 public static final String  NP_COV_ADJ_NN = "NP < (JJ $. /NN.?/ )";
	 public static final String  PP = "PP";
	 public static final String  NP_COV_JJ = "NP < JJ";




}
