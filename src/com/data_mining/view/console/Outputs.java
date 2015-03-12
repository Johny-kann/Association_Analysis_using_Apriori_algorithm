package com.data_mining.view.console;

import java.util.logging.Level;

import com.data_mining.constants.Notations;
import com.data_mining.logs.TrainingLog;
import com.data_mining.model.association.AssociationList;
import com.data_mining.model.association.AssociationRule;
import com.data_mining.model.association.FrequentAndCandidateItemSet;
import com.data_mining.model.association.TransactionTable;

public class Outputs {

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
	
	public static void printToConsole(String str)
	{
		System.out.println(str);
//		TrainingLog.trainLogs.log(Level.INFO,str);
	}
}
