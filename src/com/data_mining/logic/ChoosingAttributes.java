package com.data_mining.logic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.data_mining.constants.FilesList;
import com.data_mining.constants.Notations;
import com.data_mining.file_readers.TextFileWriter;
import com.data_mining.logs.TrainingLog;
import com.data_mining.model.association.FrequentAndCandidateItemSet;
import com.data_mining.model.association.ItemSet;
import com.data_mining.model.association.TransactionTable;
import com.data_mining.view.console.Outputs;


/**
 * @author Janakiraman
 *
 *Class with methods to choose the Result Set
 */
public class ChoosingAttributes {

	public FrequentAndCandidateItemSet getResultSet(TransactionTable table, double minSupport)
	{
		boolean run = true;
		AssociationLogics chs = new AssociationLogics();
		FrequentAndCandidateItemSet freq = null;
		FrequentAndCandidateItemSet result = new FrequentAndCandidateItemSet(ItemSet.ResultSet);
		StringBuffer str = new StringBuffer();
		str.append("Generation By "+Notations.GENERATION_TYPE);
		str.append(System.lineSeparator());
		
		for(int k=1;run;k++)
		{
			freq = chs.generateFrequentSetForK(table, minSupport, k, freq,str);
			result.addAllItem(freq);
			
			if(freq.size()==0)
			{
				run=false;
			}
		}
		
		TextFileWriter tfw = new TextFileWriter();
		tfw.writeFile(str.toString(), FilesList.WRITE_RESULT);
		
		return result;
	}
}
