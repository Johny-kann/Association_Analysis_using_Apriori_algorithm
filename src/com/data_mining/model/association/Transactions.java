package com.data_mining.model.association;

import java.util.ArrayList;
import java.util.List;

public class Transactions {

	private List<Integer> record;
	
	public Transactions()
	{
		record = new ArrayList<Integer>();
	}
	
	public Transactions(List<Integer> rec)
	{
		record = rec;
	}
	
	public void addItem(int num)
	{
		record.add(num);
	}
	
	public Integer getItemAtIndex(int index)
	{
		return record.get(index);
	}
	
	public void setItemAtIndex(int index, Integer element)
	{
		record.set(index, element);
	}

	public List<Integer> getRecord() {
		return record;
	}

	public void setRecord(List<Integer> record) {
		this.record = record;
	}

	public Integer getNumberOfItems() {
		// TODO Auto-generated method stub
		return this.record.size();
	}
}
