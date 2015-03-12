package com.data_mining.model.association;

import java.util.Set;

public class AssociationRule {

	private Set<String> antecedant;
	private Set<String> consequent;
	private double confidence;
	
	public AssociationRule(Set<String> antecedant,Set<String> cons,double confidence)
	{
		this.antecedant = antecedant;
		this.consequent = cons;
		this.confidence = confidence;
	}

	public Set<String> getAntecedant() {
		return antecedant;
	}

	public Set<String> getConsequent() {
		return consequent;
	}
	
	public double getConfidence()
	{
		return confidence;
	}
	
}
