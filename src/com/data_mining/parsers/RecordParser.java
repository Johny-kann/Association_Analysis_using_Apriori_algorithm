package com.data_mining.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.data_mining.constants.Notations;

/**
 * @author Janakiraman
 *
 */
public class RecordParser {

	private String mainLine;
	private List<String> elements;
	private String category;
	
	public RecordParser(String line)
	{
		mainLine = line;
		
		elements = new ArrayList<String>();
		
	//	int split = mainLine.indexOf(Notations.ATTRIBUTE_SPLITTER);
		splitter(line,Notations.RECORD_DELIMITER);
	}
	
	
	/**
	 * List of elements in the record
	 * @return
	 */
	public List<String> getRecordElements()
	{
		return elements;
	}
	
	/**
	 * 
	 * @return category
	 */
	public String getCategory()
	{
		return category;
	}
	
	/**
	 * Splits the record line from file based on delimiter
	 * @param Record line from file
	 * @param delimiter for records
	 */
	private void splitter(String str,String delim)
	{
		StringTokenizer stk = new StringTokenizer(str, delim);
		
		
		while(stk.hasMoreTokens())
		{
			
			elements.add(stk.nextToken());
		}
		
//		category = elements.get(elements.size()-1);
//		elements.remove(elements.size()-1);
	}
	

	
}
