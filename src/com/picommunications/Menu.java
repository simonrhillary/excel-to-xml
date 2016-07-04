package com.picommunications;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Menu extends JPanel{

    JFrame f = new JFrame("Excel to XML Converter");
    public JTextArea outputTextArea;
    public JCheckBox checkBox1;
    public JCheckBox checkBox2;
    public JCheckBox checkBox3;
    public JCheckBox checkBox4;
    public JCheckBox checkBox5;
    public JCheckBox checkBox6;
    public JCheckBox checkBox7;
    public JCheckBox checkBox8;
    public ArrayList<JCheckBox> checkboxList;

    private JPanel rootPanel;
    public JButton inputButton;
    public JButton outputButton;
    private JPanel outputPanel;
    private JPanel inputPanel;
    public JPanel optionPanel;
    public JButton convertButton;
    private JPanel convertPanel;
    public JTextField inputTextField;
    public JTextField outputTextField;
    public JButton outputToDirectoryButton;
    private JScrollPane scroll;


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

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        f.setSize(500, 500);
        f.setResizable(true);
        f.setContentPane(rootPanel);

        f.setLayout(new GridLayout(2, 2));
        f.setSize(850, 300);

        inputPanel.setLayout(new GridLayout(2, 1));
        inputPanel.setOpaque(true);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Options"));
        inputButton.addActionListener(listener);
        outputButton.addActionListener(listener);
        inputPanel.add(inputButton);
        inputPanel.add(outputButton);

        f.add(inputPanel);

        checkboxList = new ArrayList<>();
        checkboxList.add(checkBox1);
        checkboxList.add(checkBox2);
        checkboxList.add(checkBox3);
        checkboxList.add(checkBox4);
        checkboxList.add(checkBox5);
        checkboxList.add(checkBox6);
        checkboxList.add(checkBox7);
        checkboxList.add(checkBox8);
        checkBox1.setMnemonic(0);
        checkBox2.setMnemonic(1);
        checkBox3.setMnemonic(2);
        checkBox4.setMnemonic(3);
        checkBox5.setMnemonic(4);
        checkBox6.setMnemonic(5);
        checkBox7.setMnemonic(6);
        optionPanel.setLayout(new GridLayout(5, 5));
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

        scroll = new JScrollPane(outputTextArea);
        outputPanel.setLayout(new GridLayout(1, 1));
        outputPanel.setOpaque(true);
        outputPanel.setBackground(Color.WHITE);
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        outputPanel.add(scroll);
        f.add(outputPanel);

        outputToDirectoryButton.addActionListener(listener);
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
