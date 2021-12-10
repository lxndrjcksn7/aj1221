package com.alexjackson.model;

import com.alexjackson.exception.DiscountPercentOutOfRangeException;
import com.alexjackson.exception.InvalidRentalDaysException;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class RentalAgreementTest {

    @Test
    public void validateShouldThrowDPOORExceptionWhenDiscountGreaterThan100() {
        RentalAgreement rental = new RentalAgreement(ToolCode.JAKR, 5, new GregorianCalendar(2015, Calendar.SEPTEMBER, 3), 101);
        DiscountPercentOutOfRangeException exception = assertThrows(
                DiscountPercentOutOfRangeException.class,
                rental::validate
        );
        assertEquals("Discount must be in range 0...100", exception.getMessage());
    }

    @Test
    public void validateShouldThrowIRDExceptionWhenRentalDaysLessThan1() {
        RentalAgreement rental = new RentalAgreement(ToolCode.JAKR, 0, new GregorianCalendar(2015, Calendar.SEPTEMBER, 3), 50);
        InvalidRentalDaysException exception = assertThrows(
                InvalidRentalDaysException.class,
                rental::validate
        );
        assertEquals("Rental must last at least 1 day", exception.getMessage());
    }
}
