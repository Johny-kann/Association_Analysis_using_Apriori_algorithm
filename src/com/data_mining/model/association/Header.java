package com.data_mining.model.association;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.data_mining.logic.CommonLogics;

public class Header {

	private List<String> items;
	
	public Header()
	{
		items = new ArrayList<String>();
	}
	
	public Header(List<String> itemList)
	{
		items = itemList;
	}

	public List<String> getItems() {
		return items;
	}
	
	
	
	public void addItem(String item)
	{
		items.add(item);
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public String getItemAtIndex(int index)
	{
		return items.get(index);
	}
	
	public Integer getNumberOfItems()
	{
		return items.size();
	}

	public Integer getItemIndex(String itemName) {
	
		return items.indexOf(itemName);
	}

}
