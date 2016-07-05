package com.picommunications;


import org.apache.poi.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class ConverterFactory {

/**
 *   Class Description:
 *
 *
 *   @version 20, June, 2016
 *   @author simonrhillary
 */


//=============================================
//  Class Variables
//=============================================

     static String defaultOutputFilePath = System.getProperty("user.home");

//=============================================
//  Instance Variables
//=============================================

   private  ArrayList<Converter> converterInstances = new ArrayList<Converter>();

//=============================================
//  Methods
//=============================================

    public Converter getConverter(int i){
        return converterInstances.get(i);
    }

    public Converter getConverter(){
        return new Converter();
    }

    Converter getConverter(File f) throws IOException, InvalidFormatException, POIXMLException {
        Converter c = new Converter(f);
        c.setOutputDirectory(defaultOutputFilePath);
        converterInstances.add(c);
        return c;
    }

    public Converter getConverter(File f, String outputFilePath) throws IOException, InvalidFormatException, POIXMLException{
        Converter c = new Converter(f, outputFilePath);
        converterInstances.add(c);
        return c;
    }

    Converter getConverter(File f, String outputFilePath, String[] options) throws IOException, InvalidFormatException, POIXMLException{
        Converter c = new Converter(f, outputFilePath, options);
        converterInstances.add(c);
        return c;
    }



}
