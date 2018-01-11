package org.astanis.calculator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class XLSCalculator extends ExcelCalculator {
    XLSCalculator(File file) {
        try {
            wb = new HSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            System.out.println("File not found.");
        }
        int index = wb.getActiveSheetIndex();
        sheet = wb.getSheetAt(index);
        findIndexes();
    }
}