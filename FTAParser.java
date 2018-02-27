package com.FridgeAlert;
import java.io.File;

/**
 * 
 * @author phoenix
 *
 */
public class FTAParser {
	
	public FTAParser() {
		File directory = new File(System.getProperty("user.home")+"/FTApp/");
		if(!directory.exists()) {
		    try{
		        directory.mkdir();
		    } 
		    catch(SecurityException e) {
		    	
		    }
		}
		//TODO if saveFile is present, take it, otherwise make it
	}
}
