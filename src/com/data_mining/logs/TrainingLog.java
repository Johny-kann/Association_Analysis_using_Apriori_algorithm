package com.data_mining.logs;

import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.data_mining.constants.FilesList;
import com.data_mining.constants.Notations;

public class TrainingLog {

	public static final Logger accuracyLogs = Logger.getLogger(Notations.LOG_FOR_ACCURACY);
	public static final Logger trainLogs = Logger.getLogger(Notations.LOG_FOR_TRAINING);
	public static final Logger testLogs = Logger.getLogger(Notations.LOG_FOR_TESTING);
	public static final Logger mainLogs = Logger.getLogger(Notations.LOG_DOCUMENT);
	
	public TrainingLog()
	{
		try {
			SimpleFormatter formatter = new SimpleFormatter();
			
			accuracyLogs.addHandler(new FileHandler(FilesList.LOG_ACCURACY));
			trainLogs.addHandler(new FileHandler(FilesList.LOG_TRAIN));
			testLogs.addHandler(new FileHandler(FilesList.LOG_TRAIN));
			mainLogs.addHandler(new FileHandler(FilesList.LOG_DOC));
			
	//		  FileHandler fh = new FileHandler(FilesList.LOG_ACCURACY);
			trainLogs.getHandlers()[0].setFormatter(formatter);
			accuracyLogs.getHandlers()[0].setFormatter(formatter);
			testLogs.getHandlers()[0].setFormatter(formatter);
			mainLogs.getHandlers()[0].setFormatter(formatter);
			
			
			accuracyLogs.setUseParentHandlers(false);
			trainLogs.setUseParentHandlers(false);
			testLogs.setUseParentHandlers(false);
			mainLogs.setUseParentHandlers(false);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
