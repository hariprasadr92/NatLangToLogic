package com.uottawa.nlp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class subject {
	
	private String rootSubj;
	private String compound;
	private String amod;
	private String nmod;
	
	
}
