package com.picommunications;


import java.io.File;
import java.util.ArrayList;

public class ConverterFactory {

/**
 *   Class Description:
 *
 *
 *   @version 20, June, 2016
 *   @author simonrhillary
 */

    /**
     * TODO
     * 1 Constructors
     *
     *
     */


//=============================================
//  Class Variables
//=============================================

    public ArrayList<Boolean> defaultOptions;
    public static String defaultOutputFilePath = System.getProperty("user.home");

//=============================================
//  Instance Variables
//=============================================

   public  ArrayList<Converter> converterInstances = new ArrayList<Converter>();

//=============================================
//  Methods
//=============================================

    public Converter getConverter(int i){
        return converterInstances.get(i);
    }

    public Converter getConverter(){
        return new Converter();
    }

    public Converter getConverter(File f){
        Converter c = new Converter(f);
        c.setOutputDirectory(defaultOutputFilePath);
        converterInstances.add(c);
        return c;
    }

    public Converter getConverter(File f, String outputFilePath){
        Converter c = new Converter(f, outputFilePath);
        converterInstances.add(c);
        return c;
    }

    public Converter getConverter(File f, String outputFilePath, String[] options){
        Converter c = new Converter(f, outputFilePath, options);
        converterInstances.add(c);
        return c;
    }



}
