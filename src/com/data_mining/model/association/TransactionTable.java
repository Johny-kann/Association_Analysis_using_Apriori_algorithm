package com.data_mining.model.association;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import com.data_mining.logic.AssociationLogics;
import com.data_mining.logic.CommonLogics;
import com.data_mining.logs.TrainingLog;

public class TransactionTable {

	private Header header;
	private List<Transactions> data;
	
	public TransactionTable()
	{
		header = new Header();
		data = new ArrayList<Transactions>();
	}

	public Header getHeader() {
		return header;
	}
	
	public Set<String> getItemHeaderSet()
	{
		return new CommonLogics().convertsListStringToSet(header.getItems());
	}
	
	public Transactions getRecordAtIndex(int index)
	{
		return data.get(index);
	}
	
	public Set<String> getItemSetAtRow(int index)
	{
		AssociationLogics al = new AssociationLogics();
		return al.convertsTableRecordToSet(data.get(index), header);
	}

	public String getItemNameAtIndex(int index)
	{
		return header.getItemAtIndex(index);
	}
	
	public Integer getItemAtTransaction(int itemIndex,int recordIndex)
	{
		return data.get(recordIndex).getItemAtIndex(itemIndex);
	}
	
	public Integer getItemAtTransaction(String itemName,int recordIndex)
	{
		return data.get(recordIndex).getItemAtIndex(header.getItemIndex(itemName));
	}
	
	public void setHeader(Header header) {
		this.header = header;
	}

	public void setHeader(List<String> header) {
		this.header = new Header(header);
	}
	
	public List<Transactions> getData() {
		return data;
	}

	public void setData(List<Transactions> data) {
		this.data = data;
	}
	
	public void setRecordAtIndex(List<Integer> record, int index)
	{
		if(header.getNumberOfItems()==record.size())
		{
			data.set(index, new Transactions(record));
			
		}
		else
		{
			TrainingLog.mainLogs.log(Level.SEVERE, "Size of header and data doesn't match "+header.getNumberOfItems()+" "
					+record.size());
			throw new IndexOutOfBoundsException();
		}
	}
	
	public void addHeader(String item)
	{
		header.addItem(item);
		
	}
	
	public void addRecord(List<Integer> record)
	{
		if(header.getNumberOfItems()==record.size())
		{
			data.add(new Transactions(record));
			
		}
		else
		{
			TrainingLog.mainLogs.log(Level.SEVERE, "Size of header and data doesn't match "+header.getNumberOfItems()+" "
					+record.size());
			throw new IndexOutOfBoundsException();
		}
	}
	
	public Integer numberOfItems()
	{
		return header.getNumberOfItems();
	}
	
	public Integer numberOfRecords()
	{
		return data.size();
	}
}
