package positionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;

import dataExtractor.ClassDetails;
//import positionService.Position;
//import model.Node.NodeInfo;
import positionService.Position.Positions;
import positionService.Position.generator;

public class Node {
	String classType = "STRUCT";
	String FileType = "FILE";
	String PackageType = "PACKAGE";
	
	NodeInfo tree;
	
	public static class NodeInfo{
		@Expose(serialize = true)
		String name;
		@Expose(serialize = true)
		String url;
		@Expose(serialize = true)
		public ArrayList<String> commits;
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
	}
	public NodeInfo create(HashMap<String,ClassDetails> items,String repositoryName,String repositoryBranch) {
		tree = new NodeInfo();
		tree.name = repositoryName;
		tree.url = "";
		tree.branch = repositoryBranch;
		tree.childrenMap = new HashMap<String,NodeInfo>();
		tree.children = new ArrayList<NodeInfo>();
		tree.position = new HashMap<String, Float>();
		tree.position.put("x",(float)0);
		tree.position.put("y", (float)0);
		
		for(Map.Entry<String, ClassDetails> entity : items.entrySet()) {
//			System.out.println(entity.getKey());
			NodeInfo currentNode = tree;
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
				
				
				currentNode = currentNode.childrenMap.get(pathlist[i]);
			}
			String fileName = getFileName(pathlist);
			
			NodeInfo fileNodeObj = new NodeInfo();
			fileNodeObj.name = fileName;
			fileNodeObj.type = FileType;
			fileNodeObj.childrenMap = new HashMap<String,NodeInfo>();
			fileNodeObj.children = new ArrayList<NodeInfo>();
			fileNodeObj.numberOfAttributes = entity.getValue().numberAttributes;
			
			currentNode.childrenMap.put(fileName,fileNodeObj);
			NodeInfo fileNode = currentNode.childrenMap.get(fileName);
			
			String className = getClassName(pathlist);
			NodeInfo classNodeObj = new NodeInfo();
			classNodeObj.name = className;
			classNodeObj.type = classType;
			classNodeObj.path = getpath(entity.getKey(),pathlist[0]);
			classNodeObj.children = new ArrayList<NodeInfo>();
			classNodeObj.childrenMap = new HashMap<String, NodeInfo>();
			classNodeObj.line = entity.getValue().line;
			classNodeObj.numberOfAttributes = entity.getValue().numberAttributes;
			classNodeObj.numberOfMethods = entity.getValue().numberMethods;
			classNodeObj.numberOfLines = entity.getValue().numberLines;
			
			fileNode.childrenMap.put(fileName, classNodeObj);
//			System.out.println(fileNode.childrenMap.get(fileName).name);
		}
		
		GenerateChildList(tree,"https://"+repositoryName+"/{{TYPE}}/"+repositoryBranch);
		GenerateChildrenPosition(tree);
		return tree;
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
	
	void GenerateChildList(NodeInfo n,String parentPath) {
		for ( Map.Entry<String, NodeInfo> child : n.childrenMap.entrySet()) {
			String[] noderes = getNodeURL(child.getValue(), parentPath);
			String baseName = noderes[0];
			String nodeURL = noderes[1];
			child.getValue().url = nodeURL;
			n.children.add(child.getValue());
			if((child.getValue().childrenMap.size()) > 0 ){
				GenerateChildList(child.getValue(),baseName);
			}
		}
	}
	
	String[] getNodeURL(NodeInfo node , String parentPath){
		if (node.type == classType) {
			String formatted = parentPath.replace("{{TYPE}}", "blob");
//			+"#L"+ node.line
			return new String[] {formatted,formatted};
		}
		String raw;
		if (node.name.length() > 0) {
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
