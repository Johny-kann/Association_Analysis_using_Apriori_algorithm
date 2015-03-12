package com.data_mining.logic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.data_mining.constants.FilesList;
import com.data_mining.constants.Notations;
import com.data_mining.file_readers.TextFileWriter;
import com.data_mining.logs.TrainingLog;
import com.data_mining.model.association.FrequentAndCandidateItemSet;
import com.data_mining.model.association.ItemSet;
import com.data_mining.model.association.TransactionTable;
import com.data_mining.model.attributes_records.DataTable;
import com.data_mining.model.attributes_records.OrderedClassSet;
import com.data_mining.model.rules.RuleCondition;
import com.data_mining.model.rules.RuleSet;
import com.data_mining.model.rules.Rules;
import com.data_mining.view.console.Outputs;


/**
 * @author Janakiraman
 *
 *Class with methods to choose the best attributes
 */
public class ChoosingAttributes {

		
	public RuleSet fillRuleSet(DataTable input,OrderedClassSet set,DataTable validation)
	{
		RuleSet ruleSet = new RuleSet();
		
		int index = 0;
		
		for(int i=0;i<set.getClassesAlone().size();i++)
		{
			if(i == set.getClassesAlone().size()-1)
			{
				ruleSet.addRules(defaultRule(set.getClassAtIndex(i)
						, index,validation));
			
			}
			else
			{
				TrainingLog.trainLogs.info("Extracting Rule for "+set.getClassAtIndex(i));
				index = extractRule(input, ruleSet, set.getClassAtIndex(i), index,validation);
			}
		}
		return ruleSet;
	}
	
	public Rules defaultRule(String category,int index,DataTable val)
	{
	
		return new Rules(index, category,laplaceForTable(val, category));
	}
	
	public int extractRule(DataTable input,RuleSet ruleset,String category,int index,DataTable validation)
	{
			
	
		DataTable temp=null;
		try {
			temp = input.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(temp.sizeOfRecords()!=0)
		{
			DataTable reserve;
			Rules rule = addRule(temp, category, index);
		
			reserve = refineCoveredRules(temp, rule);
	
			if(reserve.sizeOfRecords()==0)
			{
				TrainingLog.trainLogs.info("Reserve size is zero breaking loop");
				break;
				
			}
				
			CommonLogics cl = new CommonLogics();
	
			cl.removeRecords(temp, reserve);
	
		if(Notations.PRUNING_ON)
		{
			rule = pruneTheRule(rule, validation);

			if(rule.getgError()<0.5)
			{
				
				TrainingLog.trainLogs.info("Adding rule inside pruning "+new Outputs().outputRule(rule)
						);
			ruleset.addRules(rule);
			index++;
			}
			else
			{
				TrainingLog.trainLogs.info("Eliminating Rule for generalization error greater than 0.5");
				break;
			}
		}
		else
		{
			TrainingLog.trainLogs.info("Adding rule outside pruning "+new Outputs().outputRule(rule)
					);
			ruleset.addRules(rule);
			index++;
		}
		}
		
		return index;
	}
	
	public Rules pruneTheRule(Rules rule,DataTable validationTable)
	{
			 
		Rules newRule = null;
		DataTable temp = null;
		try {
			newRule = rule.clone();
			temp = validationTable.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Double laplace = laplaceForTable(temp, newRule.getCategory());
		Double gError = 1- laplace;
		
		for(int i=newRule.getRules().size()-1;i>=0;i--)
		{
			newRule.getRules().remove(i);
			
			temp = refineCoveredRules(validationTable, newRule);
			
			Double newgError = 1 - laplaceForTable(temp, newRule.getCategory());
			
			
			if(newgError<gError)
			{
		//		System.out.println("Hell");
		
				TrainingLog.trainLogs.info("Pruning the rule "+rule);
			try {
				rule = newRule.clone();
				TrainingLog.trainLogs.info("New Rule "+rule);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			gError = newgError;
			}
			
			
		}
		
		rule.setgError(gError);
		
	return rule;
	}
	
	public DataTable refineCoveredRules(DataTable input,Rules rule)
	{
		DataTable temp = null;
		try {
			temp = input.clone();
		} catch (CloneNotSupportedException e) {
		
			e.printStackTrace();
		}
		if(rule.getRules().size()>0)
		{
		for(RuleCondition cond:rule.getRules())
		{
			if(temp.numberOfAttributes()!=0)
			{
			
			temp = new SearchingLogics().
				refiningSetBasedForNextRule(temp, cond);
			}
		}
		}
		return temp;
	}
	
public Rules addRule(DataTable input,String category,int index)
	{
		DataTable temp = null;
		try {
			temp = input.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Rules ruleRecord = new Rules(index, category);
		
		Double pastMeasure = laplaceForTable(input, category);
		Double newMeasure = null;
		
		Boolean run = true;

		SearchingLogics sl = new SearchingLogics();
		
		while(run)
		{
			
			RuleCondition rc;
			try
			{
			rc = findBestAttribute(temp, pastMeasure, category);
			newMeasure = rc.getError();
			
			if(newMeasure>pastMeasure)
			{
	
				TrainingLog.trainLogs.info("Adding the rule condition "+rc.getCondition());
			ruleRecord.addRule(rc);

			
			temp = sl.refiningSetBasedonRuleCondition(temp, rc);
		
			if(temp.sizeOfRecords()==0)
			{
			run = false;
	
			TrainingLog.trainLogs.info("Training record size zero");
			}
			else
			{
				run = true;
			}
			pastMeasure = newMeasure;
			
			}
			else
			{
				run = false;
			}
			}catch(IndexOutOfBoundsException ie)
			{
				run = false;
				Outputs.printToConsole("Index Out of Bound");
			}
		
}

	return ruleRecord;
	}
	
	/**
	 * Calculates the error for the table
	 * @param table
	 * @return error value
	 * 
	 */
	public double calculateErrorForTable(DataTable table)
	{
		List<String> values = table.getClassValues();
		
		CommonLogics cl = new CommonLogics();
		
		Map<String, Integer> categor =  
				cl.classAndCounts(table);
	//			new LinkedHashMap<String, Integer>();
		
		ErrorsAndGain eg = new ErrorsAndGain();
		
		return eg.giniError(categor);
		
	}
	
	/**
	 * finds the best attribute based on the split and returns the an object containing the details
	 * @param table
	 * @param error
	 * @param +ve class
	 * @return
	 */
	public RuleCondition findBestAttribute(DataTable input,Double error,String category) throws IndexOutOfBoundsException
	{
	//	Map<String,Double> attrbErrorMap = new LinkedHashMap<String, Double>();
				
		List<RuleCondition> rules = new ArrayList<RuleCondition>();
		
		for(int i=0;i<input.numberOfAttributes();i++)
		{
			if(input.getAttributes().get(i).getType().equals(Notations.DISCRETE_ATTRB))
			{
	
			rules.addAll(findErrorForDiscrete(input, i,category)
						);
				
			}
			
			if(input.getAttributes().get(i).getType().equals(Notations.CNTS_ATTRB))
			{
							
				rules.addAll(findErrorForContinuous
						(input, i,category)
						);
				
			}
		}
		
		
		for(RuleCondition rr:rules)
		{
		
		}
		CommonLogics cl = new CommonLogics();

		return cl.bestAttributeFromErrorModel(rules);
		
		
	}
	
	
	/**
	 * Finds error for discrete attributes
	 * @param input
	 * @param index
	 * 
	 * @return List of rule Conditions
	 */
	private List<RuleCondition> findErrorForDiscrete(DataTable input,int index,String category)
	{
		List<String> values =  input.getAttributes().get(index).getValues();
		DataTable temp;
		
		
		List<RuleCondition> ruleConditions = new ArrayList<RuleCondition>();
	
		
		for(String str:values)
		{
			SearchingLogics sl = new SearchingLogics();
//			System.out.println(input.getAttributeName(index));
			
			temp = sl.refiningSetDiscrete(input, index, str);
//			System.out.println(temp.getAttributeName(index));
			
			CommonLogics cl = new CommonLogics();
	
		
			ruleConditions.add(
					new RuleCondition(input.getAttributeName(index), 
							str, 
							cl.conditionGeneratorDiscrete(input.getAttributeName(index), str), 
							laplaceForTable(temp, category),
							Notations.DISCRETE_EQUAL,
							temp.getCountOfAClassValues(category)
							)
					);
		
		}
		
//		System.out.println(errors);
//		ErrorsAndGain er = new ErrorsAndGain();
//		Double error = er.errorSplit(errors, records, input.sizeOfRecords());
		
		return ruleConditions;
		
//		Double gainRatio = er.gainRatio(records, input.sizeOfRecords(), pError,error);
	//	return new ErrorModel(attrbName,index, attrbType, null, error,gainRatio);
	}
	


	/**
	 * finds errors for continuous attributes
	 * @param table
	 * @param attribute index
	 * @param parent error
	 * @return list of Rule Conditions
	 */
	private List<RuleCondition> findErrorForContinuous(DataTable input,int index,String category)
	{
		List<RuleCondition> ruleConditions = new ArrayList<RuleCondition>();
		
		List<Double> values = findValuesForContinuousAttributes(input, index);
		DataTable temp,temp2;
		
//		System.out.println(values);
//		new Outputs().outPutTable(input);
		
		CommonLogics cl = new CommonLogics();
		
		for(Double str:values)
		{
			List<Integer> records = new ArrayList<Integer>();
			List<Double> errors = new ArrayList<Double>();
			SearchingLogics sl = new SearchingLogics();
			String name = input.getAttributeName(index);
			
			
			temp = sl.refiningSetContinuousLeft(input, index, str);
			
		
			ruleConditions.add(
					new RuleCondition(input.getAttributeName(index), 
							str.toString(), 
							cl.conditionGeneratorCnts(input.getAttributeName(index), str,true), 
							laplaceForTable(temp, category),
							Notations.CNTS_LEFT,
							cl.getCountOfClassValue(temp, category)
							)
			);
			
			
			temp2 = sl.refiningSetContinuousRight(input, index, str);

	
			 
			ruleConditions.add(
					new RuleCondition(input.getAttributeName(index), 
							str.toString(), 
							cl.conditionGeneratorCnts(input.getAttributeName(index), str,false), 
							laplaceForTable(temp2, category),
							Notations.CNTS_RIGHT,
							temp.getCountOfAClassValues(category)
							)
			);

			
			
		}
		
		return ruleConditions;
	}
	
	/**
	 * @param table
	 * @param attribute index
	 * @return possible values of continuous attributes
	 */
	private List<Double> findValuesForContinuousAttributes(DataTable input, int index)
	{
		CommonLogics cl = new CommonLogics();
		
		cl.sort(input, index);
		
		List<Double> splits = cl.fillSplitList(input, index);
		List<Integer> pos = cl.splitPostition(input);
		
		List<Double> values = new ArrayList<Double>();
		for(int i=0;i<pos.size();i++)
		{
			values.add(splits.get(pos.get(i)));
		}
		
		return values;
	}
	
	/**
	 * @param categories
	 * @return error gain
	 */
	public Double error(Map<String,Integer> categories)
	{
		ErrorsAndGain errGain = new ErrorsAndGain();
		return errGain.classificationError(categories);
	}
	
	
	
	
	public double laplaceForTable(DataTable table,String classValue)
	{
		CommonLogics cl = new CommonLogics();
		Integer correctClass = cl.getCountOfClassValue(table, classValue);
		Integer noOfRecords = table.sizeOfRecords();
		Integer classValues = table.getClassValues().size();
		
		ErrorsAndGain error = new ErrorsAndGain();
		return error.laplace(correctClass, noOfRecords, classValues);
		
	}
	
	public double AccuracyForTableByRuleSet(DataTable table,RuleSet rule,StringBuffer stBuffer)
	{
		SearchingLogics sl = new SearchingLogics();
		int correct=0;
		int wrong = 0;
		DataTable temp = null;
		
		try
		{
			temp = table.clone();
		}catch(CloneNotSupportedException ce)
		{
			ce.printStackTrace();
		}
		CommonLogics cl = new CommonLogics();
		for(Rules rr:rule.getRulesList())
		{
			
			DataTable reserve = refineCoveredRules(temp, rr);
			
					String val = rr.getCategory();
				
					correct+=reserve.getCountOfAClassValues(val);
					rr.setCorrectClass(reserve.getCountOfAClassValues(val));
					
					wrong+=reserve.getCountOfOtherClassValues(val);
				
					rr.addWrongClass(reserve.getCountOfOtherClassValues(val));
				
//				System.out.println(correct+"|"+wrong);
					stBuffer.append(
							new Outputs().outputRule(rr)+
							" correct "+rr.getCorrectClass()+" wrong "+rr.getWrongClass());
					stBuffer.append(System.lineSeparator());
			
			cl.removeRecords(temp, reserve);
		}
		
		
		return (double)correct/(wrong+correct);
	}
	
	public FrequentAndCandidateItemSet getResultSet(TransactionTable table, double minSupport)
	{
		boolean run = true;
		AssociationLogics chs = new AssociationLogics();
		FrequentAndCandidateItemSet freq = null;
		FrequentAndCandidateItemSet result = new FrequentAndCandidateItemSet(ItemSet.ResultSet);
		StringBuffer str = new StringBuffer();
		str.append("Generation By "+Notations.GENERATION_TYPE);
		str.append(System.lineSeparator());
		
		for(int k=1;run;k++)
		{
			freq = chs.generateFrequentSetForK(table, minSupport, k, freq,str);
			result.addAllItem(freq);
			
			if(freq.size()==0)
			{
				run=false;
			}
		}
		
		TextFileWriter tfw = new TextFileWriter();
		tfw.writeFile(str.toString(), FilesList.WRITE_RESULT);
		
		return result;
	}
}
