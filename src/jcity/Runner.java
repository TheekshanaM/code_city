package jcity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.Multimap;

import dependency.exception.DCLException;
import dependency.main.Main;
import jcity.util.ResultWriter;
import repositoryDataAnalyser.Helper;

public class Runner {

	public HashMap<String, JCity> runMethod(String repo){ 

		try {
			String path = Helper.localPath;
			boolean useJars = false;
			
			HashMap<String, JCity> newList = new HashMap<String, JCity>();
		
			new CK().calculate(path, useJars, result -> {
				try {
				    String file = result.getFile();
				    
				    BufferedReader reader = new BufferedReader(new FileReader(file));
				    int loc = 0;
				    while (reader.readLine() != null) loc++;
				    reader.close();
				    
				    file = file.replace((path), repo);
				    file = file.replace('\\', '/');
				    
				    String classPath = result.getClassName();
				    String className = classPath;
				    if(className.lastIndexOf('.') != -1) {
				    	className = className.substring(className.lastIndexOf('.')+1);
				    }
//				    int loc = result.getLoc();
				    int methods = result.getNumberOfMethods();
				    int variables = result.getVariablesQty();
				 
				    
				    JCity ckList = new JCity(file, classPath, methods, loc, variables);
				    newList.put(file+"?"+className, ckList);
				    
//				    System.out.println(ckList.toString());
	 
				    
				   
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
			
			Main main = new Main();
			
			Collection<String> depLsit =  main.mainRunner(path);
			
			HashMap<String, String> extendList = main.getExtendedlist(depLsit);
			Multimap<String, String> implementList = main.getImplementedlist(depLsit);
			
			for (HashMap.Entry<String, String> item : extendList.entrySet()) {
				String className =  item.getKey();
//				if(className.lastIndexOf('.') != -1) {
//			    	className = className.substring(className.lastIndexOf('.')+1);
//			    }
				String superClass = item.getValue();
				
				
				
				for (HashMap.Entry<String, JCity> jcity : newList.entrySet()) {
					JCity classBuilding = jcity.getValue();
					
					if (classBuilding.getClassName().equalsIgnoreCase(className)) {
						if(superClass.lastIndexOf('.') != -1) {
							superClass = superClass.substring(superClass.lastIndexOf('.')+1);
							superClass = superClass.trim();
					    }
						classBuilding.setSuperClass(superClass);
						
					}	
					
					
				}
			}
			
			
			for (HashMap.Entry<String, JCity> jcity : newList.entrySet()) {
				JCity classBuilding = jcity.getValue();
				ArrayList<String> interfaceList = new ArrayList<String>();
				for (Map.Entry<String, String> item : implementList.entries()) {
					String className = item.getKey();
//					if(className.lastIndexOf('.') != -1) {
//				    	className = className.substring(className.lastIndexOf('.')+1);
//				    }
					String interfaceName = item.getValue();
					
					if (classBuilding.getClassName().equalsIgnoreCase(className)) {
						if(interfaceName.lastIndexOf('.') != -1) {
							interfaceName = interfaceName.substring(interfaceName.lastIndexOf('.')+1);
							interfaceName = interfaceName.trim();
					    }
						interfaceList.add(interfaceName);	
					}	
						
				}
				classBuilding.setInterfaceList(interfaceList);
			}
			
			for (String string : depLsit) {
				System.out.println(string);
			}
		
			for (HashMap.Entry<String, JCity> jcity : newList.entrySet()) {
				JCity classBuilding = jcity.getValue();
				
				System.out.println(classBuilding.toString());
					
			}	
			return newList;
		} catch (Exception e2) {
			return null;
		}
		
	}
}
