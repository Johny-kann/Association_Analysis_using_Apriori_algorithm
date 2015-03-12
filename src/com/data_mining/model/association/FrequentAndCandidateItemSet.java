package com.data_mining.model.association;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.data_mining.logic.CommonLogics;
import com.data_mining.logs.TrainingLog;

public class FrequentAndCandidateItemSet {

	private List<Set<String>> items;
	private List<Integer> count;
	private int setNumber;
	private ItemSet setType;
	
	public FrequentAndCandidateItemSet(ItemSet type)
	{
		this.setType = type;
		this.items = new ArrayList<Set<String>>();
		this.count = new ArrayList<Integer>();
	}
	
	public FrequentAndCandidateItemSet(int setNumber,ItemSet type)
	{
		this.items = new ArrayList<Set<String>>();
		this.count = new ArrayList<Integer>();
		this.setNumber = setNumber;
		this.setType = type;
	}
	
	
	public void addAllItem(List<Set<String>> listItem,List<Integer> count)
	{
		CommonLogics cl = new CommonLogics();
		
		int i=0;
		for(Set<String> set:listItem)
		{
			if(!cl.compareFromListOfSets(items, set))
			{
				items.add(set);
				this.count.add(count.get(i));
			}
			i++;
		}
	}
	
	public void addAllItem(FrequentAndCandidateItemSet freq)
	{
		CommonLogics cl = new CommonLogics();
		
		int i=0;
		for(Set<String> set:freq.getItems())
		{
			if(!cl.compareFromListOfSets(items, set))
			{
				items.add(set);
				this.count.add(freq.getCount().get(i)
						);
			}
			i++;
		}
	}
	
	public void addItem(String item,int count)
	{
		CommonLogics cl = new CommonLogics();
		Set<String> set = new TreeSet<String>();
		set.add(item);
		if(!cl.compareFromListOfSets(items, set))
		{

			if(setNumber==1)
			{
			
			items.add(set);
			this.count.add(count);
			}
			else
			{
			TrainingLog.mainLogs.info("Set number is not 1");
			}
		}
	}
	
	public void addItem(Set<String> itemset,int count)
	{
		CommonLogics cl = new CommonLogics();
		
		if(!cl.compareFromListOfSets(items, itemset))
		{

			if(setNumber==itemset.size())
			{
			items.add(itemset);
			this.count.add(count);
			}
			else
			{
			TrainingLog.mainLogs.info("Set number is"+ setNumber+" not equal"+itemset.size()+setType);
			}
		}

	}
	
	public Set<String> setAtIndex(Integer index)
	{
		return items.get(index);
	}
	
	public void removeSetAtIndex(int index)
	{
		items.remove(index);
		count.remove(index);
	}
	
	public Set<String> setAtIndexCopy(Integer index)
	{
		Set<String> dest = new TreeSet<String>();
		new CommonLogics().copySet(items.get(index), dest);
		return dest;
	}
	
	public Integer countAtIndex(Integer index)
	{
		return count.get(index);
	}
	
	public Integer size()
	{
		return count.size();
	}

	public ItemSet getSetType() {
		return setType;
	}

	public void setSetType(ItemSet setType) {
		this.setType = setType;
	}

	public int getSetNumber() {
		return setNumber;
	}

	public List<Set<String>> getItems() {
		return items;
	}

	public List<Integer> getCount() {
		return count;
	}
}
