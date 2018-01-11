package org.astanis.calculator;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XLSXCalculator extends ExcelCalculator {
    public XLSXCalculator(File file) {
        try {
            wb = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            System.out.println("File not found.");
        }
        int index = wb.getActiveSheetIndex();
        sheet = wb.getSheetAt(index);
        findIndexes();
    }
}
