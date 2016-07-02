package com.picommunications;

//IMPORTS




import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main implements ActionListener {

    ConverterFactory cf = new ConverterFactory();
    static Menu gui = new Menu(new Main());
    final JFileChooser fc = new JFileChooser();

    public static void main(String[] args) {


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == gui.convertButton) {
            try {
                cf.getConverter().convert();
                //create converter object
                //populate converter object fields with data from the UI and input file
                //convert the file
                //show the file output in the output window
                //write the file to selected output location

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if(e.getSource() == gui.inputButton){
            int returnVal = fc.showOpenDialog(gui);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                File file = fc.getSelectedFile();
                //import the file and populate converter options with the file headers
                //set the file input and output paths

            }
        }else if(e.getSource() == gui.outputButton){
            //do something
        }
    }
}
