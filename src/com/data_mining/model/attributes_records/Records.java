package com.data_mining.model.attributes_records;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for containing records
 * @author Janakiraman
 *
 */
public class Records implements Cloneable
{
	private List<String> elements;
	private String classAttribute;
	
	public Records(List<String> elems,String category)
	{
		elements = new ArrayList<String>();
		for (String str:elems)
		{
			elements.add(str);
		}
		classAttribute = category;
	}

	public Records(Records temp)
	{
		classAttribute = temp.classAttribute;
		elements = new ArrayList<String>();
		
		for(String str:temp.getElements())
		{
			elements.add(str);
		}
	}
	
	public Records clone() throws CloneNotSupportedException {
	      
		Records clonedCustomer = (Records) super.clone();
	 
	      // Clone the object referenced objects
	      
	      return clonedCustomer;
	   }
	
	/**
	 * @return list of element
	 * 
	 * s in the record
	 */
	public List<String> getElements() {
		return elements;
	}
	
	/**
	 * @return element in the record
	 */
	public String getElementValueAtIndex(int i) {
		return elements.get(i);
	}

	/**
	 * sets the record
	 * @param list of string
	 */
	public void setElements(List<String> elements) {
		this.elements = elements;
	}

	/**
	 * 
	 * @return category of the record
	 */
	public String getClassAttribute() {
		return classAttribute;
	}
	
	
	/**
	 * Sets the class attribute
	 * @param classAttribute
	 */
	public void setClassAttribute(String classAttribute) {
		this.classAttribute = classAttribute;
	}
	
		
}