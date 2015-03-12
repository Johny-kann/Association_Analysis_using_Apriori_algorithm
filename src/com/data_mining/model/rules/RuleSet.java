package com.data_mining.model.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuleSet {

	private List<Rules> rules;
	
	public RuleSet()
	{
		rules = new ArrayList<Rules>();
		
	}
	
	public Integer sizeOfRuleSet()
	{
		return rules.size();
	}
	
	public void addRules(List<Rules> rulesList)
	{
		this.rules.addAll(rulesList);
	}
	
	public void addRules(Rules rule)
	{
		this.rules.add(rule);
	}
	
	public List<Rules> getRulesList()
	{
		return rules;
	}
}
