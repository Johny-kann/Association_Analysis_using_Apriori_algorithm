package com.data_mining.main;

import com.data_mining.controller.MainController;
import com.data_mining.logic.CommonLogics;


public class InitProgram 
{

	public static void main(String[] args) 
	{
		
	CommonLogics.assignInitValues(args);
		
	MainController mc = new MainController();
	mc.loadTable();
	mc.start();
	
	}
	
}
