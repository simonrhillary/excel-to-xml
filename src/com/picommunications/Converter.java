package com.picommunications;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
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

    public static int converterID;
    public File inputFile;
    public String inputFilePath;
    public String outputFilePath;
    public String[] headers;
    public List<String> options;
    public String outputFileName;
    StreamResult streamResult;
    StringWriter writer;
    InputStream input;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    String finalString;
    DocumentBuilderFactory factory;
    DocumentBuilder builder = null;
    Document doc;

    TransformerFactory tf;
    Transformer transformer;
    DOMSource source;



//=============================================
//  Constructors
//=============================================

    public Converter(File f, String ifp, String ofp) throws IOException, InvalidFormatException{
    converterID = System.identityHashCode(this);
    this.inputFile = f;
    this.inputFilePath = ifp;
    this.outputFilePath = ofp;
    this.options = new ArrayList<String>();
    this.outputFileName = FilenameUtils.removeExtension(f.getName()) + ".xml";
        setFile(f);
    }

    public Converter(String ifp, String ofp) throws IOException, InvalidFormatException{
        converterID = System.identityHashCode(this);
        this.inputFilePath = ifp;
        this.outputFilePath = ofp;
        this.options = new ArrayList<String>();
        this.inputFile = getFileAt(inputFilePath);
        setFile(ifp);
        this.outputFileName = FilenameUtils.removeExtension(this.inputFile.getName()) + ".xml";
    }

    public Converter(File f, String ofp, String[] opts) throws IOException, InvalidFormatException{
        converterID = System.identityHashCode(this);
        this.inputFile = f;
        this.outputFilePath = ofp;
        this.options = new ArrayList<String>();
        this.outputFileName = FilenameUtils.removeExtension(f.getName()) + ".xml";
        setOptions(opts);
        setFile(f);
    }

    public Converter(File f, String ofp) throws IOException, InvalidFormatException{
        converterID = System.identityHashCode(this);
        this.inputFile = f;
        this.outputFilePath = ofp;
        this.options = new ArrayList<String>();
        this.outputFileName = FilenameUtils.removeExtension(f.getName()) + ".xml";
        setFile(f);
    }


    public Converter(File f) throws IOException, InvalidFormatException{
        converterID = System.identityHashCode(this);
        this.inputFile = f;
        this.inputFilePath = f.getAbsolutePath();
        this.outputFilePath = ConverterFactory.defaultOutputFilePath;
        this.options = new ArrayList<String>();
        this.outputFileName = FilenameUtils.removeExtension(f.getName()) + ".xml";
        setFile(f);
    }

    public Converter(){
        converterID = System.identityHashCode(this);
    }


//=============================================
//  Methods
//=============================================

    public void convert() throws IOException, ParserConfigurationException, IOException, TransformerException {
        System.out.println("[" + converterID + "]" + " Inside Convert Method");
        //Create new XML Document
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        doc = builder.newDocument();
        Element rootElement = doc.createElement("data-set");
        doc.appendChild(rootElement);

        //count number of programmes in dataset
        System.out.println("Number of Programmes to be Imported: " + sheet.getLastRowNum());
        String[] headerNames = new String[sheet.getRow(0).getLastCellNum()];
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            headerNames[i] = sheet.getRow(0).getCell(i).toString().toLowerCase(); // add element name to headerNames
        }

        //add top level elements corresponding to number of programmes
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Element programmeElement = doc.createElement("programme");
            for (int j = 0; j < options.size(); j++) {
                if (1 == 1) { //if options contains the element header, add it to the document
                    Element newElement = doc.createElement(options.get(j));
                    newElement.setTextContent(String.valueOf(sheet.getRow(i + 1).getCell(j)));
                    System.out.println("Row : " + i + 1 + " Cell : " + j + " Value : " + String.valueOf(sheet.getRow(i + 1).getCell(j)));
                    programmeElement.appendChild(newElement); //append node to programme
                    System.out.println("Appended " + newElement.getTagName() + " "
                            + newElement.getNodeValue() + " to " + newElement.getParentNode().getNodeName());
                }
            }
            rootElement.appendChild(programmeElement);
        }

        //Output the XML document
        tf = TransformerFactory.newInstance();
        transformer = tf.newTransformer();
        //set output formatting
        tf.setAttribute("indent-number", 4);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        source = new DOMSource(doc);
        streamResult = new StreamResult(new File(String.valueOf(System.out)));// change this to output directory
        transformer.transform(source, streamResult);

        //create a StringWriter for the output
        writer = new StringWriter();
        StreamResult res = new StreamResult(writer);
        transformer.transform(source, res);
        StringBuffer sb = writer.getBuffer();
        finalString = sb.toString();
        System.out.println("END OF CONVERT METHOD");
    }



    public int getConverterID(){
        return  converterID;
    }

    public void setInputFilePath(String ifp){
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


    public List<String> getOptions(){
        return options;
    }

    public File getFileAt(String path){
        File f = new File(path);
        return f;
    }

    public void populateHeaders(XSSFSheet sheet){
        headers = new String[sheet.getRow(0).getLastCellNum()];
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++){
            headers[i] = sheet.getRow(0).getCell(i).toString().toLowerCase(); // add element name to headerNames
        }

    }

    public void setOptions(String[] s){
        for(int i = 0; i < s.length; i++){
            options.add(s[i]);
        }
    }

    public String getFinalOutputString(){
        return finalString;
    }

    public void setFile(File f) throws FileNotFoundException, IOException, InvalidFormatException, POIXMLException {
        input = new FileInputStream(new File(f.getAbsolutePath()));
        workbook = new XSSFWorkbook(input);
        sheet = workbook.getSheetAt(0);
        populateHeaders(sheet);
    }

    public void setFile(String filepath) throws IOException, InvalidFormatException, POIXMLException{
        input = new FileInputStream(new File(filepath));
        workbook = new XSSFWorkbook(input);
        sheet = workbook.getSheetAt(0);
        populateHeaders(sheet);
    }

    void outputResult() throws TransformerException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(outputFilePath + "/");
        stringBuilder.append(FilenameUtils.removeExtension(inputFile.getName()));
        StreamResult result = new StreamResult((new File(stringBuilder.toString())));
        transformer.transform(source, result);
    }
}
