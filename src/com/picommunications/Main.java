package com.picommunications;

//IMPORTS


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {

    ConverterFactory cf = new ConverterFactory();

    public static void main(String[] args) {


        Menu gui = new Menu();







    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cf.getConverter().convert();
    }
}
