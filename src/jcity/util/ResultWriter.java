package jcity.util;

import org.apache.commons.csv.CSVFormat;

import org.apache.commons.csv.CSVPrinter;

import jcity.JClassResult;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter {


    private static final String[] NEW_CLASS_HEADER = {"file","class","totalMetods","loc","varaiblesQty"};
    
    private CSVPrinter classPrinter;
   
    public ResultWriter(String classFile) throws IOException{
    	FileWriter classOut = new FileWriter(classFile);
        this.classPrinter = new CSVPrinter(classOut, CSVFormat.DEFAULT.withHeader(NEW_CLASS_HEADER));
    }
   

    public void printResult(JClassResult result) throws IOException{
    	this.classPrinter.printRecord(result.getFile(), result.getClassName(), result.getNumberOfMethods(), result.getLoc(), result.getVariablesQty());

    }

    public void flushAndClose() throws IOException {
        this.classPrinter.flush();
        this.classPrinter.close();

    }
}
