package com.picommunications;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Converter {

/**
 *   Class Description:
 *
 *
 *   @version 20, June, 2016
 *   @author simonrhillary
 */


/**
 *  TODO
 * 1 finish constructors
 * 2  conversion method development
 *
 */


//=============================================
//  Instance Variables
//=============================================

    private static int converterID;
    private File inputFile;
    private String inputFilePath;
    private String outputFilePath;
    private ArrayList<Boolean> options;

//=============================================
//  Constructors
//=============================================

    public Converter(File f, String ifp, String ofp, ArrayList<Boolean> opts){
    this.converterID = System.identityHashCode(this);

    this.inputFile = f;
    this.inputFilePath = ifp;
    this.outputFilePath = ofp;
    this.options = (ArrayList<Boolean>)opts.clone();
    }

    public Converter(String ifp, String ofp, ArrayList<Boolean> opts){
        this.converterID = System.identityHashCode(this);

        this.inputFilePath = ifp;
        this.outputFilePath = ofp;
        this.options = (ArrayList<Boolean>)opts.clone();
        this.inputFile = getFileAt(inputFilePath);
    }

    public Converter(File f, String ofp, ArrayList<Boolean> opts){
        this.converterID = System.identityHashCode(this);

        this.inputFile = f;
        this.outputFilePath = ofp;
        this.options = (ArrayList<Boolean>)opts.clone();
    }

    public Converter(String ifp, String ofp){
        this.converterID = System.identityHashCode(this);

        this.inputFilePath = ifp;
        this.outputFilePath = ofp;
        this.inputFile = getFileAt(inputFilePath);
        //TODO
        // default options
    }

    public Converter(File f){
        this.converterID = System.identityHashCode(this);

        this.inputFile = f;
        // TODO: 21/06/2016
        //default options
    }

    public Converter(){
        this.converterID = System.identityHashCode(this);
    }


//=============================================
//  Methods
//=============================================

    public void convert(){
        System.out.println("[" + converterID + "]" + " Inside Convert Method");
        try {
            //Create new XML Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement = doc.createElement("data-set");
            doc.appendChild(rootElement);

            //Obtain the input spreadsheet
            InputStream input = new FileInputStream(new File("/Users/simonrhillary/Developer/workspace/ExcelToXML/src/RBTest1.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);

            //count number of programmes in dataset
            System.out.println("Number of Programmes to be Imported: " + spreadsheet.getLastRowNum());

            //add top level elements corresponding to number of programmes
            Element programmeElement = doc.createElement("programme");
            for(int i = 0; i< spreadsheet.getLastRowNum() ; i++){
                rootElement.appendChild(programmeElement);
                System.out.println("Appended " + programmeElement.getTagName() + " " + (i + 1) + " to " + rootElement.getTagName());
            }

            //read header names to generate elements
            String[] headerNames = new String[spreadsheet.getRow(0).getLastCellNum()];
            for( int i = 0; i < spreadsheet.getRow(0).getLastCellNum(); i++ ) {
                headerNames[i] = spreadsheet.getRow(0).getCell(i).toString().toLowerCase();
                System.out.println("Added " + headerNames[i].toString() + " to list");
            }



        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public int getConverterID(){
        return  converterID;
    }

    public void setInputFilepath(String ifp){
        this.inputFilePath = ifp;
    }

    public String getInputFilePath(){
        return inputFilePath;
    }

    public void setInputFile(File f){
        this.inputFile = f;
    }

    public File getInputFile(){
        return this.inputFile;
    }

    public void setOutputDirectory(String ofp) {
        this.outputFilePath = ofp;
    }

    public String getOutputDirectory(){
        return outputFilePath;
    }

    public void setOptions(ArrayList<Boolean> o){
        this.options = o;
    }

    public ArrayList<Boolean> getOptions(){
        return options;
    }

    public File getFileAt(String path){
        File f = new File(path);
        return f;
    }
}
