package positionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.gson.annotations.Expose;

import jcity.JCity;
//import dataExtractor.ClassDetails;
//import positionService.Position;
//import model.Node.NodeInfo;
import positionService.Position.Positions;
import positionService.Position.generator;

public class Node {
	String classType = "CLASS";
	String FileType = "FILE";
	String PackageType = "PACKAGE";
	
	NodeInfo tree;
	Structure strTree;
	
	public static class Structure{
		@Expose(serialize = true)
		String name;
		@Expose(serialize = true)
		boolean toggled;
		@Expose(serialize = false)
		HashMap<String, Structure> childrenMap;
		@Expose(serialize = true)
		ArrayList<Structure> children;
	}
	public static class NodeInfo{
		@Expose(serialize = true)
		String name;
		@Expose(serialize = true)
		String url;
		@Expose(serialize = true)
		public ArrayList<String> commits;
		@Expose(serialize = true)
		public Structure structure;
		@Expose(serialize = true)
		public String path;
		@Expose(serialize = false)
		String branch;
		@Expose(serialize = true)
		String type;
		@Expose(serialize = true)
		float width;
		@Expose(serialize = true)
		float depth;
		@Expose(serialize = false)
		Positions positions;
		@Expose(serialize = true)
		HashMap<String, Float> position;
		@Expose(serialize = true)
		int numberOfLines;
		@Expose(serialize = true)
		int numberOfMethods;
		@Expose(serialize = true)
		int numberOfAttributes;
		@Expose(serialize = true)
		ArrayList<NodeInfo> children;
		@Expose(serialize = false)
		int line;
		@Expose(serialize = false)
		HashMap<String,NodeInfo> childrenMap;
		@Expose(serialize = true)
		String superClass;
		@Expose(serialize = true)
		ArrayList<String> interfaces;
		@Expose(serialize = true)
		ArrayList<String> supperClassList;
		@Expose(serialize = true)
		ArrayList<String> interfacesList;
		@Expose(serialize = true)
		boolean fillDiffStatus;
		@Expose(serialize = true)
		HashMap<String, HashSet<String>> methodBugList;
		@Expose(serialize = true)
		boolean bugStatus;
	}
	public NodeInfo create(HashMap<String,JCity> items,String repositoryName,String repositoryBranch,String repositary, String commit) {
		tree = new NodeInfo();
		tree.name = repositoryName;
		tree.url = "";
		tree.branch = repositoryBranch;
		tree.childrenMap = new HashMap<String,NodeInfo>();
		tree.children = new ArrayList<NodeInfo>();
		tree.position = new HashMap<String, Float>();
		tree.position.put("x",(float)0);
		tree.position.put("y", (float)0);
		tree.supperClassList = new ArrayList<String>();
		tree.supperClassList.add("Select a class");
		tree.interfacesList = new ArrayList<String>();
		tree.interfacesList.add("Select a Interface");
		
		strTree = new Structure();
		strTree.name = "start";
		strTree.childrenMap = new HashMap<String, Node.Structure>();
		strTree.children = new ArrayList<Node.Structure>();
		
		for(Map.Entry<String, JCity> entity : items.entrySet()) {
			
			String tempSuperClass =entity.getValue().getSuperClass();
			ArrayList<String> tempIntefaces = entity.getValue().getInterfaceList(); 
			if(tempSuperClass!= null) {
				addSuppeClass(tempSuperClass);
			}
			if(tempIntefaces.size() !=0) {
				addInterface(tempIntefaces);
			}
//			System.out.println(entity.getKey());
			NodeInfo currentNode = tree;
			Structure currentItem = strTree;
			String[] pathlist= entity.getKey().split("/");
			
			for(int i=0; i<pathlist.length-1 ; i++) {
				//check
				if(currentNode.childrenMap.get(pathlist[i]) == null) {
					NodeInfo childNode = new NodeInfo();
					childNode.name = pathlist[i];
					childNode.type = PackageType;
					childNode.childrenMap = new HashMap<String,NodeInfo>();
					childNode.children = new ArrayList<NodeInfo>();
					currentNode.childrenMap.put(pathlist[i],childNode);
				}
				if(currentItem.childrenMap.get(pathlist[i]) == null){
					Structure childItem = new Structure();
					childItem.name = pathlist[i];
					if(i != (pathlist.length-2)) {
						childItem.toggled = true;
					}
					childItem.childrenMap = new HashMap<String, Node.Structure>();
					childItem.children = new ArrayList<Node.Structure>();
					currentItem.childrenMap.put(pathlist[i], childItem);
				}
				
				currentNode = currentNode.childrenMap.get(pathlist[i]);
				currentItem = currentItem.childrenMap.get(pathlist[i]);
			}
			String fileName = getFileName(pathlist);
			
			NodeInfo fileNodeObj = new NodeInfo();
			fileNodeObj.name = fileName;
			fileNodeObj.type = FileType;
			fileNodeObj.childrenMap = new HashMap<String,NodeInfo>();
			fileNodeObj.children = new ArrayList<NodeInfo>();
			fileNodeObj.numberOfAttributes = entity.getValue().getNumberOfAttributes();
			
			Structure fileStructure = new Structure();
			fileStructure.name = fileName;
			fileStructure.childrenMap = new HashMap<String, Node.Structure>();
//			fileStructure.children = new ArrayList<Node.Structure>();
			
			currentNode.childrenMap.put(fileName,fileNodeObj);
			currentItem.childrenMap.put(fileName,fileStructure);
			NodeInfo fileNode = currentNode.childrenMap.get(fileName);
//			Structure structueNode = currentItem.childrenMap.get(fileName);
			
			String className = getClassName(pathlist);
			NodeInfo classNodeObj = new NodeInfo();
			classNodeObj.name = className;
			classNodeObj.type = classType;
			classNodeObj.path = getpath(entity.getKey(),pathlist[0]);
			classNodeObj.children = new ArrayList<NodeInfo>();
			classNodeObj.childrenMap = new HashMap<String, NodeInfo>();
			classNodeObj.line = entity.getValue().getLine();
			classNodeObj.numberOfAttributes = entity.getValue().getNumberOfAttributes();
			classNodeObj.numberOfMethods = entity.getValue().getNumberOfMethods();
			classNodeObj.numberOfLines = entity.getValue().getLinesOfCodes();
			classNodeObj.superClass = entity.getValue().getSuperClass();
			classNodeObj.interfaces = entity.getValue().getInterfaceList();
			classNodeObj.fillDiffStatus = entity.getValue().isFillDiffStatus();
			classNodeObj.bugStatus = entity.getValue().isBugStatus();
			classNodeObj.methodBugList = entity.getValue().getMethodBugList();
			
//			Structure classStructure = new Structure();
//			classStructure.name = className;
//			classStructure.childrenMap = new HashMap<String, Node.Structure>();
//			classStructure.children = new ArrayList<Node.Structure>();
			
			fileNode.childrenMap.put(fileName, classNodeObj);
//			structueNode.childrenMap.put(fileName, classStructure);
//			System.out.println(fileNode.childrenMap.get(fileName).name);
		}
		
		GenerateChildList(tree,repositoryName+"/{{TYPE}}/"+commit,repositary);
		structureChildList(strTree);
		GenerateChildrenPosition(tree);
		tree.structure = strTree;
		return tree;
	}
	
	void addInterface(ArrayList<String> interfaceList) {
		boolean status = false;
		for(String item:interfaceList) {
			for(String data:tree.interfacesList) {
				if(item.equals(data)) {
					status =true;
				}
				
			}
			if(status == false) {
				tree.interfacesList.add(item);break;
			}
//			if(tree.interfacesList.size()==0) {
////				for(String item2:interfaceList) {
//					tree.interfacesList.add(item);
////				}
//			}
			status = false;
		}
		
		
	}

	void addSuppeClass(String value) {
		boolean status = false;
		for(String item:tree.supperClassList) {
			if(item.equals(value) ) {
				status =true;
			}
		}
		if(status == false) {
			tree.supperClassList.add(value);
		}
		status = false;
//		if(tree.supperClassList.size()==0) {
//			tree.supperClassList.add(value);
//		}
		
	}

	String getpath(String path, String folder) {
		return path.substring(0, path.indexOf('?')).replace(folder+"/", "");
	}
	String getFileName(String[] fullPath) {
		String value = fullPath[fullPath.length-1];
		value = value.substring(0, value.indexOf('?'));
		return value;
		
	}
	String getClassName(String[] fullPath) {
		String value = fullPath[fullPath.length-1];
		value = value.substring(value.indexOf('?')+1);
		return value;
		
	}
	
	void GenerateChildList(NodeInfo n,String parentPath, String repositary) {
		for ( Map.Entry<String, NodeInfo> child : n.childrenMap.entrySet()) {
			String[] noderes = getNodeURL(child.getValue(), parentPath, repositary);
			String baseName = noderes[0];
			String nodeURL = noderes[1];
			child.getValue().url = nodeURL;
			n.children.add(child.getValue());
			if((child.getValue().childrenMap.size()) > 0 ){
				GenerateChildList(child.getValue(),baseName,repositary);
			}
		}
	}
	void structureChildList(Structure n) {
		for ( Map.Entry<String, Structure> child : n.childrenMap.entrySet()) {
			n.children.add(child.getValue());
			if((child.getValue().childrenMap.size()) > 0 ){
				structureChildList(child.getValue());
			}
		}
	}
	
	String[] getNodeURL(NodeInfo node , String parentPath,String repositary){
		if (node.type == classType) {
			String formatted = parentPath.replace("{{TYPE}}", "blob");
//			+"#L"+ node.line
			return new String[] {formatted,formatted};
		}
		String raw;
//		System.out.println(node.name.equalsIgnoreCase(repositary) );
		if (node.name.length() > 0 && !node.name.equalsIgnoreCase(repositary)) {
			raw = parentPath+"/"+ node.name;
		} else {
			raw = parentPath;
		}
		String formatted;
		if (node.type == PackageType) {
			formatted = raw.replace("{{TYPE}}", "tree");
			return new String[] {raw,formatted};
		}
		formatted = raw.replace("{{TYPE}}", "blob");
		return new String[] {raw,formatted};
	}
	
	void GenerateChildrenPosition(NodeInfo n) {
		if( n.children.size() == 0) {
			n.width = n.numberOfAttributes + 1;
			n.depth = n.numberOfAttributes + 1;
			return;
		}

		Position positionObj = new Position(); 
		generator positionGenerator = positionObj.NewGenerator(n.children.size());
		for( NodeInfo child : n.children ){
			GenerateChildrenPosition(child);
			child.positions = positionObj.NextPosition(positionGenerator, child.width, child.depth);
			
		}

		Positions bounds = positionObj.GetBounds(positionGenerator);
		n.width = (int)bounds.x;
		n.depth = (int)bounds.y;
		
		for(NodeInfo child : n.children ){
			child.positions.x -= n.width / 2.0;
			child.positions.y -= n.depth / 2.0;
			child.position = new HashMap<String, Float>();
			child.position.put("x", child.positions.x);
			child.position.put("y", child.positions.y);
			
		}

		if( n.type == FileType ){
			n.width += (float)(n.numberOfAttributes);
			n.depth += (float)(n.numberOfAttributes);
		}
	}
}
