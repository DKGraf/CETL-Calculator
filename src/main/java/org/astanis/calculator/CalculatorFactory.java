package org.astanis.calculator;

import javax.swing.*;
import java.io.File;

public class CalculatorFactory {
    private static File file;

    private CalculatorFactory() {
    }

    public static Calculator createCalculator() throws NoSuchMethodException {
        JFileChooser fileopen = new JFileChooser();
        String fileName = "";
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileopen.getSelectedFile();
            fileName = file.getName();
        }

//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open File");
//
////        fileChooser.getExtensionFilters().addAll(
////                new FileChooser.ExtensionFilter("Excel files", "*.xls, *.xlsx*"),
////                new FileChooser.ExtensionFilter("DBF files", "*.dbf")
////        );
//        FileChooser.ExtensionFilter extFilter =
//                new FileChooser.ExtensionFilter("Excel files", "*.xls");
//        fileChooser.getExtensionFilters().add(extFilter);
//        file = fileChooser.showOpenDialog();//Указываем текущую сцену CodeNote.mainStage
//        String fileName = file.getName();
//        if (file != null) {
//            //Open
//            System.out.println("Процесс открытия файла");
//        }

        int dotIndex = fileName.lastIndexOf('.');
        String extension = fileName.substring(dotIndex).toLowerCase();
        if (".xls".equals(extension)) {
            return new XLSCalculator(file);
        } else if (".xlsx".equals(extension)) {
            return new XLSXCalculator(file);
        } else if (".dbf".equals(extension)) {
            return new DBFCalculator(file);
        } else {
            throw new NoSuchMethodException("Wrong file format");
        }
    }
}
