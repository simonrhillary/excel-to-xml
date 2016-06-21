package com.picommunications;


import java.io.File;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.*;

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

    static int converterID;
    File inputFile;
    String inputFilePath;
    String outputFilePath;
    ArrayList<Boolean> options;

//=============================================
//  Constructors
//=============================================

    public Converter(int id, File f, String ifp, String ofp, ArrayList<Boolean> opts){
    this.converterID = System.identityHashCode(this);
    this.inputFile = f;
    this.inputFilePath = ifp;
    this.outputFilePath = ofp;
    this.options = (ArrayList<Boolean>)opts.clone();
    }

    public Converter(File f, String ofp, ArrayList<Boolean> opts){
        //to be populated
        this.inputFile = f;
        this.outputFilePath = ofp;
        this.options = (ArrayList<Boolean>)opts.clone();
    }

    public Converter(){

    }


//=============================================
//  Methods
//=============================================

    public int getConverterID(){
        return  0;//converterID;
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

    public void setOptions(ArrayList<Boolean>  o){
        this.options = o;
    }

    public ArrayList<Boolean> getOptions(){
        return options;
    }

    public void convert(){
        System.out.println("[" + converterID + "]" + " Inside Convert Method");
    }
}
