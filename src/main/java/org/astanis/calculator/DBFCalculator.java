package org.astanis.calculator;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import org.astanis.calculator.price.PriceList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

class DBFCalculator implements Calculator {
    private DBFReader reader;
    private int quantityIndex;
    private int pricePositionIndex;
    private int[] coefficientsIndexes;

    DBFCalculator(File file) {
        try {
            reader = new DBFReader(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        findIndexes();
    }

    public String calculate() {
        BigDecimal total = BigDecimal.ZERO;

        DBFRow row;
        while ((row = reader.nextRow()) != null) {
            int category = getCategory(row);
            String pricePosition = row.getString(pricePositionIndex);
            BigDecimal price = PriceList.getPrice(pricePosition, category);
            BigDecimal coefficient = getCoefficient(row);
            BigDecimal quantity = getBigDecimal(row, quantityIndex);
            BigDecimal sum = price.multiply(quantity).multiply(coefficient);
            total = total.add(sum);
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(true);
        String result = df.format(total);

        return result + " RUB";
    }

    private int getCategory(DBFRow row) {
        int category;

        try {
            category = row.getInt("KAT");
        } catch (DBFException e) {
            category = Integer.parseInt(row.getString("KAT"));
        }
        return category;
    }

    private BigDecimal getBigDecimal(DBFRow row, int i) {
        BigDecimal coefficient;

        try {
            coefficient = row.getBigDecimal(i);
        } catch (DBFException e) {
            coefficient = BigDecimal.valueOf(Double.parseDouble(row.getString(i)));
        }

        return coefficient;
    }

    private BigDecimal getCoefficient(DBFRow row) {
        BigDecimal coefficient = BigDecimal.ONE;

        for (int i = 0; i < 7; i++) {
            coefficient = coefficient.multiply(getBigDecimal(row, coefficientsIndexes[i]));
        }

        return coefficient;
    }

    private void findIndexes() {
        int numberOfFields = reader.getFieldCount();
        coefficientsIndexes = new int[7];
        int index = 0;
        for (int i = 0; i < numberOfFields; i++) {
            DBFField field = reader.getField(i);
            String name = field.getName();
            switch (name) {
                case "KOLED":
                    quantityIndex = i;
                    break;
                case "N_POS":
                    pricePositionIndex = i;
                    break;
                case "KF_NAR":
                    coefficientsIndexes[index] = i;
                    index++;
                    break;
                case "KF_U":
                    coefficientsIndexes[index] = i;
                    index++;
                    break;
                case "KF_Z":
                    coefficientsIndexes[index] = i;
                    index++;
                    break;
                case "KF_OT":
                    coefficientsIndexes[index] = i;
                    index++;
                    break;
                case "KF_A":
                    coefficientsIndexes[index] = i;
                    index++;
                    break;
                case "KF_OB":
                    coefficientsIndexes[index] = i;
                    index++;
                    break;
                case "KF_OS":
                    coefficientsIndexes[index] = i;
                    index++;
                    break;
            }
        }
    }
}
