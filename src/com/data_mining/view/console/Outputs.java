package com.data_mining.view.console;

import java.util.logging.Level;

import com.data_mining.constants.Notations;
import com.data_mining.logs.TrainingLog;
import com.data_mining.model.association.AssociationList;
import com.data_mining.model.association.AssociationRule;
import com.data_mining.model.association.FrequentAndCandidateItemSet;
import com.data_mining.model.association.TransactionTable;
import com.data_mining.model.attributes_records.DataTable;
import com.data_mining.model.rules.RuleCondition;
import com.data_mining.model.rules.RuleSet;
import com.data_mining.model.rules.Rules;

public class Outputs {

	/**
	 * prints the table
	 * @param table
	 * @return gives the string
	 */
	public String outPutTable(DataTable table)
	{
		String str = "";
		Integer row = table.sizeOfRecords();
		Integer col = table.numberOfAttributes();
		
		for(int i=0;i<=col;i++)
		{
			if(i<col)
			{
		str += (
					table.getAttributes().get(i).getName()+"\t\t"
					);
	//		str+="\t\t";
			}
			else
			{
				str += (table.getClassName());
				str+=table.getClassName();
				str+=System.lineSeparator();
			}
		}
		
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<=col;j++)
			{
				if(j<col)
				{
				str+=table.getRecordAtIndex(i).getElementValueAtIndex(j)+"\t\t";
				}
				else
				{

					str+=table.getRecordAtIndex(i).getClassAttribute();
					str+=System.lineSeparator();
					
				}
			}
		}
		return str;
	}
	
	
	public String outPutTransaction(TransactionTable table)
	{
		String str = "";
		Integer row = table.numberOfRecords();
		Integer col = table.numberOfItems();
		
		for(int i=0;i<col;i++)
		{
			
		str += (	table.getItemNameAtIndex(i)
					+"\t\t"
					);
		
		}
		
		str+=System.lineSeparator();
		
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				str+=table.getRecordAtIndex(i).getItemAtIndex(j)+"\t\t";
			}
			str+=System.lineSeparator();
		}
		return str;
	}
	
	
	public String outputFrequentItemSet(FrequentAndCandidateItemSet freq)
	{
		StringBuffer str = new StringBuffer();
		for(int i=0;i<freq.size();i++)
		{
			str.append(freq.setAtIndex(i));
			str.append(" "+freq.countAtIndex(i));
			str.append(System.lineSeparator());
		}
		return str.toString();
	}
	
	
	public String outputAssociationRules(AssociationList list)
	{
		StringBuffer stb= new StringBuffer();
		
		for(AssociationRule rule : list.getListAssocsRule())
		{
			stb.append(rule.getAntecedant()+"-->"+rule.getConsequent()+"\tconfidence  "+rule.getConfidence());
			stb.append(System.lineSeparator());
		}
		
		return stb.toString();
	}
	
	public String outputRuleSet(RuleSet ruleSet)
	{
		StringBuffer str = new StringBuffer();
		for(int i=0;i<ruleSet.getRulesList().size();i++)
		{
			try
			{
				if(i==ruleSet.getRulesList().size()-1)
				{
					str.append(outputRule(ruleSet.getRulesList().get(i))+"{}");
		//			str.append(" --> "+ruleSet.getRulesList().get(i).getCategory());
			
				}
				else
				{
				str.append(outputRule(ruleSet.getRulesList().get(i)));
				}
				
				str.append(" --> "+ruleSet.getRulesList().get(i).getCategory());
				
				if(Notations.PRUNING_ON)
				{
					str.append(" G error "+giveGError(ruleSet, i));
				}
			}catch(IndexOutOfBoundsException ie)
			{
			//	System.out.println("Index out of bound");
				str.append("{}");
				str.append(" --> "+ruleSet.getRulesList().get(i).getCategory()+" error "+giveGError(ruleSet, i));
			}
		  str.append(System.lineSeparator());
		}
		
		return str.toString();
	}
	
	public String giveGError(RuleSet ruleset,int index)
	{
		if(Notations.PRUNING_ON)
		{
		return ruleset.getRulesList().get(index).getgError().toString();
		}
		else
		{
			return "";
		}
	}
	public String outputRule(Rules rule)
	{
		Boolean lastRule;
		if(rule.getRules().size()==0)
		{
			lastRule = true;
		}
		else
		{
			lastRule = false;
		}
		StringBuffer str = new StringBuffer();
		str.append("r"+rule.getRuleNumber()+":");
		for(RuleCondition temp:rule.getRules())
		{
			str.append(temp.getCondition()+"/\\");
		}
		if(!lastRule)
		{
		return str.substring(0, str.length()-2).toString();
		}
		else
		{
		return str.toString();
		}
	}
	
	public static void printToConsole(String str)
	{
		System.out.println(str);
//		TrainingLog.trainLogs.log(Level.INFO,str);
	}
}
