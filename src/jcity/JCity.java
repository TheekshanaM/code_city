package jcity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class JCity {
	private String filePath;
    private String className;
    private int line;
    private int numberOfMethods;
    private int linesOfCodes;
    private int numberOfAttributes;
    private String superClass;
    private ArrayList<String> interfaceList;
    private boolean fillDiffStatus;
    private HashMap<String, HashSet<String>> methodBugList;
    private boolean bugStatus;
    

	public JCity(String filePath, String className, int numberOfMethods, int linesOfCodes, int numberOfAttributes) {
		super();
		this.filePath = filePath;
		this.className = className;
		this.line = 1;
		this.numberOfMethods = numberOfMethods;
		this.linesOfCodes = linesOfCodes;
		this.numberOfAttributes = numberOfAttributes;
		this.fillDiffStatus= false;
		this.bugStatus = false;
	}

	public HashMap<String, HashSet<String>> getMethodBugList() {
		return methodBugList;
	}

	public boolean isBugStatus() {
		return bugStatus;
	}

	public void setBugStatus(boolean bugStatus) {
		this.bugStatus = bugStatus;
	}

	public void setMethodBugList(HashMap<String, HashSet<String>> methodBugList) {
		this.methodBugList = methodBugList;
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



	public boolean isFillDiffStatus() {
		return fillDiffStatus;
	}



	public void setFillDiffStatus(boolean fillDiffStatus) {
		this.fillDiffStatus = fillDiffStatus;
	}
    
}



		
    



	