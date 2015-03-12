package com.data_mining.logic;

import java.util.List;

import com.data_mining.file_readers.TextFileReader;
import com.data_mining.model.association.TransactionTable;
import com.data_mining.model.attributes_records.DataTable;
import com.data_mining.parsers.AttributeParser;
import com.data_mining.parsers.RecordParser;

/**
 * @author Janakiraman
 * Class with methods to parse the attributes and Records and load it in the models
 *
 */
public class AttributeAndRecordLoaders {

	/**
	 * Loads the text file into model Attributes and class object
	 * @param Attributes and class object
	 * @param attribute file location
	 * @param record file location
	 */
	public static void loadAttributeFromFile(DataTable temp,String attributeFile,String recordFile)
	{
		TextFileReader tf = new TextFileReader(attributeFile);
		
		AttributeParser attribParser;
		
		List<String> attributes = tf.getOutput();
		tf.closeStream();
		
		//Setting attributes
		for(int i=0;i<attributes.size();i++)
		{
			attribParser = new AttributeParser(attributes.get(i));
			
//			if(i==attributes.size()-1)
//			{
//				temp.setClassName(attribParser.getAttributeName());
//				temp.setClassType(attribParser.getAttributeType());
//			}
							
				temp.addAttribute(
						attribParser.getAttributeName(), 
						attribParser.getAttributeType(),
						attribParser.getAttributeValues()
						);
			
		}
		loadRecordsFromFile(temp, recordFile);
		
		
	}
	
	public static void loadAttributeFromFile(TransactionTable temp,String attributeFile,String recordFile)
	{
		TextFileReader tf = new TextFileReader(attributeFile);
		
		AttributeParser attribParser;
		
		List<String> attributes = tf.getOutput();
		tf.closeStream();
		
		//Setting attributes
		for(int i=0;i<attributes.size();i++)
		{
			attribParser = new AttributeParser(attributes.get(i));
			
//			if(i==attributes.size()-1)
//			{
//				temp.setClassName(attribParser.getAttributeName());
//				temp.setClassType(attribParser.getAttributeType());
//			}
						
			temp.addHeader(attribParser.getAttributeName());
			
		}
		loadRecordsFromFile(temp, recordFile);
	}
	
	
	public static void loadRecordsFromFile(TransactionTable temp,String recordFile)
	{
		TextFileReader tf = new TextFileReader(recordFile);
		
		CommonLogics cl = new CommonLogics();
		
		RecordParser recParser;
		
		List<String> records = tf.getOutput();
		tf.closeStream();
		
		for(int i=0;i<records.size();i++)
		{
			recParser = new RecordParser(records.get(i));
			
				temp.addRecord(cl.convertsListStringToListInt(
						recParser.getRecordElements()
								)
						);
			
		}
		
	}
	
	/**
	 * Loads the Records from text file into Object
	 * @param Attribute and class object
	 * @param training file location
	 */
	public static void loadRecordsFromFile(DataTable temp,String recordFile)
	{
		TextFileReader tf = new TextFileReader(recordFile);
		
		RecordParser recParser;
		
		List<String> records = tf.getOutput();
		tf.closeStream();
		
		for(int i=0;i<records.size();i++)
		{
			recParser = new RecordParser(records.get(i));
			
				temp.addRecord(recParser.getRecordElements(), recParser.getCategory());
			
		}
	}
	
	
}
