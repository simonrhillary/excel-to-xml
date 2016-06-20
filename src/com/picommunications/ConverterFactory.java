package com.picommunications;


import java.util.ArrayList;

public class ConverterFactory {

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

    ArrayList<Boolean> defaultOptions;

//=============================================
//  Instance Variables
//=============================================

    ArrayList<Converter> converterInstances;

//=============================================
//  Methods
//=============================================

    public Converter getConverter(int i){
        return converterInstances.get(i);
    }

    public Converter getConverter(){
        return new Converter();
    }

}
