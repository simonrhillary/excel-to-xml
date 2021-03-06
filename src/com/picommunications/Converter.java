package com.picommunications;


import org.apache.commons.io.FilenameUtils;
import org.apache.poi.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
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
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class Converter extends JFrame{

/**
 *   Class Description:
 *
 *
 *   @version 20, June, 2016
 *   @author simonrhillary
 */

//=============================================
//  Instance Variables
//=============================================

    private static int converterID;
    private File inputFile;
    private String inputFilePath;
    private String outputFilePath;
    String[] headers;
    private List<String> options;
    String outputFileName;
    private String inputFileName;
    private StreamResult streamResult;
    private StringWriter writer;
    private InputStream input;
    private Workbook workbook;
    private Sheet sheet;
    private String finalString;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder = null;
    private Document doc;

    private TransformerFactory tf;
    private Transformer transformer;
    private DOMSource source;



//=============================================
//  Constructors
//=============================================

    public Converter(File f, String ifp, String ofp) throws IOException, InvalidFormatException{
    converterID = System.identityHashCode(this);
    this.inputFile = f;
    this.inputFilePath = ifp;
    this.outputFilePath = ofp;
    this.options = new ArrayList<>();
        this.inputFileName = f.getName();
    this.outputFileName = FilenameUtils.removeExtension(f.getName()) + ".xml";

        setFile(f);
    }

    Converter(String ifp, String ofp) throws IOException, InvalidFormatException{
        converterID = System.identityHashCode(this);
        this.inputFilePath = ifp;
        this.outputFilePath = ofp;
        this.options = new ArrayList<>();
        this.inputFile = getFileAt(inputFilePath);
        setFile(ifp);
        this.outputFileName = FilenameUtils.removeExtension(this.inputFile.getName()) + ".xml";
        this.inputFileName = this.inputFile.getName();
    }

     Converter(File f, String ofp, String[] opts) throws IOException, InvalidFormatException{
        converterID = System.identityHashCode(this);
        this.inputFile = f;
        this.outputFilePath = ofp;
        this.options = new ArrayList<>();
        this.inputFileName = this.inputFile.getName();
        this.outputFileName = FilenameUtils.removeExtension(f.getName()) + ".xml";
        setOptions(opts);
        setFile(f);
    }

     Converter(File f, String ofp) throws IOException, InvalidFormatException{
        converterID = System.identityHashCode(this);
        this.inputFile = f;
        this.outputFilePath = ofp;
        this.options = new ArrayList<>();
        this.outputFileName = FilenameUtils.removeExtension(f.getName()) + ".xml";
        this.inputFileName = f.getName();
        setFile(f);
    }


     Converter(File f) throws IOException, InvalidFormatException{
        converterID = System.identityHashCode(this);
        this.inputFile = f;
        this.inputFilePath = f.getAbsolutePath();
        this.outputFilePath = ConverterFactory.defaultOutputFilePath;
        this.options = new ArrayList<>();
        this.outputFileName = FilenameUtils.removeExtension(f.getName()) + ".xml";
        setFile(f);
    }

     Converter(){
        converterID = System.identityHashCode(this);
    }


//=============================================
//  Methods
//=============================================

     void convert() throws ParserConfigurationException, IOException, TransformerException {
        System.out.println("[" + converterID + "]" + " Inside Convert Method");
        //Create new XML Document
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        doc = builder.newDocument();
        Element rootElement = doc.createElement("data-set");
        doc.appendChild(rootElement);
        //count number of programmes in dataset
        System.out.println("Number of Programmes to be Imported: " + sheet.getLastRowNum());
        populateHeaders(sheet);
        //add top level elements corresponding to number of programmes
        int count = (sheet.getLastRowNum());
        boolean add = false;
        for (int i = 0; i <= count; i++) {
            Element programmeElement = doc.createElement("programme");
            if(!isRowEmpty(sheet.getRow(i)) && !rowContainsHeaders(sheet.getRow(i))){
                for (int j = 0; j < options.size(); j++) {
                    String tagName = options.get(j).replaceAll("\\s+", "");
                    String s;
                    Element newElement = doc.createElement(tagName);
                    s = new DataFormatter().formatCellValue(sheet.getRow(i).getCell(j));
                    if(tagName.equals("date")){
                        try{
                        Date date = new SimpleDateFormat("MM/dd/yy").parse(s);
                        s = new SimpleDateFormat("dd/MM/yy").format(date);
                    }catch(ParseException pe){
                            System.out.println("Date could not be parsed!");
                            pe.printStackTrace();
                        }
                    }
                    newElement.setTextContent(s);
                    System.out.println("Row : " + (i) + " Cell : " + j + " Value : " + (s));
                    programmeElement.appendChild(newElement);
                    System.out.println("Appended " + newElement.getTagName() + " "
                            + newElement.getNodeValue() + " to " + newElement.getParentNode().getNodeName());
                }
                add = true;
            }
            if(add) {
                rootElement.appendChild(programmeElement);
            }
        }
        //Output the XML document
        tf = TransformerFactory.newInstance();
        transformer = tf.newTransformer();
        //set output formatting
        tf.setAttribute("indent-number", 4);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        source = new DOMSource(doc);
        //create a StringWriter for the output
        writer = new StringWriter();
        streamResult  = new StreamResult(writer);
        transformer.transform(source, streamResult);
        StringBuffer sb = writer.getBuffer();
        finalString = sb.toString();
        System.out.println("END OF CONVERT METHOD");
    }

    public int getConverterID(){
        return  converterID;
    }

    void setInputFilePath(String ifp){
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

    void setOutputDirectory(String ofp) {
        this.outputFilePath = ofp;
    }

    String getOutputDirectory(){
        return outputFilePath;
    }


    public List<String> getOptions(){
        return options;
    }

    private File getFileAt(String path){
        return new File(path);
    }

    private void populateHeaders(Sheet sheet){
        int firstRow = 0;
        while(isRowEmpty(sheet.getRow(firstRow))){
            firstRow++;
        }
        headers = new String[sheet.getRow(firstRow).getLastCellNum()];
        for (int i = 0; i < sheet.getRow(firstRow).getLastCellNum(); i++){
            headers[i] = sheet.getRow(firstRow).getCell(i).toString().toLowerCase(); // add element name to headerNames
        }

    }

    private void setOptions(String[] s){
        Collections.addAll(options, s);
    }

    String getFinalOutputString(){
        return finalString;
    }

    private void setFile(File f) throws IOException, InvalidFormatException, POIXMLException {
        input = new FileInputStream(new File(f.getAbsolutePath()));
        workbook = new XSSFWorkbook(input);
        sheet = workbook.getSheetAt(0);
        populateHeaders(sheet);
    }

    private void setFile(String filepath) throws IOException, InvalidFormatException, POIXMLException{
        input = new FileInputStream(new File(filepath));
        workbook = WorkbookFactory.create(input);
        sheet = workbook.getSheetAt(0);
        populateHeaders(sheet);
    }

    void outputResult() throws TransformerException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(outputFilePath + "/");
        stringBuilder.append(outputFileName);
        StreamResult result = new StreamResult((new File(stringBuilder.toString())));
        transformer.transform(source, result);
    }

    private static boolean isRowEmpty(Row row){
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                return false;
        }
        return true;
    }

    public int getEmptyRows(Sheet sheet){
        int res = 0;
        int c = sheet.getLastRowNum();
        for(int i = 0; i < c; i++){
            if(isRowEmpty(sheet.getRow(i))){
                res++;
            }
        }
        return res;
    }

    private boolean rowContainsHeaders(Row row){
        int c = row.getLastCellNum();
        for(int i = 0; i<c; i++){
            for (String header : headers) {
                if (row.getCell(i).toString().equalsIgnoreCase(header)) {
                    return true;
                }
            }
        }
        return false;
    }
}
