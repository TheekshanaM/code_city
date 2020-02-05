package com.github.mauricioaniche.ck;

public class CKList {
	private String filePath;
    private String className;
    private int numberOfMethods;
    private int linesOfCodes;
    private int numberOfAttributes;
    
	public CKList(String filePath, String className, int numberOfMethods, int linesOfCodes, int numberOfAttributes) {
		super();
		this.filePath = filePath;
		this.className = className;
		this.numberOfMethods = numberOfMethods;
		this.linesOfCodes = linesOfCodes;
		this.numberOfAttributes = numberOfAttributes;
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
                + "]";
    }
    
}



		
    



	