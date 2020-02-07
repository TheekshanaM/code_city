package com.github.mauricioaniche.ck;

import com.github.mauricioaniche.ck.util.ResultWriter;

import dataExtractor.ClassDetails;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Runner {

	public HashMap<String, ClassDetails> runMethod(String repo) {
		try {
			String path = "E:\\Document\\Theekshana\\Research\\gitrepo";
			boolean useJars = false;
//			if(args.length == 2)
//				useJars = Boolean.parseBoolean(args[1]);
			
//			ResultWriter writer = new ResultWriter("class.csv");
			
		
			HashMap<String, ClassDetails> newList = new HashMap<String, ClassDetails>();
		
			new CK().calculate(path, useJars, result -> {
				try {
//				    writer.printResultNuwan(result);
				    String file = result.getFile();
				    BufferedReader reader = new BufferedReader(new FileReader(file));
				    int loc = 0;
				    while (reader.readLine() != null) loc++;
				    reader.close();
				    
				    file = file.replace((path), repo);
				    file = file.replace('\\', '/');
				    
				    String className = result.getClassName();
				    if(className.lastIndexOf('.') != -1) {
				    	className = className.substring(className.lastIndexOf('.')+1);
				    }
				    
//				    int loc = result.getLoc();
				    int methods = result.getNumberOfMethods();
				    int variables = result.getVariablesQty();
				    
//				    CKList ckList = new CKList(file, className, methods, loc, variables);
				    ClassDetails ckList = new ClassDetails(variables, methods, loc);
				    newList.put(file+"?"+className, ckList);
				    System.out.println(file+"?"+className);
	 
				    
				   
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
			
//			System.out.println(newList);
			return newList;
			
//			writer.flushAndClose();
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}

		
	}
}
