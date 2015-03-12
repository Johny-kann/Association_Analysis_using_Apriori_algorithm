package com.data_mining.file_readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.data_mining.constants.FilesList;
import com.data_mining.logs.TrainingLog;

/**
 * Reads a text file and gives output based on the desired format
 *
 * @author Janakiraman
 *
 */
public class TextFileReader {

	private BufferedReader br;
	private InputStream file;
	private String location;
	
	public TextFileReader(String fileLoc)
	{
		location = fileLoc;
		resetReader();
		
		
	}
	
	/**
	 * Resets the Reader back to the initial position
	 */
	private void resetReader()
	{
	//	InputStream file = FileReader.class.getClass().getResourceAsStream(location);
		FileReader file = null;
		try {
			TrainingLog.mainLogs.info("Going to read file "+location);
			file = new FileReader(location);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			TrainingLog.trainLogs.log(Level.SEVERE, "Cant read the input file under location "+location);
			e.printStackTrace();
		}
		br = new BufferedReader(
				//		new FileReader("tester.txt")
						file
						);
	}
	
	/**
	 * Returns the text file content via a List of String
	 * @return List with each line as a string
	 */
	public List<String> getOutput()
	{
		String sCurrentLine;
		List<String> lines = new ArrayList<String>();
		resetReader();
		try {
			
			while ((sCurrentLine = br.readLine()) != null) {
				
				lines.add(sCurrentLine);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lines;
		
	}
	
	/**
	 * Closes the Buffer for the file
	 */
	public void closeStream()
	{
		try {
			if (br != null)br.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
