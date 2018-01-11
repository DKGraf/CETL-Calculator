package org.astanis.calculator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.astanis.calculator.price.PriceList;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@SuppressWarnings("WeakerAccess")
abstract class ExcelCalculator implements Calculator {
    protected Workbook wb;
    protected Sheet sheet;
    protected int categoryColumn;
    protected int quantityColumn;
    protected int pricePositionColumn;
    protected int coefficient1Column;
    protected int coefficient2Column;
    protected int coefficient3Column;
    protected int coefficient4Column;
    protected int coefficient5Column;
    protected int coefficient6Column;
    protected int coefficient7Column;

    public String calculate() {
        BigDecimal total = BigDecimal.ZERO;
        for (Row row :
                sheet) {
            try {
                int category = getCategory(row);
                String pricePosition = row.getCell(pricePositionColumn).getStringCellValue();
                BigDecimal coefficient1 = getBigDecimal(row.getCell(coefficient1Column));
                BigDecimal coefficient2 = getBigDecimal(row.getCell(coefficient2Column));
                BigDecimal coefficient3 = getBigDecimal(row.getCell(coefficient3Column));
                BigDecimal coefficient4 = getBigDecimal(row.getCell(coefficient4Column));
                BigDecimal coefficient5 = getBigDecimal(row.getCell(coefficient5Column));
                BigDecimal coefficient6 = getBigDecimal(row.getCell(coefficient6Column));
                BigDecimal coefficient7 = getBigDecimal(row.getCell(coefficient7Column));
                BigDecimal quantity = getBigDecimal(row.getCell(quantityColumn));
                BigDecimal price = PriceList.getPrice(pricePosition, category);
                BigDecimal sum = price
                        .multiply(quantity)
                        .multiply(coefficient1)
                        .multiply(coefficient2)
                        .multiply(coefficient3)
                        .multiply(coefficient4)
                        .multiply(coefficient4)
                        .multiply(coefficient5)
                        .multiply(coefficient6)
                        .multiply(coefficient7);
                total = total.add(sum);
            } catch (NullPointerException | NumberFormatException e) {
                continue;
            }
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(true);
        String result = df.format(total);

        return result + " RUB";
    }

    protected int getCategory(Row row) {
        Cell cell = row.getCell(categoryColumn);
        int category;
        try {
            category = (int) cell.getNumericCellValue();
        } catch (IllegalStateException e) {
            category = Integer.parseInt(cell.getStringCellValue());
        }
        return category;
    }

    protected BigDecimal getBigDecimal(Cell cell) {
        BigDecimal coefficient;
        try {
            coefficient = BigDecimal.valueOf(cell.getNumericCellValue());
        } catch (IllegalStateException e) {
            coefficient = BigDecimal.valueOf(Double.parseDouble(cell.getStringCellValue()));
        }
        return coefficient;
    }

    protected void findIndexes() {
        Row headerRow = sheet.getRow(0);
        for (Cell cell :
                headerRow) {

            String columnName;
            try {
                columnName = cell.getStringCellValue();
            } catch (IllegalStateException e) {
                columnName = String.valueOf(cell.getNumericCellValue());
            }

            switch (columnName) {
                case "KAT":
                    categoryColumn = cell.getColumnIndex();
                    break;
                case "KOLED":
                    quantityColumn = cell.getColumnIndex();
                    break;
                case "N_POS":
                    pricePositionColumn = cell.getColumnIndex();
                    break;
                case "KF_NAR":
                    coefficient1Column = cell.getColumnIndex();
                    break;
                case "KF_U":
                    coefficient2Column = cell.getColumnIndex();
                    break;
                case "KF_Z":
                    coefficient3Column = cell.getColumnIndex();
                    break;
                case "KF_OT":
                    coefficient4Column = cell.getColumnIndex();
                    break;
                case "KF_A":
                    coefficient5Column = cell.getColumnIndex();
                    break;
                case "KF_OB":
                    coefficient6Column = cell.getColumnIndex();
                    break;
                case "KF_OS":
                    coefficient7Column = cell.getColumnIndex();
                    break;
            }
        }
    }
}
