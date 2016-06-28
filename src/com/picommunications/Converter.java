package com.picommunications;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Converter extends JFrame{

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
 * 2 Debug Conversion Method
 * 3 User Interface creation
 */


//=============================================
//  Instance Variables
//=============================================

    private static int converterID;
    private File inputFile;
    private String inputFilePath;
    private String outputFilePath;
    private ArrayList<Boolean> options;
    public String[] headers;

//=============================================
//  Constructors
//=============================================

    public Converter(File f, String ifp, String ofp, ArrayList<Boolean> opts){
    converterID = System.identityHashCode(this);

    this.inputFile = f;
    this.inputFilePath = ifp;
    this.outputFilePath = ofp;
    this.options = (ArrayList<Boolean>)opts.clone();
    }

    public Converter(String ifp, String ofp, ArrayList<Boolean> opts){
        converterID = System.identityHashCode(this);

        this.inputFilePath = ifp;
        this.outputFilePath = ofp;
        this.options = (ArrayList<Boolean>)opts.clone();
        this.inputFile = getFileAt(inputFilePath);
    }

    public Converter(File f, String ofp, ArrayList<Boolean> opts){
        converterID = System.identityHashCode(this);

        this.inputFile = f;
        this.outputFilePath = ofp;
        this.options = (ArrayList<Boolean>)opts.clone();
    }

    public Converter(String ifp, String ofp){
        converterID = System.identityHashCode(this);

        this.inputFilePath = ifp;
        this.outputFilePath = ofp;
        this.inputFile = getFileAt(inputFilePath);
        //TODO
        // default options
    }

    public Converter(File f){
        converterID = System.identityHashCode(this);

        this.inputFile = f;
        // TODO: 21/06/2016
        //default options
    }

    public Converter(){
        converterID = System.identityHashCode(this);
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
            InputStream input = new FileInputStream(new File("RBTest2.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);

            //count number of programmes in dataset
            System.out.println("Number of Programmes to be Imported: " + spreadsheet.getLastRowNum());
            String[] headerNames = new String[spreadsheet.getRow(0).getLastCellNum()];
            for (int i = 0; i < spreadsheet.getRow(0).getLastCellNum(); i++){
                headerNames[i] = spreadsheet.getRow(0).getCell(i).toString().toLowerCase(); // add element name to headerNames
            }

            //add top level elements corresponding to number of programmes
            for(int i = 0; i < spreadsheet.getLastRowNum(); i++){
                Element programmeElement = doc.createElement("programme");
                for (int j = 0; j < headerNames.length; j++) {
                    Element newElement = doc.createElement(headerNames[j]);
                    newElement.setTextContent(String.valueOf(spreadsheet.getRow(i+1).getCell(j)).toString());
                    System.out.println("Row : " + i+1 + " Cell : " + j + " Value : " + String.valueOf(spreadsheet.getRow(i+1).getCell(j)).toString());
                    programmeElement.appendChild(newElement); //append node to programme
                    System.out.println("Appended " + newElement.getTagName() + " " + newElement.getNodeValue() + " to " + newElement.getParentNode().getNodeName());
                }
                rootElement.appendChild(programmeElement);
            }

            //Output the XML document
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            //set output formatting
            tf.setAttribute("indent-number" , 4);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(String.valueOf(System.out)));// change this to output directory
            transformer.transform(source, result);

            //output result to directory


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
