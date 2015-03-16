package com.data_mining.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.data_mining.constants.FilesList;
import com.data_mining.constants.Notations;
import com.data_mining.constants.ValueConstants;
import com.data_mining.logs.TrainingLog;
import com.data_mining.model.association.FrequentAndCandidateItemSet;

/**
 * @author Janakiraman
 *Basic logic functions like sort, remove etx
 */
public class CommonLogics {

	
	
	public static void assignInitValues(String args[])
	{
		for(String str:args)
		{
			
		if(str.contains("minsup"))
		{
			ValueConstants.MIN_SUPPORT = Double.parseDouble(str.substring(7, str.length()));
		}
		
		else if(str.contains("minconf"))
		{
			ValueConstants.MIN_CONFIDENCE = Double.parseDouble(str.substring(8, str.length()));
		}
		
		else if(str.contains("CONFIG"))
		{
		
			FilesList.CONFIG_FILE = str.substring(7, str.length());
			TrainingLog.mainLogs.info("Getting config file "+FilesList.CONFIG_FILE);
		}
		
		else if(str.contains("gen-method"))
		{
			if(str.substring(11, str.length()).equals("F1"))
			{
				Notations.GENERATION_TYPE = Notations.F1;
			}
			else
			{
				Notations.GENERATION_TYPE = Notations.FK_1;
			}
		}
		
		else if(str.contains("algorithm"))
		{
			if(str.substring(10,str.length()).equals("original"))
			{
				Notations.ALG_MOD = false;
			}
			else
			{
				Notations.ALG_MOD = true;
			}
		}
		
		}
		new TrainingLog();
		
	}
	
	public static String settingFiles(String name,String filesConst)
	{
		TrainingLog.mainLogs.info("Setting file "+name+" file constant "+filesConst);
		if(!( name == null || name.isEmpty()))
		{
			filesConst = name;
		}
		return filesConst;
	}
	
	public String conditionGeneratorDiscrete(String name,String condition)
	{
		return name+Notations.DISCRETE_EQUALITY+condition;
	}
	
	public Map<String,Integer> sortMapValues(Map<String,Integer> map)
	{

		Set<String> keys = map.keySet();

		List<String> index = new ArrayList<String>(keys
				);
		List<Integer> objs = getListFromMap(map);
		
		for(int i=0;i<objs.size();i++)
		{
			for(int j=i;j<objs.size();j++)
			{
				if(objs.get(i)>objs.get(j))
				{
					Collections.swap(objs, i, j);
					Collections.swap(index, i, j);
				}
			}
		}
		
	Map<String,Integer> mapp = new LinkedHashMap<String, Integer>();
	
	for(int i=0;i<objs.size();i++)
	{
		mapp.put(index.get(i), objs.get(i));
	}
	
	return mapp;
	}
	
	public List<Integer> getListFromMap(Map<String, Integer> map)
	{
		Set<String> keys = map.keySet();
		
		List<Integer> str = new ArrayList<Integer>();
		
		for(String key:keys)
		{
			str.add(map.get(key));
		}
		
		return str;
	}
	
	
	public List<Integer> convertsListStringToListInt(List<String> list)
	{
		List<Integer> listInt = new ArrayList<Integer>();
		
		for(String str:list)
		{
			listInt.add(
					Integer.parseInt(str)
					);
		}
		
		return listInt;
	}
	
	public Set<String> convertsListStringToSet(List<String> list)
	{
		Set<String> set = new TreeSet<String>();
		
		for(String str:list)
		{
			set.add(str);
		}
		
		return set;
	}
	
	
	

	
	
	
	
	public Integer getValueofMapAtIndex(Map<String,Integer> map,int index)
	{
		Set<String> keys = map.keySet();
		return map.get(keys.toArray()[index]);
		
	}
	
	
	
	
	
	
	
	
	
	 
	
	
	
	
	
	/**
	 * @param Node condition
	 * @return Node value
	 */
	public String getNodeValueFromCondition(String str)
	{
		String temp;
		if(str.contains(" "))
		{
			int index = str.indexOf(" ");
			temp = str.substring(index+1, str.length());
		}
		else if(str.contains("<"))
		{
			int index = str.indexOf("<");
			temp = str.substring(index+1, str.length());
		}
		else if(str.contains(">="))
		{
			int index = str.indexOf(">=");
			temp = str.substring(index+2, str.length());
		}
		else
		{
			temp = Notations.ERROR_IN_COND;
		}
		return temp;
	}
	
	/**
	 * @param Node condition
	 * @return node name
	 */
	public String getNodeNameFromCondition(String str)
	{
		String temp;
		if(str.contains(" "))
		{
			int index = str.indexOf(" ");
			temp = str.substring(0,index);
		}
		else if(str.contains("<"))
		{
			int index = str.indexOf("<");
			temp = str.substring(0,index);
		}
		else if(str.contains(">="))
		{
			int index = str.indexOf(">=");
			temp = str.substring(0,index);
		}
		else
		{
			temp = Notations.ERROR_IN_COND;
		}
		return temp;
	}
	
	
	
	
	
	
	/**
	 * based on this value the records are assigned to the children
	 * @param node condition of parent
	 * @return child split information
	 */
	public String getDecisionForChildRecordSender(String str)
	{
		String temp;
		if(str.contains(" "))
		{
			int index = str.indexOf(" ");
			temp = Notations.DISCRETE_EQUAL;
		}
		else if(str.contains("<"))
		{
			int index = str.indexOf("<");
			temp = Notations.CNTS_LEFT;
		}
		else if(str.contains(">="))
		{
			int index = str.indexOf(">=");
			temp = Notations.CNTS_RIGHT;
		}
		else
		{
			temp = Notations.ERROR_IN_COND;
		}
		return temp;
	}

	
	/**
	 * If true is given condition chosen in less than and if false is given condition chosen is greater than
	 * @param attributeName
	 * @param value
	 * @param true or false
	 * @return rule condition
	 */
	public String conditionGeneratorCnts(String attributeName, Double str,
			boolean b) {
		// TODO Auto-generated method stub
		if(b==false)
		{
			return attributeName+Notations.CNTNS_GREATER_THAN+str;
		}
		else
		{
			return attributeName+Notations.CNTNS_LESS_THAN+str;
		}
		
	}
	
	public int getNameFromListUsingIndex(List<String> names,String name)
	{
		return names.indexOf(name);
	}
	
	
	/**
	 * Returns true if mainset contains or equal to subset
	 * @param mainSet
	 * @param subSet
	 * @return
	 */
	public boolean containsSet(Set<String> mainSet,Set<String> subSet)
	{

		if(mainSet.containsAll(subSet))
			return true;
		
		return false;
	}
	
	public boolean containsItem(Set<String> mainSet,String item)
	{
		
		if(mainSet.contains(item))
			return true;

		return false;
	}

	
	/**
	 * Gives false if two sets are not equal and true if they are
	 * @param set1
	 * @param set2
	 * @return
	 */
	public boolean compareSets(Set<String> set1, Set<String> set2)
	{
		Iterator<String> itr1 = set1.iterator();
		Iterator<String> itr2 = set2.iterator();
		
		String str1 = null;
		String str2 = null;
		
		while(itr1.hasNext() || itr2.hasNext())
		{
			try
			{
			str1 = itr1.next();
			str2 = itr2.next();
			}catch(NoSuchElementException ne)
			{
				TrainingLog.mainLogs.info("No such Element exception");
				return false;
			}
		
			
			if(! (str1.
					
					equalsIgnoreCase(str2)))
			{
				return false;
			}
		}
		
		
		return true;
	}
	
	
	/**
	 *  Gives false if two sets are not equal and true if they are
	 * @param list
	 * @param set
	 * @return
	 */
	public boolean compareFromListOfSets(List<Set<String>> list,Set<String> set)
	{
		for(Set<String> temp:list)
		{
			if(compareSets(temp, set))
			{
		
				return true;
			}
		}
		
	
		return false;
	}

	public void removeListBasedOnIndexes(List mainList,List<Integer> indexes)
	{
	Collections.sort(indexes, Collections.reverseOrder());
	
	for (int i : indexes)
	    mainList.remove(i);
	
	}
	
	public void removeFromFreqAndCandSet(FrequentAndCandidateItemSet mainList,List<Integer> indexes)
	{
		Collections.sort(indexes, Collections.reverseOrder());
		
		for (int i : indexes)
		    mainList.removeSetAtIndex(i);
		
	}
	
	public void copySet(Set<String> source,Set<String> destination)
	{
		for(String items:source)
		{
		//	String item = items;
			destination.add(items);
		}
	}
	
	public boolean initialSetMatch(Set<String> set1,Set<String> set2,int matchNumber)
	{
		int match = 0;
		
		Iterator<String> itr1 = set1.iterator();
		Iterator<String> itr2 = set2.iterator();
		
		while(itr1.hasNext())
		{
			if(itr1.next().equalsIgnoreCase(itr2.next()))
			{		match++;
			}
			else
			{	break;
			}
			
		}
		
	//	System.out.println(match);
		if(match>=matchNumber)
			return true;
		else
			return false;
	}
		
}
