package com.data_mining.controller;

import java.util.Map;

import com.data_mining.constants.FilesList;
import com.data_mining.constants.Notations;
import com.data_mining.constants.ValueConstants;
import com.data_mining.file_readers.PropertiesConfig;
import com.data_mining.file_readers.TextFileWriter;
import com.data_mining.logic.AssociationLogics;
import com.data_mining.logic.AttributeAndRecordLoaders;
import com.data_mining.logic.ChoosingAttributes;
import com.data_mining.logic.CommonLogics;
import com.data_mining.logs.TrainingLog;
import com.data_mining.model.association.AssociationList;
import com.data_mining.model.association.FrequentAndCandidateItemSet;
import com.data_mining.model.association.TransactionTable;
import com.data_mining.view.console.Outputs;


/**
 * @author Janakiraman 
 * 
 * Main Controller for loading test data and train data and calling functions
 *
 */
public class MainController {

	TransactionTable transTable;
	
	public MainController()
	{
		PropertiesConfig.assignInputFiles();
	
	}
	
	
	
	public void loadTable()
	{
		TransactionTable temp = new TransactionTable();
		
		AttributeAndRecordLoaders.loadAttributeFromFile(temp, FilesList.ATTRIBUTES_FILES, FilesList.RECORD_FILES);
		transTable = temp;
	}
	

	public void start()
	{
		ChoosingAttributes chs = new ChoosingAttributes();
		FrequentAndCandidateItemSet result = chs.getResultSet(transTable, ValueConstants.MIN_SUPPORT);
		
		AssociationLogics al = new AssociationLogics();
		AssociationList list = al.ruleGeneration(result, transTable, ValueConstants.MIN_CONFIDENCE,ValueConstants.MIN_SUPPORT);
	
		StringBuffer stb = new StringBuffer();
		stb.append(new Outputs().outPutTransaction(transTable));
		stb.append(System.lineSeparator());
		stb.append("Frequent Item Sets");
		stb.append(System.lineSeparator());
		stb.append(new Outputs().outputFrequentItemSet(result));
		stb.append(System.lineSeparator());
		stb.append("Association Rules");
		stb.append(System.lineSeparator());
		stb.append(new Outputs().outputAssociationRules(list));
		
		System.out.println(stb.toString());
		new TextFileWriter().writeFile(stb.toString(), FilesList.WRITE_ITEMSET);
		
	}
	
	
	
	
}
