package com.picommunications;

import org.apache.poi.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Main implements ActionListener {

    boolean validFile;
    ConverterFactory cf = new ConverterFactory();
    static Menu gui = new Menu(new Main());
    static final JFileChooser fc = new JFileChooser();
    static FileNameExtensionFilter filter = new FileNameExtensionFilter("Microsoft .xlsx Files", "xlsx", "xls");
    Converter converter;

    public static void main(String[] args) {
        fc.setFileFilter(filter);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if convert button clicked
        if (e.getSource() == gui.convertButton) {
            try {
                if(!gui.inputTextField.getText().equals("")){
                    if (!gui.outputTextField.getText().equals("")) {
                        if (getTickedOptions().length > 0) {
                            converter = cf.getConverter(new File(gui.inputTextField.getText()),
                                    gui.outputTextField.getText(), getTickedOptions());
                            converter.convert();
                            gui.outputTextArea.setText(converter.getFinalOutputString()); //show the file output in the output window
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
            } catch (FileNotFoundException fnfe) {
                JOptionPane.showMessageDialog(gui, "The Specified File was not found! Please check the filepath.", "Error", JOptionPane.ERROR_MESSAGE);
                fnfe.printStackTrace();
            } catch(ParserConfigurationException pce){
                JOptionPane.showMessageDialog(gui, "Parser Configuration Exception!", "Error", JOptionPane.ERROR_MESSAGE);
                pce.printStackTrace();
            } catch(TransformerException te){
                JOptionPane.showMessageDialog(gui, "Transformer Exception!", "Error", JOptionPane.ERROR_MESSAGE);
                te.printStackTrace();
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(gui, "I/O Exception!", "Error", JOptionPane.ERROR_MESSAGE);
                ioe.printStackTrace();
            } catch(InvalidFormatException ife){
                JOptionPane.showMessageDialog(gui, "Invalid Format Exception!", "Error", JOptionPane.ERROR_MESSAGE);
                ife.printStackTrace();
            }
            // if input button clicked
        } else if(e.getSource() == gui.inputButton){
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal = fc.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                File file = fc.getSelectedFile();
                try {
                    validFile = true;
                    converter =  cf.getConverter(file);
                    setDefaultOptions(converter.headers);          //import the file and populate converter options with the file headers
                } catch (IOException e1) {
                    validFile = false;
                    JOptionPane.showMessageDialog(gui, "IO Exception acquiring Input File!", "Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (InvalidFormatException ife){
                    validFile = false;
                    JOptionPane.showMessageDialog(gui, "Invalid Format Exception! Please select another file.", "Error", JOptionPane.ERROR_MESSAGE);
                    ife.printStackTrace();
                } catch(POIXMLException poie){
                    validFile = false;
                    JOptionPane.showMessageDialog(gui, "POI XML Exception! Please select another file.", "Error", JOptionPane.ERROR_MESSAGE);
                    gui.inputTextField.setText("");
                    converter.setInputFilePath("");
                    poie.printStackTrace();
                }
                if(validFile) {
                    gui.inputTextField.setText(file.getAbsolutePath()); //set the file input and output paths
                }
            }
            //if output button clicked
        }else if(e.getSource() == gui.outputButton){
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  //set filechooser to only accept directories
            int returnVal = fc.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                gui.outputTextField.setText(fc.getSelectedFile().getAbsolutePath());
                converter.setOutputDirectory(fc.getSelectedFile().getAbsolutePath());
            }
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY); //reset filechooser to accept excel

        // if outputToDirectory button clicked
        }else if(e.getSource() == gui.outputToDirectoryButton){
            boolean output = true;
            if (!gui.inputTextField.getText().equals("")) {
                if (!gui.outputTextField.getText().equals("")) {
                    if (getTickedOptions().length > 0) {
                        File dir = new File(converter.getOutputDirectory());
                        File[] dirContents = dir.listFiles();
                        try{
                            for(int i = 0; i < dirContents.length; i++){
                                if(dirContents[i].getName().equals(converter.outputFileName)){
                                    int res = JOptionPane.showConfirmDialog(gui, "A file by this name already exists in the specified folder." +
                                            " Would you like to Overwrite it?", "Warning", JOptionPane.WARNING_MESSAGE);
                                    if(res == JOptionPane.YES_OPTION){
                                        converter.outputResult();
                                        JOptionPane.showMessageDialog(gui, "The File has been written to the Output Directory",
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                                        output = false;
                                    }else{
                                        output = false;
                                    }
                                }
                            }
                            if (output) {
                                converter.outputResult();
                                JOptionPane.showMessageDialog(gui, "The File has been written to the Output Directory",
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
                            }

                        } catch (TransformerException e1) {
                            JOptionPane.showMessageDialog(gui, "Transformer Exception!", "Error", JOptionPane.ERROR_MESSAGE);
                            e1.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(gui, "You must Select at least 1 element to populate the XML File with",
                                "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(gui, "You must select an output directory!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(gui, "You must select an input file!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void setDefaultOptions(String[] s){
        for(int i = 0; i< s.length; i++){
            if(gui.checkboxList.get(i)!= null) {
                gui.checkboxList.get(i).setText(s[i]);
                gui.checkboxList.get(i).setSelected(true);
            }else if(gui.checkboxList.get(i) == null){
                gui.checkboxList.add(new JCheckBox(s[i]));
                gui.checkboxList.get(i).setSelected(true);
            }
        }
        for(int i = 0; i < gui.checkboxList.size(); i++){
            if(!gui.checkboxList.get(i).isSelected()){
                gui.checkboxList.get(i).setVisible(false);
            }
        }
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
