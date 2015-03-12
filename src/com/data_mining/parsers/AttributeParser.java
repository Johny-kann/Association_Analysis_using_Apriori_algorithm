package com.data_mining.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.data_mining.constants.Notations;

/**
 * contains the functions used for parsing the attribute file 
 * @author Janakiraman
 *
 */
public class AttributeParser {

	private String mainLine;
	private String attributeName;
	private String attributeType;
	private List<String> attributeValues;
	
	public AttributeParser(String line)
	{
		mainLine = line;
		attributeValues = new ArrayList<String>();
		
		int split;
		if(mainLine.contains(Notations.ATTRIBUTE_SPLITTER1))
		{
		split = mainLine.indexOf(Notations.ATTRIBUTE_SPLITTER1);
		}
		else
		{
		split = mainLine.indexOf(Notations.ATTRIBUTE_SPLITTER2);
		}

		attributeName = mainLine.substring(0, split);
		
		splitter(mainLine.substring(split+1,mainLine.length()),Notations.ATTRIBUTE_DELIMITER);
	}
	
	/**
	 * splits the line depending on the delimiter setting the attribute and values
	 * @param line from file
	 * 
	 * @param delimiter used in the file
	 */
	private void splitter(String str,String delim)
	{
		StringTokenizer stk = new StringTokenizer(str, delim);
		
		if(stk.countTokens()==1)
		{
		
			if(stk.nextToken().equalsIgnoreCase("continuous"))
				attributeType = Notations.CNTS_ATTRB;
		}
		else
		{
		while(stk.hasMoreTokens())
		{
			attributeValues.add(stk.nextToken());
		}
		attributeType = Notations.DISCRETE_ATTRB;
		}
		
	}
	
	/**
	 * @return possible values of the attributes
	 */
	public List<String> getAttributeValues()
	{
		return attributeValues;
	}

	public String getMainLine() {
		return mainLine;
	}

	public void setMainLine(String mainLine) {
		this.mainLine = mainLine;
	}

	/**
	 * @return attribute name
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @param attributeName
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @return
	 */
	public String getAttributeType() {
		return attributeType;
	}

	/**
	 * @param attributeType
	 */
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	
}
