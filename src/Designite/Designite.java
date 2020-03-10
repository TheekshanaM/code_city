package Designite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import Designite.SourceModel.*;

/**
 * 
 * This is the start of the bug finder
 */
public class Designite {
	public static Map<String, HashMap<String, HashSet<String>>> designiteRun(String inputpath) throws IOException {
		String inputPath = inputpath;
		SM_Type sm_type =  new SM_Type();

		InputArgs argsObj =  new InputArgs(inputPath);
		SM_Project project = new SM_Project(argsObj); //get source name and path for save result - argsObj


		project.parse();
		project.resolve();
		project.computeMetrics();
		project.detectCodeSmells();
		
		
		
		return sm_type.getBuglist();
	}
}
