package com.alexjackson.model;

import com.alexjackson.exception.DiscountPercentOutOfRangeException;
import com.alexjackson.exception.InvalidRentalDaysException;
import com.alexjackson.util.CalendarUtil;
import com.alexjackson.util.CurrencyUtil;

import java.util.Calendar;

public class RentalAgreement {

    private final ToolCode toolCode;
    private final ToolType toolType;
    private final String toolBrand;
    private final int rentalDays;
    private final Calendar checkOutDate;
    private Calendar dueDate;
    private final double dailyRentalCharge;
    private int chargeDays;
    private double preDiscountCharge;
    private final int discountPercent;
    private double discountAmount;
    private double finalCharge;

    public RentalAgreement(ToolCode toolCode, int rentalDays, Calendar checkOutDate, int discountPercent) {
        this.toolCode = toolCode;
        this.rentalDays = rentalDays;
        this.checkOutDate = checkOutDate;
        this.discountPercent = discountPercent;

        // Enum derived fields
        this.toolType = this.toolCode.type;
        this.toolBrand = this.toolCode.brand;
        this.dailyRentalCharge = toolType.dailyCharge;
    }

    public void validate() throws InvalidRentalDaysException, DiscountPercentOutOfRangeException {
        if (this.rentalDays <= 0) {
            throw new InvalidRentalDaysException("Rental must last at least 1 day");
        } else if (this.discountPercent < 0 || this.discountPercent > 100) {
            throw new DiscountPercentOutOfRangeException("Discount must be in range 0...100");
        }
    }

    public void calculate() {
        // CalendarUtil calculated fields
        this.dueDate = CalendarUtil.calculateDueDate(this.checkOutDate, this.rentalDays);
        this.chargeDays = CalendarUtil.calculateChargeDays(this.checkOutDate, this.dueDate, this.toolType);

        // ChargeUtil calculated fields
        this.preDiscountCharge = CurrencyUtil.calculatePreDiscountCharge(this.dailyRentalCharge, this.chargeDays);
        this.discountAmount = CurrencyUtil.calculateDiscountAmount(this.preDiscountCharge, this.discountPercent);
        this.finalCharge = CurrencyUtil.calculateFinalCharge(this.preDiscountCharge, this.discountAmount);
    }

    @Override
    public String toString() {
        return "Tool code: " + toolCode +
                "\nTool type: " + toolType.printName +
                "\nTool brand: " + toolBrand +
                "\nRental days: " + rentalDays +
                "\nCheck out date: " + CalendarUtil.formatCalendar(checkOutDate) +
                "\nDue date: " + CalendarUtil.formatCalendar(dueDate) +
                "\nDaily rental charge: " + CurrencyUtil.formatCurrency(dailyRentalCharge) +
                "\nCharge days: " + chargeDays +
                "\nPre discount charge: " + CurrencyUtil.formatCurrency(preDiscountCharge) +
                "\nDiscount percent: " + discountPercent + "%" +
                "\nDiscount amount: " + CurrencyUtil.formatCurrency(discountAmount) +
                "\nFinal charge: " + CurrencyUtil.formatCurrency(finalCharge);
    }
}
