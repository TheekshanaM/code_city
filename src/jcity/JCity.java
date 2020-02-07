package jcity;

import java.util.ArrayList;

public class JCity {
	private String filePath;
    private String className;
    private int line;
    private int numberOfMethods;
    private int linesOfCodes;
    private int numberOfAttributes;
    private String superClass;
    private ArrayList<String> interfaceList;
    

	public JCity(String filePath, String className, int numberOfMethods, int linesOfCodes, int numberOfAttributes) {
		super();
		this.filePath = filePath;
		this.className = className;
		this.line = 1;
		this.numberOfMethods = numberOfMethods;
		this.linesOfCodes = linesOfCodes;
		this.numberOfAttributes = numberOfAttributes;
	}



	public ArrayList<String> getInterfaceList() {
		return interfaceList;
	}



	public void setInterfaceList(ArrayList<String> interfaceList) {
		this.interfaceList = interfaceList;
	}



	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getNumberOfMethods() {
		return numberOfMethods;
	}

	public void setNumberOfMethods(int numberOfMethods) {
		this.numberOfMethods = numberOfMethods;
	}

	public int getLinesOfCodes() {
		return linesOfCodes;
	}

	public void setLinesOfCodes(int linesOfCodes) {
		this.linesOfCodes = linesOfCodes;
	}

	public int getNumberOfAttributes() {
		return numberOfAttributes;
	}

	public void setNumberOfAttributes(int numberOfAttributes) {
		this.numberOfAttributes = numberOfAttributes;
	}
    
	@Override
    public String toString() {
        return "CKList [filePath=" + filePath + ", className=" + className + ", methods=" + numberOfMethods + ", linesOfCodes=" + linesOfCodes + ", attributes=" + numberOfAttributes
                + ", superClass=" + superClass + ", intefaceList=" + interfaceList + "]";
    }



	public int getLine() {
		return line;
	}



	public void setLine(int line) {
		this.line = line;
	}
    
}



		
    



	