package com.picommunications;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class Menu extends JPanel{

    JFrame f = new JFrame("Excel to XML Converter");
    public JTextPane outputTextArea;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JCheckBox checkBox4;
    private JCheckBox checkBox5;
    private JCheckBox checkBox6;
    private JCheckBox checkBox7;

    private JPanel rootPanel;
    public JButton inputButton;
    public JButton outputButton;
    private JPanel outputPanel;
    private JPanel inputPanel;
    private JPanel optionPanel;
    public JButton convertButton;
    private JPanel convertPanel;
    public JTextField inputTextField;
    public JTextField outputTextField;

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
        f.setSize(500, 500);
        f.setResizable(true);
        f.setContentPane(rootPanel);




        convertButton.addActionListener(listener);
        f.setLayout(new GridLayout(2, 2));
        f.setSize(850, 300);



        inputPanel.setLayout(new GridLayout(2, 1));
        inputPanel.setOpaque(true);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Options"));
        inputPanel.add(inputButton);
        inputPanel.add(outputButton, BorderLayout.PAGE_END);

        f.add(inputPanel);

        optionPanel.setLayout(new GridLayout(7, 1));
        optionPanel.setOpaque(true);
        optionPanel.setBackground(Color.WHITE);
        optionPanel.setBorder(BorderFactory.createTitledBorder("Conversion Options"));
        optionPanel.add(checkBox1);
        optionPanel.add(checkBox2);
        optionPanel.add(checkBox3);
        optionPanel.add(checkBox4);
        optionPanel.add(checkBox5);
        optionPanel.add(checkBox6);
        optionPanel.add(checkBox7);
        f.add(optionPanel);

        outputPanel.setLayout(new GridLayout(1, 1));
        outputPanel.setOpaque(true);
        outputPanel.setBackground(Color.WHITE);
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        outputPanel.add(outputTextArea);
        f.add(outputPanel);

        convertButton.addActionListener(listener);
        convertPanel.setOpaque(true);
        convertPanel.setBackground(Color.WHITE);
        convertPanel.setBorder(BorderFactory.createTitledBorder("Convert"));

        f.add(convertPanel);




       // f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



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
