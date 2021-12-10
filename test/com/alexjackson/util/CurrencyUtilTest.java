package com.alexjackson.util;

import com.alexjackson.model.ToolType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyUtilTest {

    @Test
    public void calculatePreDiscountChargeShould() {
        Double preDiscountCharge = CurrencyUtil.calculatePreDiscountCharge(ToolType.CHAINSAW.dailyCharge, 3);
        assertEquals(Double.valueOf(4.47), preDiscountCharge);
    }

    @Test
    public void calculateDiscountAmountShould() {
        Double discountAmount = CurrencyUtil.calculateDiscountAmount(4.47, 25);
        System.out.println(discountAmount);
        assertEquals(Double.valueOf(1.12), discountAmount);
    }

    @Test
    public void calculateFinalChargeShould() {
        Double finalCharge = CurrencyUtil.calculateFinalCharge(4.47, 1.12);
        assertEquals(Double.valueOf(3.35), finalCharge);
    }

    @Test
    public void formatCurrencyShouldAddDollarSign() {
        assertEquals("$3.35", CurrencyUtil.formatCurrency(3.35));
    }
}
