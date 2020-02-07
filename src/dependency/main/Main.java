package dependency.main;

import java.io.File;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


import dependency.core.Architecture;
import dependency.exception.DCLException;

public class Main {
	
	public Collection<String> mainRunner(String line)throws CoreException, IOException, DCLException, InterruptedException {
		String root = line;
	
		
		File filePath = new File("").getAbsoluteFile();
		String path = filePath.getAbsolutePath();
		String projectPath = "";
		
		File f = new File(root);
		
		if(!f.exists()){
			File f2 = new File(path+"/"+root);
			if(!f2.exists()){
				File f3 = new File(path);
				if(!f3.exists()){
					System.out.println("Wrong Specified Path!");
				}else{
					projectPath = path;
				}
			}else{
				projectPath = path+"/"+root;
			}
		}else{
			projectPath = root;
		}
		
		Collection<String> listDep = new ArrayList<String>();
		if(!projectPath.equals("")){
			Architecture architecture = new Architecture(projectPath);
			listDep = architecture.getDependencies();
			
		}
		
		Collection<String> depList =  new ArrayList<String>();
		for (String string : listDep) {
			if(string.contains(",extend,") || string.contains(",implement,")) {
				depList.add(string);
			}
		}
		
		
		return depList;
	}
	
	public HashMap<String,String> getExtendedlist(Collection<String> depList){
		HashMap<String,String> extendlist = new HashMap<String,String>();
		for (String string : depList) {
			if (string.contains(",extend,")) {
				String[] parts = string.split(",extend,");
				String part1 = parts[0]; 
				String part2 = parts[1];
				extendlist.put(part1, part2);
			}
		}
		
		return extendlist; 
	}

	
	public Multimap<String,String> getImplementedlist(Collection<String> depList){
		Multimap<String, String> implementlist = ArrayListMultimap.create();
		for (String string : depList) {
			if (string.contains(",implement,")) {
				String[] parts = string.split(",implement,");
				String part1 = parts[0]; 
				String part2 = parts[1];
				implementlist.put(part1, part2);
			}
		}
		
		return implementlist; 
	}
	 
}
