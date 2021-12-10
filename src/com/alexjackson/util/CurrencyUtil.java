package com.alexjackson.util;

public class CurrencyUtil {

    /**
     * Calculate the charge before discounts for the given daily charge and number of charge days
     *
     * @param dailyRentalCharge The daily rate for the tool being rented
     * @param chargeDays The number of days for which the rented tool can be charged
     * @return a double with the product of dailyRentalCharge and chargeDays
     */
    public static double calculatePreDiscountCharge(double dailyRentalCharge, int chargeDays) {
        return dailyRentalCharge * chargeDays;
    }

    /**
     * Calculate the discount amount for a rental
     *
     * @param preDiscountCharge The charge for the rental before discounts are applied
     * @param discountPercent The percent as a whole number that the rental will be discounted
     * @return The product of preDiscountCharge and discountPercent, rounded half up to cents
     */
    public static double calculateDiscountAmount(double preDiscountCharge, int discountPercent) {
        long integerDiscountCents = Math.round(preDiscountCharge * discountPercent);
        return integerDiscountCents / 100.00;
    }

    /**
     * Calculate the final charge for a rental.
     *
     * @param preDiscountCharge The charged for the rental before discounts are applied
     * @param discountAmount The discount to apply to the rental charges
     * @return The difference resulting from the preDiscountCharge minus discountAmount
     */
    public static double calculateFinalCharge(double preDiscountCharge, double discountAmount) {
        long integerFinalCents = Math.round((100 * preDiscountCharge) - (100 * discountAmount));
        return integerFinalCents / 100.00;
    }

    /**
     * Convenience method for formatting a monetary amount in the form $9999.99 for printing
     *
     * @param amount The dollar amount that should be printed
     * @return A String containing the formatted amount
     */
    public static String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }
}
