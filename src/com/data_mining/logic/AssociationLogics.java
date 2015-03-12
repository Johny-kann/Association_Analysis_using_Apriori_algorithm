package com.data_mining.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import com.data_mining.constants.FilesList;
import com.data_mining.constants.Notations;
import com.data_mining.file_readers.TextFileWriter;
import com.data_mining.logs.TrainingLog;
import com.data_mining.model.association.AssociationList;
import com.data_mining.model.association.AssociationRule;
import com.data_mining.model.association.FrequentAndCandidateItemSet;
import com.data_mining.model.association.Header;
import com.data_mining.model.association.ItemSet;
import com.data_mining.model.association.TransactionTable;
import com.data_mining.model.association.Transactions;
import com.data_mining.view.console.Outputs;

public class AssociationLogics {

	public FrequentAndCandidateItemSet generateFrequentSetForK(TransactionTable table,Double minSupport,int k,FrequentAndCandidateItemSet fKminus1,StringBuffer str)
	{
		FrequentAndCandidateItemSet
			freq1 = findFrequentItemSet1(table.getItemHeaderSet(), minSupport, table);
		FrequentAndCandidateItemSet freq = null;
		if(k==1)
		{
			freq = freq1;
			str.append("k="+k+System.lineSeparator());

			str.append(new Outputs().outputFrequentItemSet(freq1));
		}
		else
		{
			TrainingLog.mainLogs.info("Generating CK");
		//	freq = aprioriGenerationFkMinus1(fKminus1, table);
			freq = aprioriGeneration(fKminus1, freq1, table, Notations.GENERATION_TYPE);

			str.append("k="+k+System.lineSeparator());
			str.append("Number of candidates before pruning "+freq.size()+System.lineSeparator());
			extractFrequentSet(freq, minSupport, table.numberOfRecords());
			str.append("Number of candidates after pruning "+freq.size()+System.lineSeparator());
			str.append(new Outputs().outputFrequentItemSet(freq));
		}
		
		
		
		return freq;
	}
	
	
	public AssociationList apGenerateRules(Set<String> fk,FrequentAndCandidateItemSet Hm, FrequentAndCandidateItemSet freq1,TransactionTable table,double minConf)
	{
		int k= fk.size();
		
		Set<String> temp = new TreeSet<String>();
		CommonLogics cl = new CommonLogics();
		cl.copySet(fk, temp);
		
		int m = Hm.getSetNumber();
		AssociationList listAssocs = new AssociationList();
		
		
		if(k > m+1)
		{
			TrainingLog.mainLogs.info("Generating H"+(int)(m+1));
		
		
			FrequentAndCandidateItemSet HmPlus1 = 
		//				aprioriGenerationFkMinus1(Hm, table);
						aprioriGeneration(Hm, freq1, table, Notations.GENERATION_TYPE);
	
			double conf;
		
			int i=0;
			
//			List<Integer> listCounts = HmPlus1.getCount();
			ListIterator<Integer> listcounts = HmPlus1.getCount().listIterator();
			
			for(ListIterator<Set<String>> list = HmPlus1.getItems().listIterator();
					list.hasNext()&&listcounts.hasNext();i++)
			{
				Set<String> hmPlus1 = list.next();
				Integer count = listcounts.next();
				
				conf = getConfidence(table, temp, hmPlus1);
				
				if(conf>=minConf)
				{
					Set<String> temp2 = new TreeSet<String>();
					cl.copySet(temp, temp2);
					
					temp2.removeAll(hmPlus1);
				
					AssociationRule asr = new AssociationRule(temp2, hmPlus1, conf);
			
					listAssocs.addRule(
								asr
							);
				}
				else
				{
					listcounts.remove();
					list.remove();
				
				}
			}
			
			listAssocs.addAllRules(apGenerateRules(fk, 
						aprioriGenerationFkMinus1(HmPlus1, table),
					freq1, table, minConf));
			
		}
		
		return listAssocs;
	}
	
	
	public AssociationList ruleGeneration(FrequentAndCandidateItemSet result,TransactionTable table,double minconf,double minSupport)
	{
		AssociationList list = new AssociationList();
		FrequentAndCandidateItemSet
		freq1 = findFrequentItemSet1(table.getItemHeaderSet(), minSupport, table);
		
		int i=0;
		for(ListIterator<Set<String>> iter = result.getItems().listIterator();iter.hasNext();i++)
		{
			Set<String> fk = iter.next();
	
			if(fk.size()>=2)
			{
		
			FrequentAndCandidateItemSet H1 = oneItemConsequentSet(fk, table);
	
			TrainingLog.mainLogs.info("Generating H1");
			list.addAllRules(apGenerateRules(fk, H1,freq1, table, minconf));
			}
		}
		
		return list;
	
	}
	
	public FrequentAndCandidateItemSet oneItemConsequentSet(Set<String> fk,TransactionTable table)
	{
		FrequentAndCandidateItemSet H1 = new FrequentAndCandidateItemSet(1,ItemSet.CandidateItemSet);
		
		for(String str:fk)
		{
			H1.addItem(str, getSupportCount(table, str));
		}
		
		return H1;
	}
	
	public FrequentAndCandidateItemSet findFrequentItemSet1(Set<String> items,Double minSupport,TransactionTable table)
	{
		FrequentAndCandidateItemSet freq = new FrequentAndCandidateItemSet(1,ItemSet.FreqeuntItemSet);
				
		for(String item:items)
		{
		
		
			int count = getSupportCount(table, item); 
			if( (Double)((double)count/table.numberOfRecords()) >= minSupport )
			{
		
				freq.addItem(item, count);
			}
				
		
		}
		
		return freq;
	}
	
	public FrequentAndCandidateItemSet aprioriGenerationF1(FrequentAndCandidateItemSet freqKminus1,Set<String> f1,TransactionTable table)
	{
		FrequentAndCandidateItemSet candSet = new FrequentAndCandidateItemSet(freqKminus1.getSetNumber()+1, ItemSet.CandidateItemSet);
		CommonLogics cl = new CommonLogics();
		
		for(int i=0;i<freqKminus1.size();i++)
		{
			for(String str:f1)
			{
					Set<String> set = freqKminus1.setAtIndexCopy(i);
					set.add(str);
					candSet.addItem(set, getSupportCount(table, set)
							);
			}
		}
		
		candidatePruningForF1(candSet, freqKminus1);
		return candSet;
	}
	
	public FrequentAndCandidateItemSet candidatePruningForF1(FrequentAndCandidateItemSet candidateSet,FrequentAndCandidateItemSet fKMinus1Set)
	{
		int k = fKMinus1Set.getSetNumber();
	
		AssociationLogics al = new AssociationLogics();
		
		List<Integer> recsToRemove = new ArrayList<Integer>();
		
		for(int i=0;i<candidateSet.size();i++)
		{
			for(String str:candidateSet.getItems().get(i))
			{
				if(al.getSupportCount(fKMinus1Set, str)<k)
				{
					recsToRemove.add(i);
					break;
				}
			}
		}
		
		new CommonLogics().removeFromFreqAndCandSet(candidateSet, recsToRemove);
		
	//	removeListBasedOnIndexes(candidateSet.getItems(), recsToRemove);
		
//		candidateSet.getItems().removeAll(recsToRemove);
		return candidateSet;
		
	}
	
	/**
	 * Apriori generation
	 * @param freqKminus1
	 * @param freq1
	 * @param table
	 * @param generationType whether its by Fk-1*F1 or Fk-1 * Fk-1
	 * @return
	 */
	public FrequentAndCandidateItemSet aprioriGeneration(FrequentAndCandidateItemSet freqKminus1,FrequentAndCandidateItemSet freq1,TransactionTable table,String generationType)
	{
		if(generationType.equals(Notations.F1))
		{
			return aprioriGenerationFkMinus1(freqKminus1, table);
		}else if(generationType.equals(Notations.FK_1))
		{
			return aprioriGenerationF1(freqKminus1, convertsFreqAndCand1ToSet(freq1)
					, table);
		}
		return null;
	}
	
	/**
	 * generates candidate item set based on Fk-1 approach
	 * @param Fk-1 item Set
	 * @param transaction table
	 * @return Fk itemset
	 */
	public FrequentAndCandidateItemSet aprioriGenerationFkMinus1(FrequentAndCandidateItemSet freqKminus1,TransactionTable table)
	{
		FrequentAndCandidateItemSet candSet = new FrequentAndCandidateItemSet(freqKminus1.getSetNumber()+1, ItemSet.CandidateItemSet);
		CommonLogics cl = new CommonLogics();
		
		for(int i=0;i<freqKminus1.size();i++)
		{
			for(int j=i+1;j<freqKminus1.size();j++)
			{
	
				if(cl.initialSetMatch(
						freqKminus1.setAtIndexCopy(i), 
						freqKminus1.setAtIndexCopy(j), 
						freqKminus1.getSetNumber()-1))
				{
					Set<String> set = freqKminus1.setAtIndexCopy(i);
					set.addAll(freqKminus1.setAtIndexCopy(j));
					candSet.addItem(set, getSupportCount(table, set)
							);
				}
			}
		}
		
		return candSet;
	}
	
	
	public void extractFrequentSet(FrequentAndCandidateItemSet candSet,double minThreshold,double totalN)
	{
		List<Integer> countList = candSet.getCount();
		
		Iterator<Integer> iterCount = candSet.getCount().listIterator();
		Iterator<Set<String>> iterItems = candSet.getItems().listIterator();		
//		int i=0;
		for (Iterator<Integer> iter = countList.listIterator(); iter.hasNext(); ) {
		    
			Double supportCount = (double)iter.next();
			Set<String> set = iterItems.next();
		    if (supportCount/totalN < minThreshold) {
		        iter.remove();
		        iterItems.remove();
		     
		        
		    }
		}
	}
	
	/**
	 * converts transaction record to set of items
	 * @param transaction record
	 * @param header title
	 * @return a set
	 */
	public Set<String> convertsTableRecordToSet(Transactions record,Header title)
	{
		Set<String> set = new TreeSet<String>();
		
		for(int i=0;i<title.getNumberOfItems();i++)
		{
			if(record.getItemAtIndex(i)==1)
			{
				set.add(title.getItemAtIndex(i));
			}
		}
		return set;
	}
	
	public Integer getSupportCount(TransactionTable table,String item)
	{
		Set<String> set = new TreeSet<String>();
		set.add(item);
	
		return getSupportCount(table, set);
	}
	
	public Integer getSupportCount(TransactionTable table,Set<String> itemSet)
	{
		int supportCount = 0;
		CommonLogics cl = new CommonLogics();
	
		for(int i=0;i<table.numberOfRecords();i++)
		{
			
			if(
			cl.containsSet(table.getItemSetAtRow(i), itemSet)
			)
				supportCount++;
			
		}
		return supportCount;
	}
	
	public Integer getSupportCount(FrequentAndCandidateItemSet mainSet,String item)
	{
		int supportCount = 0;
		
		for(int i=0;i<mainSet.size();i++)
		{
			
			if(
			mainSet.setAtIndex(i).contains(item)
			)
				supportCount++;
			
		}
		return supportCount;
	}
	
	public double getConfidence(TransactionTable table,Set<String> numeratorSet,Set<String> denominatorSet)
	{
		Set<String> temp = new TreeSet<String>();
		new CommonLogics().copySet(numeratorSet, temp);
		
		double numSupport = getSupportCount(table, temp);
		temp.removeAll(denominatorSet);
		double denSupport = getSupportCount(table,temp);
		return numSupport/denSupport;
	}
	
	/**
	 * Returns List of Antecedants in Association rules list
	 * @param list of association Rules
	 * @return
	 */
	public List<Set<String>> getListAntecedants(List<AssociationRule> list)
	{
		List<Set<String>> retList = new ArrayList<Set<String>>();
		
		for(AssociationRule rule:list)
		{
			retList.add(rule.getAntecedant());
		}
		return retList;
	}
	
	/**
	 * Returns List of Consequents in Association rules list
	 * @param list of association Rules
	 * @return
	 */
	public List<Set<String>> getListConsequents(List<AssociationRule> list)
	{
		List<Set<String>> retList = new ArrayList<Set<String>>();
		
		for(AssociationRule rule:list)
		{
			retList.add(rule.getConsequent());
		}
		return retList;
	}
	
	/**
	 * true if it matches , false if it doesn't match
	 * @param list
	 * @param rule
	 * @return true or false
	 */
	public boolean compareAssociationRules(List<AssociationRule> list,AssociationRule rule)
	{
		CommonLogics cl = new CommonLogics();
		for(AssociationRule temp:list)
		{
			if(cl.compareSets(temp.getAntecedant(), rule.getAntecedant()) && cl.compareSets(temp.getConsequent(), rule.getConsequent()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public Set<String> convertsFreqAndCand1ToSet(FrequentAndCandidateItemSet freq1)
	{
		Set<String> set = new TreeSet<String>();
		
		for(int i=0;i<freq1.size();i++)
		{
	//		Object[] a = freq1.setAtIndex(i).toArray();
	//		String str = a[0].toString();
			
			set.add(freq1.setAtIndex(i).toArray()[0].toString());
		}
		
		return set;
	}
}
