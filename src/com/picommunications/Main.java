package com.picommunications;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main implements ActionListener {

    ConverterFactory cf = new ConverterFactory();
    static Menu gui = new Menu(new Main());
    static final JFileChooser fc = new JFileChooser();
    static FileNameExtensionFilter filter = new FileNameExtensionFilter("XLSX Files", "xlsx");
    Converter converter;

    public static void main(String[] args) {
        fc.setFileFilter(filter);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == gui.convertButton) { //if convert button clicked
            try {
                if(!gui.inputTextField.getText().equals("")){
                    if (!gui.outputTextField.getText().equals("")) {
                        if (getTickedOptions().length > 0) {
                            converter = cf.getConverter(new File(gui.inputTextField.getText()),
                                    gui.outputTextField.getText(), getTickedOptions());
                            converter.convert();
                            gui.outputTextArea.setText(converter.getFinalOutput()); //show the file output in the output window
                            //write the file to selected output location
                        } else {
                            JOptionPane.showMessageDialog(gui, "You must Select at least 1 element to populate the XML File with",
                                    "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(gui, "You must select an output directory!",
                                "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(gui, "You must select an input file!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                }

                //convert the file


            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if(e.getSource() == gui.inputButton){  // if input button clicked
            int returnVal = fc.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                File file = fc.getSelectedFile();
                converter =  cf.getConverter(file);
                setDefaultOptions(converter.headers);          //import the file and populate converter options with the file headers
                gui.inputTextField.setText(file.getAbsolutePath());  //set the file input and output paths
            }
        }else if(e.getSource() == gui.outputButton){  //if output button clicked
            //do something
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  //set filechooser to only accept directories
            int returnVal = fc.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                gui.outputTextField.setText(fc.getSelectedFile().getAbsolutePath());
                converter.setOutputDirectory(fc.getSelectedFile().getAbsolutePath());
            }
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY); //reset filechooser to accept excel
        }
    }

    public void setDefaultOptions(String[] s){
        gui.checkBox1.setText(s[0]);
        gui.checkBox2.setText(s[1]);
        gui.checkBox3.setText(s[2]);
        gui.checkBox4.setText(s[3]);
        gui.checkBox5.setText(s[4]);
        gui.checkBox6.setText(s[5]);
        gui.checkBox7.setText(s[6]);
        gui.checkBox8.setText("subtitles");
    }

    public String[] getTickedOptions(){
        ArrayList<String> al = new ArrayList();
        for(int i = 0; i < gui.checkboxList.size(); i++){
            if(gui.checkboxList.get(i).isSelected()){
                al.add(gui.checkboxList.get(i).getText());
            }
        }
        String[] returnArray = new String[al.size()];
        returnArray = al.toArray(returnArray);
        return returnArray;
    }


}
