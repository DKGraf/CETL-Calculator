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
