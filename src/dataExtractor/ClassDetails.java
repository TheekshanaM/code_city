package dataExtractor;

public class ClassDetails {
	public int line;
	public String className;
	public int numberAttributes;
	public int numberMethods;
	public int numberLines;
	
	public ClassDetails(int variables,int methods,int loc){
		line = 1;
//		className = a;
		numberAttributes = variables;
		numberMethods = methods;
		numberLines = loc;
	}
}
