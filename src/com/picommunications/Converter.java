package com.picommunications;


import java.io.File;
import java.util.ArrayList;

public class Converter {

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

    static int converterID;
    File inputFile;
    String inputFilePath;
    String outputFilePath;
    ArrayList<Boolean> options;

//=============================================
//  Constructors
//=============================================

public Converter(int id, File f, String ifp, String ofp, ArrayList<Boolean> opts){
    this.converterID = id;
    this.inputFile = f;
    this.inputFilePath = ifp;
    this.outputFilePath = ofp;
    this.options = (ArrayList<Boolean>)opts.clone();
    }


//=============================================
//  Methods
//=============================================

    public int getConverterID(){
        return converterID;
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

    }
}
