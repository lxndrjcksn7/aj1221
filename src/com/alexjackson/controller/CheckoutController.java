package com.alexjackson.controller;

import com.alexjackson.exception.DiscountPercentOutOfRangeException;
import com.alexjackson.exception.InvalidRentalDaysException;
import com.alexjackson.model.RentalAgreement;
import com.alexjackson.model.ToolCode;

import java.util.Calendar;

public class CheckoutController {

    public CheckoutController() {
        super();
    }

    public String processRental(ToolCode toolCode, int rentalDays, Calendar checkOutDate, int discountPercent)
            throws InvalidRentalDaysException, DiscountPercentOutOfRangeException {
        // Create and process rental agreement
        RentalAgreement rentalAgreement = new RentalAgreement(toolCode, rentalDays, checkOutDate, discountPercent);
        rentalAgreement.validate();
        rentalAgreement.calculate();
        String output = rentalAgreement.toString();

        // Print results to console
        System.out.println(output);

        // Return printed output (useful for testing)
        return output;
    }
}
