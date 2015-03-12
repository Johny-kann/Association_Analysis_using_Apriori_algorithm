package com.data_mining.file_readers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.data_mining.constants.FilesList;
import com.data_mining.logic.CommonLogics;
import com.data_mining.view.console.Outputs;

public class PropertiesConfig {
	
	public static void assignInputFiles()
	{
		Properties prop = new Properties();
		try
        {
           prop.load(new FileReader(FilesList.CONFIG_FILE));
           
        //   prop.load(is);
       
           CommonLogics.settingFiles(prop.getProperty("Attributes"), FilesList.ATTRIBUTES_FILES);
           CommonLogics.settingFiles(prop.getProperty("Train-records"), FilesList.RECORD_FILES);
                    
           CommonLogics.settingFiles(prop.getProperty("Out-Result"), FilesList.WRITE_RESULT);
           CommonLogics.settingFiles(prop.getProperty("Out-Items-Rules"), FilesList.WRITE_ITEMSET);
            
            
        } catch (FileNotFoundException e)
        {
         	Outputs.printToConsole("File not Found ");
            e.printStackTrace();
            
        } catch (IOException e)
        {
        	System.out.println("Input Output Exception - Johny");
            e.printStackTrace();
        }
		

		
	}

	public static String readPort()
	{
		Properties prop = new Properties();
		try
        {
            // the configuration file name
                        
            InputStream is = PropertiesConfig.class.getClass().getResourceAsStream("/config/url.properties");

            // load the properties file
            prop.load(is);

            String server = 
            		prop.getProperty("port");
            
            
            // get the value for app.vendor key and if the
            // key is not available return Code Java as
            // the default value
            return server;
            
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
		

		return null;
	}

}
