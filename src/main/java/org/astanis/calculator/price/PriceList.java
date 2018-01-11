package org.astanis.calculator.price;

import java.math.BigDecimal;
import java.util.EnumSet;

public class PriceList {
    private static EnumSet<Price> priceList = EnumSet.allOf(Price.class);

    private PriceList() {
    }

    public static BigDecimal getPrice(String pricePosition, int category) {
        BigDecimal price = BigDecimal.ZERO;

        if (category == 1) {
            for (Price p :
                    priceList) {
                if (pricePosition.equals(p.getPricePosition())) price = p.getPriceSumCat1();
            }
        } else if (category == 2) {
            for (Price p :
                    priceList) {
                if (pricePosition.equals(p.getPricePosition())) price = p.getPriceSumCat2();
            }
        } else if (category == 3) {
            for (Price p :
                    priceList) {
                if (pricePosition.equals(p.getPricePosition())) price = p.getPriceSumCat3();
            }
        } else {
            throw new AssertionError("Wrong file format.");
        }

        return price;
    }
}
