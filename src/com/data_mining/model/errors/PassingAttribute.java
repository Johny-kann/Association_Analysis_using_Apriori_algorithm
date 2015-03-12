package com.data_mining.model.errors;

/**
 * The final attribute which comes to the nodes for defining errors and split
 * @author Janakiraman
 *
 */
public class PassingAttribute {

	private String attrName;
	private Integer attrIndex;
	private String splitMode;
	private String attrCondition;
	private Double errorSplit;
	private Double cntnsValue;
	private Double gain;
	private Double gainRatio;
	
	/**
	 * For discrete
	 * @param name
	 * @param index
	 * @param mode
	 * @param cond
	 * @param error
	 * @param gainRatio
	 */
	public PassingAttribute(String name,int index,String mode,String cond,Double error,Double gainRatio)
	{
		attrName = name;
		splitMode = mode;
		attrCondition = cond;
		errorSplit = error;
		attrIndex = index;
		this.gainRatio = gainRatio;
		
	}
	
	/**
	 * for continuous
	 * @param name
	 * @param index
	 * @param mode
	 * @param cond
	 * @param error
	 * @param gainRatio
	 * @param cnts
	 */
	public PassingAttribute(String name,int index,String mode,String cond,Double error,Double gainRatio,Double cnts)
	{
		attrName = name;
		splitMode = mode;
		attrCondition = cond;
		errorSplit = error;
		this.cntnsValue = cnts;
		attrIndex = index;
		this.gainRatio = gainRatio;
	}

	public String getAttrName() {
		return attrName;
	}

	public String getSplitMode() {
		return splitMode;
	}

	public String getAttrCondition() {
		return attrCondition;
	}

	public Double getErrorSplit() {
		return errorSplit;
	}

	public Integer getAttrIndex() {
		return attrIndex;
	}

	public Double getCntnsValue() {
		return cntnsValue;
	}

	public Double getGain() {
		return gain;
	}

	public Double getGainRatio() {
		return gainRatio;
	}
	
	
}
