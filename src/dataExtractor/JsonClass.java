package dataExtractor;

import java.util.ArrayList;

import positionService.Node;

public class JsonClass {
	public ArrayList<String> commits;
	public Node.NodeInfo jsonCity;
	
	public JsonClass(ArrayList<String> a,Node.NodeInfo b) {
		commits = a;
		jsonCity = b;
	}
	
//	public JsonClass getObj() {
//		return
//	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub "{\"phonetype\":\"N95\",\"cat\":\"WP\"}"
		return "{\"commits\":"+commits+",\"jsonCity\":"+jsonCity+"}";
	}
}
