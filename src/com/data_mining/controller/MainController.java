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
import com.data_mining.logic.SearchingLogics;
import com.data_mining.logs.TrainingLog;
import com.data_mining.model.association.AssociationList;
import com.data_mining.model.association.FrequentAndCandidateItemSet;
import com.data_mining.model.association.TransactionTable;
import com.data_mining.model.attributes_records.DataTable;
import com.data_mining.model.attributes_records.OrderedClassSet;
import com.data_mining.model.rules.RuleSet;
import com.data_mining.view.console.Outputs;


/**
 * @author Janakiraman 
 * 
 * Main Controller for loading test data and train data and calling functions
 *
 */
public class MainController {

	DataTable mainAttributes;
	DataTable trainData;
	DataTable validationData;
	DataTable testData;
	RuleSet mainRuleSet;
	OrderedClassSet sortedClassSet;
	TransactionTable transTable;
	
	public MainController()
	{
		mainAttributes = new DataTable();
		mainRuleSet = new RuleSet();
		testData = new DataTable();
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
	
	
	public void fillRuleSet()
	{
		CommonLogics cl = new CommonLogics();

		
	//	System.out.println(classes.getOrderedClasses());
	ChoosingAttributes choose = new ChoosingAttributes();
	if(Notations.PRUNING_ON)
	{
		sortedClassSet = new OrderedClassSet(
				cl.sortMapValues
				(cl.classAndCounts(trainData))
				);
		TrainingLog.trainLogs.info("Sorted the input classes");
		
		Outputs.printToConsole("Order of the classes "+ sortedClassSet.getClassesAlone()
				);
		
	mainRuleSet = choose.fillRuleSet(trainData, sortedClassSet,validationData);
	orderBasedOnGeneralError();
	}else
	{
	//	System.out.println(mainAttributes.sizeOfRecords()+","+trainData.sizeOfRecords()+","+testData.sizeOfRecords());
		Map<String,Integer> map = cl.classAndCounts(mainAttributes);
		
	
		sortedClassSet = new OrderedClassSet(
				cl.sortMapValues
				(map)
				);
		
		Outputs.printToConsole("Order of the classes "+ sortedClassSet.getClassesAlone()
				);
		mainRuleSet = choose.fillRuleSet(mainAttributes, sortedClassSet,validationData);
	}
	
	}
	
	public void getResultSet()
	{
		
	}
	
	public void orderBasedOnGeneralError()
	{
		new CommonLogics().sortRulesBasedonGeneralizationError(mainRuleSet);
		
	}
	
	public void output()
	{
		trainDataAccuracy();
		
		if(Notations.TEST_ON)
		{
			testDataAccuracy();
		}
		mainRuleSetPrint();
	}
	
	public void trainDataAccuracy()
	{
		StringBuffer stBuffer = new StringBuffer();

		stBuffer.append(new Outputs().outputRuleSet(mainRuleSet));
		stBuffer.append("Train Data");
		stBuffer.append(System.lineSeparator());
		
				stBuffer.append(new Outputs().outPutTable(mainAttributes));
	
		stBuffer.append("Accuracy "+
		new ChoosingAttributes().AccuracyForTableByRuleSet(mainAttributes, mainRuleSet,stBuffer)
				);

		Outputs.printToConsole(stBuffer.toString());
		new TextFileWriter().writeFile(stBuffer.toString(), FilesList.WRITE_TRAIN_RESULT);
			
	}
	
	public void mainRuleSetPrint()
	{
		StringBuffer stBuffer = new StringBuffer();
		new TextFileWriter().writeFile(
				new Outputs().outputRuleSet(mainRuleSet), 
				FilesList.WRITE_RULE_SET) ;
	}
	
	public void testDataAccuracy()
	{
		AttributeAndRecordLoaders.loadAttributeFromFile(testData, FilesList.ATTRIBUTES_FILES, FilesList.TEST_RECORD_FILES);
		TrainingLog.testLogs.info("Accuracy Loaded from file");
		StringBuffer stBuffer = new StringBuffer();
		
			
		Outputs.printToConsole(
		new Outputs().outPutTable(testData)
		);
		stBuffer.append("Test Data");
		stBuffer.append(System.lineSeparator());
		stBuffer.append(new Outputs().outPutTable(testData)
				);
		stBuffer.append("Accuracy "+
				new ChoosingAttributes().AccuracyForTableByRuleSet(testData, mainRuleSet,stBuffer));
		
		Outputs.printToConsole(stBuffer.toString());
		new TextFileWriter().writeFile(stBuffer.toString(), FilesList.WRITE_TEST_RESULT);
	}
	
	
	public DataTable getMainTable()
	{
		return mainAttributes;
	}
	
	public DataTable getTrainAttributes()
	{
		return trainData;
	}
	
	public DataTable getTestAttributes() {
		return validationData;
	}

	public OrderedClassSet getSortedClassSet() {
		return sortedClassSet;
	}
	
	
}
