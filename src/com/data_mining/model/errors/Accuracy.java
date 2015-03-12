package com.data_mining.model.errors;

/**
 * @author Janakiraman
 * 
 * Model for storing accuracy
 *
 */
public class Accuracy {

	private Integer wrongClass;
	private Integer rightClass;
	private Double accuracy;
	
	public Accuracy()
	{
		wrongClass = 0;
		rightClass = 0;
	}

	public Integer getWrongClass() {
		return wrongClass;
	}

	public void addWrongClass(Integer wrongClass) {
		this.wrongClass += wrongClass;
	}

	public Integer getRightClass() {
		return rightClass;
	}

	public void addRightClass(Integer rightClass) {
		this.rightClass += rightClass;
	}

	public Double getAccuracy() {
		return (double)rightClass/(wrongClass+rightClass);
	}
	
}
