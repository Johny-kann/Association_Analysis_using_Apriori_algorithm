package com.data_mining.file_readers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;

import com.data_mining.constants.FilesList;

/**
 * @author Janakiraman
 * 
 * Writes the output to a file
 *
 */
public class TextFileWriter {

	PrintWriter bufWrite;
	
	public TextFileWriter()
	{
		
	}
	
	public TextFileWriter(String location)
	{
		try {
		    bufWrite = new PrintWriter(new BufferedWriter(
		    	//	new OutputStreamWriter(
		    //      new FileOutputStream(location), "utf-8")
		    		new FileWriter(location,true)
		    ));
		} catch (IOException ex) {
			  // report
				ex.printStackTrace();
			}
	}
	public void writeFile(String content,String location)
	{
				 
		Writer writer = null;

		
				
		try {
		    writer = new BufferedWriter(
		    	//	new OutputStreamWriter(
		    //      new FileOutputStream(location), "utf-8")
		    		new FileWriter(location)
		    );
		    writer.write(content);
		
		} catch (IOException ex) {
		  // report
			ex.printStackTrace();
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}
	
	}
	
	public void appendFile(String content,String location)
	{
				 
	//	try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(location, true)))) {
		    bufWrite.println(content);
//		}catch (IOException e) {
//		    System.err.println(e);
//		}
	}
}
