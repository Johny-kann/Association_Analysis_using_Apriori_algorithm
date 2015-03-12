package com.data_mining.model.association;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.data_mining.logic.AssociationLogics;
import com.data_mining.logic.CommonLogics;

public class AssociationList {

	List<AssociationRule> listAssocsRule;
	
	public AssociationList()
	{
		listAssocsRule = new ArrayList<AssociationRule>();
	}
	
	public void addRule(AssociationRule rule)
	{
		AssociationLogics al = new AssociationLogics();
		if(!al.compareAssociationRules(listAssocsRule, rule))
		{
			listAssocsRule.add(rule);
		}
	}
	
	public void addAllRules(AssociationList list)
	{
		AssociationLogics al = new AssociationLogics();
		for(AssociationRule rule:list.getListAssocsRule())
		{
			if(!al.compareAssociationRules(listAssocsRule, rule))
			{
				listAssocsRule.add(rule);
			}
		}
	}
	
	public AssociationRule getRuleAtIndex(int index)
	{
		return listAssocsRule.get(index);
	}
	
	public Integer size()
	{
		return listAssocsRule.size();
	}

	public List<AssociationRule> getListAssocsRule() {
		return listAssocsRule;
	}
	
	public List<Set<String>> getListAntecedants()
	{
		return new AssociationLogics().getListAntecedants(listAssocsRule);
	}
	
	public List<Set<String>> getListConsequents()
	{
		return new AssociationLogics().getListConsequents(listAssocsRule);
	}
}
