package com.data_mining.model.rules;

public class RuleCondition
{
	private String name;
	private String value;
	private String condition;
	private Double error;
	private String split;
	private Integer noOfCorrectClass=0;
	
	public RuleCondition(String nam,String val,String con,Double error,String split,int correctClass)
	{
		name = nam;
		value = val;
		condition = con;
		this.error = error;
		this.split = split;
		this.noOfCorrectClass = correctClass;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public Double getError()
	{
		return this.error;
	}
	
	public String getSplit()
	{
		return this.split;
	}

	public Integer getNoOfCorrectClass() {
		return noOfCorrectClass;
	}

	public void setNoOfCorrectClass(Integer noOfCorrectClass) {
		this.noOfCorrectClass = noOfCorrectClass;
	}
			
}