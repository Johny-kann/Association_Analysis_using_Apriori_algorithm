package com.data_mining.model.attributes_records;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import com.data_mining.logic.CommonLogics;

public class OrderedClassSet {
	
	private Map<String,Integer> orderedClasses;
	
	public OrderedClassSet(Map<String,Integer> map)
	{
		orderedClasses = map;
		
		Set<String> keys = map.keySet();
		
		for(String key:keys)
		{
			orderedClasses.put(key, map.get(key));
		}
	
	}
	
	public Set<String> getClassesAlone()
	{
		return orderedClasses.keySet();
	}

	public Map<String, Integer> getOrderedClasses() {
		return orderedClasses;
	}
	
	public String getClassAtIndex(int index)
	{
		return orderedClasses.keySet().toArray()[index].toString();
	}
	

}
