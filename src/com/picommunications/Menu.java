package com.picommunications;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Menu extends JPanel{

    JFrame f = new JFrame("Excel to XML Converter");
    private JTextArea outputTextArea;
    private JButton convertButton;
    private JTree fileTree;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JCheckBox checkBox4;
    private JCheckBox checkBox5;
    private JCheckBox checkBox6;
    private JCheckBox checkBox7;
    private JCheckBox checkBox8;

/**
 *   Class Description:
 *
 *
 *   @version 28, June, 2016
 *   @author simonrhillary
 */


//=============================================
//  Class Variables
//=============================================

//=============================================
//  Instance Variables
//=============================================

//=============================================
//  Constructors
//=============================================

    public Menu(ActionListener listener){


        JFrame.setDefaultLookAndFeelDecorated(true);
        f.setSize(400, 400);
        f.setLayout(new GridLayout());


        convertButton.addActionListener(listener);
        f.add(fileTree);
        f.add(convertButton);
        f.add(outputTextArea);
        //f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }

    public void setData(Converter data) {
    }

    public void getData(Converter data) {
    }

    public boolean isModified(Converter data) {
        return false;
    }

//=============================================
//  Methods
//=============================================

}
