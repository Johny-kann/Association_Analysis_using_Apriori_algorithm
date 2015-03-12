package com.data_mining.model.rules;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.COMM_FAILURE;

import com.data_mining.logic.CommonLogics;
import com.data_mining.logic.ErrorsAndGain;

public class Rules implements Cloneable
{
	private Integer ruleNumber;
	private List<RuleCondition> rules;
	private String category;
	private Double gError;
	private Integer correctClass;
	private Integer wrongClass;
	
	public Rules(int index,String clas)
	{
		ruleNumber = index;
		category = clas;
		rules = new ArrayList<RuleCondition>();
		wrongClass = 0;
		correctClass = 0;
	}
	
	public Rules(int index,String clas,Double gError)
	{
		ruleNumber = index;
		category = clas;
		rules = new ArrayList<RuleCondition>();
		this.gError = new ErrorsAndGain().roundOff(gError, 4);
		wrongClass = 0;
		correctClass = 0;
	}
	
	@Override
	public Rules clone() throws CloneNotSupportedException
	{
		Rules clone = new Rules(ruleNumber, category);
		List<RuleCondition> rule = new ArrayList<RuleCondition>();
		rule.addAll(rules);
		clone.getRules().addAll(rule);
		return clone;
	}
	
	public Integer getRuleNumber() {
		return ruleNumber;
	}
	public void setRuleNumber(Integer ruleNumber) {
		this.ruleNumber = ruleNumber;
	}
	public List<RuleCondition> getRules() {
		return rules;
	}
	
	public void setRules(List<RuleCondition> rules) {
		this.rules = rules;
	}
	
	public void addRule(RuleCondition rule)
	{
		this.rules.add(rule);
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public Double getgError() {
		return gError;
	}

	public void setgError(Double gError) {
		this.gError = new ErrorsAndGain().roundOff(gError, 4);
	}

	public Integer getCorrectClass() {
		return correctClass;
	}

	public void setCorrectClass(Integer correctClass) {
		this.correctClass = correctClass;
	}

	public Integer getWrongClass() {
		return wrongClass;
	}

	public void setWrongClass(Integer wrongClass) {
		this.wrongClass = wrongClass;
	}

	public void addWrongClass(Integer wrongClass) {
		// TODO Auto-generated method stub
		this.wrongClass+=
				wrongClass;
		
	}
	
	public void addCorrectClass(Integer correctClass) {
		// TODO Auto-generated method stub
		this.correctClass+=correctClass;
		
	}
	
	
}
