package com.data_mining.model.attributes_records;

import java.util.List;

/**
 * @author Janakiraman
 * 
 * Model for storing attributes
 *
 */
public class AttributesSpecifications
{
	private String name;
	private String type;
	private List<String> values;
	
	public AttributesSpecifications(String name,String type,List<String> values)
	{
		this.name = name;
		this.type = type;
		this.values = values;
	}

	public String getName() {
		return name;
	}

	
	public String getType() {
		return type;
	}

	public List<String> getValues() {
		return values;
	}

		
}
