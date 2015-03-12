package com.data_mining.logic;

import java.util.List;
import java.util.Set;

import com.data_mining.constants.NodeConstants;
import com.data_mining.constants.Notations;
import com.data_mining.model.association.TransactionTable;
import com.data_mining.model.attributes_records.AttributesSpecifications;
import com.data_mining.model.attributes_records.DataTable;
import com.data_mining.model.attributes_records.Records;
import com.data_mining.model.rules.RuleCondition;
import com.data_mining.model.rules.Rules;
import com.data_mining.view.console.Outputs;

/**
 * @author Janakiraman
 * Class used for searching and refining attributes
 *
 */
public class SearchingLogics {

	/**
	 * @param temp
	 * @param name
	 * @return attribute obj
	 */
	public AttributesSpecifications searchingAttribute(List<AttributesSpecifications> temp,String name)
	{
		AttributesSpecifications result = null;
		
		for(AttributesSpecifications attribute:temp)
		{
			if(attribute.getName().equals(name))
			{
				result = attribute;
				break;
			}
		}
		
		return result;
	}
	
	public Integer getAttributeIndex(List<AttributesSpecifications> temp,String name)
	{
		Integer result = 0;
		
		
		for(AttributesSpecifications attribute:temp)
		{
			if(attribute.getName().equals(name))
			{
				break;
			}
			else
			{
				result++;
			}
		}
		
		return result;
	}
	
	public DataTable refiningSetBasedonRuleCondition(DataTable table,RuleCondition cond)
	{
		
		if(cond.getSplit().equalsIgnoreCase(Notations.DISCRETE_EQUAL))
		{
			return refiningSetDiscrete(table, table.getAttributeIndex(cond.getName())
					, cond.getValue());
		}
		else if(cond.getSplit().equalsIgnoreCase(Notations.CNTS_LEFT))
		{
			return refiningSetContinuousLeft(table, table.getAttributeIndex(cond.getName()),
					Double.parseDouble(	
					cond.getValue()
					));
		}
		else if(cond.getSplit().equalsIgnoreCase(Notations.CNTS_RIGHT))
		{
			return refiningSetContinuousRight(table, table.getAttributeIndex(cond.getName()),
					Double.parseDouble(	
					cond.getValue()
					));
		}
		else
		{
			Outputs.printToConsole("Splitting condition dint match");
			return null;
		}
		
		 
	}
	
	public DataTable refiningSetBasedForNextRule(DataTable table,RuleCondition cond)
	{
		if(cond.getSplit().equalsIgnoreCase(Notations.DISCRETE_EQUAL))
		{
			return refiningSet(table, table.getAttributeIndex(cond.getName())
					, cond.getValue());
		}
		
		else if(cond.getSplit().equalsIgnoreCase(Notations.CNTS_LEFT))
		{
			return refiningSetContinuousLeft(table, table.getAttributeIndex(cond.getName()),
					Double.parseDouble(	
					cond.getValue()
					));
		}
		else if(cond.getSplit().equalsIgnoreCase(Notations.CNTS_RIGHT))
		{
		
			return refiningSetContinuousRight(table, table.getAttributeIndex(cond.getName()),
					Double.parseDouble(	
					cond.getValue()
					));
		}
		else
		{
			Outputs.printToConsole("Splitting condition dint match");
			return null;
		}
	}
	
	public DataTable refiningSet(DataTable table,int attributeIndex,String attrValue)
	{
		DataTable newSet = new DataTable();
		addAttributeContents(newSet, table, attributeIndex);
		recordRefinment(newSet, table, attributeIndex, attrValue);
		return newSet;
		
	}
	
	
	
	public DataTable refiningSetDiscrete(DataTable table,int attributeIndex,String attrValue)
	{
		DataTable newSet = new DataTable();
		addAttributeContentsDiscrete(newSet, table, attributeIndex);
		recordRefinmentDiscrete(newSet, table, attributeIndex, attrValue);
		return newSet;
		
	}
	
	public DataTable refiningSetContinuousLeft(DataTable table,int attributeIndex,Double attrValue)
	{
		DataTable newSet = new DataTable();
		addAttributeContents(newSet, table, attributeIndex);
		recordRefinmentCntnsLeft(newSet, table, attributeIndex, attrValue);
		return newSet;
		
	}

	public DataTable refiningSetContinuousRight(DataTable table,int attributeIndex,Double attrValue)
	{
		DataTable newSet = new DataTable();
		addAttributeContents(newSet, table, attributeIndex);

		recordRefinmentCntnsRight(newSet, table, attributeIndex, attrValue);
		return newSet;
		
	}
	
	private void recordRefinment(DataTable newSet,DataTable oldSet,int attrbIndex,String attrbValue)
	{
		CommonLogics cl = new CommonLogics();
		
		for(int i=0;i<oldSet.sizeOfRecords();i++)
		{
			if(oldSet.searchByRowAndColumn(i, attrbIndex).equals(attrbValue))
			{
				newSet.addRecord(
						new Records(oldSet.getRecordAtIndex(i))
						);
			}
		}
	}
	
	private void recordRemoval(DataTable newSet,DataTable oldSet,int attrbIndex,String attrbValue)
	{
		
		for(int i=0;i<oldSet.sizeOfRecords();i++)
		{
			if((oldSet.searchByRowAndColumn(i, attrbIndex).equals(attrbValue)))
			{
			/*	newSet.addRecord(
						new Records(oldSet.getRecordAtIndex(i))
						);*/
				newSet.getRecords().remove(i);
			}
		}
	}
	
	private void recordRefinmentDiscrete(DataTable newSet,DataTable oldSet,int attrbIndex,String attrbValue)
	{
		CommonLogics cl = new CommonLogics();
		
		for(int i=0;i<oldSet.sizeOfRecords();i++)
		{
			if(oldSet.searchByRowAndColumn(i, attrbIndex).equals(attrbValue))
			{
				newSet.addRecord(
								cl.removeElementFromRecordDiscrete(
								oldSet.getRecordAtIndex(i)
										, attrbIndex)
								);
			}
		}
	}
	
	private void recordRefinmentCntnsLeft(DataTable newSet,DataTable oldSet,int attrbIndex,Double attrbValue)
	{
		CommonLogics cl = new CommonLogics();
		
		for(int i=0;i<oldSet.sizeOfRecords();i++)
		{
			if(Double.parseDouble(oldSet.searchByRowAndColumn(i, attrbIndex))
					<(attrbValue))
			{
//				newSet.addRecord(
//								cl.removeElementFromRecordDiscrete(
//								oldSet.getRecordAtIndex(i)
//										, attrbIndex)
//								);
				
				newSet.addRecord(
						new Records(oldSet.getRecordAtIndex(i))
						);
			}
		}
	}
	
	private void recordRefinmentCntnsRight(DataTable newSet,DataTable oldSet,int attrbIndex,Double attrbValue)
	{
		CommonLogics cl = new CommonLogics();
		
		for(int i=0;i<oldSet.sizeOfRecords();i++)
		{
			
			if(Double.parseDouble(oldSet.searchByRowAndColumn(i, attrbIndex))
					>=(attrbValue))
			{
//				newSet.addRecord(
//								cl.removeElementFromRecordDiscrete(
//								oldSet.getRecordAtIndex(i)
//										, attrbIndex)
//								);
				newSet.addRecord(new Records(oldSet.getRecordAtIndex(i)));
			}
		}
	}
	
	public void addAttributeContentsDiscrete(DataTable newSet,DataTable oldSet, int attrbIndex)
	{
		for(int i=0;i<oldSet.totColumns();i++)
		{
			if(i!=attrbIndex)
			{
				newSet.addAttribute(oldSet.getAttributes().get(i).getName(),
						oldSet.getAttributes().get(i).getType(),
						oldSet.getAttributes().get(i).getValues()
						);
			}
		}
		
	}
	

	
	public void addAttributeContents(DataTable newSet,DataTable oldSet, int attrbIndex)
	{
			for(int i=0;i<oldSet.totColumns();i++)
			{
						newSet.addAttribute(oldSet.getAttributes().get(i).getName(),
							oldSet.getAttributes().get(i).getType(),
							oldSet.getAttributes().get(i).getValues()
							);
			}
	}
	
	public void addAttributeContents(DataTable newSet,DataTable oldSet)
	{
		for(int i=0;i<oldSet.totColumns();i++)
		{
					newSet.addAttribute(oldSet.getAttributes().get(i).getName(),
						oldSet.getAttributes().get(i).getType(),
						oldSet.getAttributes().get(i).getValues()
						);
			
		}
	}

	
	
	public DataTable extractDataBasedOnCondition(DataTable input,String nodeCondition)
	{
		DataTable temp = new DataTable();
		CommonLogics cl = new CommonLogics();
		if(nodeCondition.equals(NodeConstants.ROOT_NODE))
		{
			temp = input;
			
		}
		else if(cl.getDecisionForChildRecordSender(nodeCondition)==Notations.DISCRETE_EQUAL)
		{
			SearchingLogics sl = new SearchingLogics();
			temp = sl.refiningSetDiscrete(input, input.getAttributeIndex(
					cl.getNodeNameFromCondition(nodeCondition)),
					cl.getNodeValueFromCondition(nodeCondition));
		}
		else if(cl.getDecisionForChildRecordSender(nodeCondition)==Notations.CNTS_LEFT)
		{
			SearchingLogics sl = new SearchingLogics();
			temp = sl.refiningSetContinuousLeft(input, input.getAttributeIndex(
					cl.getNodeNameFromCondition(nodeCondition)),
					Double.parseDouble(
					cl.getNodeValueFromCondition(nodeCondition)
					));
		}
		else if(cl.getDecisionForChildRecordSender(nodeCondition)==Notations.CNTS_RIGHT)
		{
			SearchingLogics sl = new SearchingLogics();
			temp = sl.refiningSetContinuousRight(input, input.getAttributeIndex(
					cl.getNodeNameFromCondition(nodeCondition)),
					Double.parseDouble(
					cl.getNodeValueFromCondition(nodeCondition)
					));
		}else
		{
			temp = null;
		}
		
		return temp;
	}
	
	public DataTable givetrainSet(DataTable totRecs) throws CloneNotSupportedException
	{
		DataTable validation = 
				totRecs.clone();
		
		validation.getRecords().subList(
				0, (int)(Notations.VALIDATION_PERCENT
						*totRecs.sizeOfRecords()/100)-1);
		
		return validation;
	}
	
	public DataTable getTrainingSet(DataTable input)
	{
		DataTable newTable = new DataTable();
		
		addAttributeContents(newTable, input);
		
		int endIndex = (int)((Notations.VALIDATION_PERCENT)/100*input.sizeOfRecords());
		
		for(int i=0;i<endIndex;i++)
		{
			newTable.addRecord(new Records(
					input.getRecordAtIndex(i))
			);
		}
		return newTable;
	}
	
	
	public DataTable getValidationSet(DataTable input)
	{
		DataTable newTable = new DataTable();
		
		addAttributeContents(newTable, input);
		
		int startIndex = (int)((Notations.VALIDATION_PERCENT)/100*input.sizeOfRecords());
		
		for(int i=startIndex;i<input.sizeOfRecords();i++)
		{
			newTable.addRecord(new Records(
					input.getRecordAtIndex(i))
			);
		}
		return newTable;
	}
	
	public Integer getSupportCount(TransactionTable table,Set<String> items)
	{
		return 0;
	}
	
}
