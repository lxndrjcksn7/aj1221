package com.alexjackson.controller;

import com.alexjackson.exception.DiscountPercentOutOfRangeException;
import com.alexjackson.model.RentalAgreement;
import com.alexjackson.model.ToolCode;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class CheckoutControllerTest {

    private CheckoutController checkoutController;
    private Calendar checkoutDate;

    @Before
    public void setup() {
        checkoutController = new CheckoutController();
        checkoutDate = Calendar.getInstance();
    }

    // Spec Test Cases
    @Test
    public void testCase1() {
        checkoutDate.set(2015, Calendar.SEPTEMBER, 3);

        RentalAgreement test1 = new RentalAgreement(ToolCode.JAKR, 5, checkoutDate, 101);
        DiscountPercentOutOfRangeException exception = assertThrows(
                DiscountPercentOutOfRangeException.class,
                test1::validate
        );

        // Check that the thrown exception has the expected user-friendly message
        assertEquals("Discount must be in range 0...100", exception.getMessage());
    }

    @Test
    public void testCase2() {
        String output = null;
        checkoutDate.set(2020, Calendar.JULY, 2);

        try {
            output = checkoutController.processRental(ToolCode.LADW, 3, checkoutDate, 10);
        } catch (Exception e) {
            fail();
        }

        // Check that tool name/brand, a date, a dollar amount, and a discount print with correct format
        assertTrue(output.contains("Tool type: Ladder"));
        assertTrue(output.contains("Tool brand: Werner"));
        assertTrue(output.contains("Due date: 07/05/20"));
        assertTrue(output.contains("Daily rental charge: $1.99"));
        assertTrue(output.contains("Discount percent: 10%"));

        // Check that final charge reflects correct calculation
        assertTrue(output.contains("Final charge: $3.58"));
    }

    @Test
    public void testCase3() {
        String output = null;
        checkoutDate.set(2015, Calendar.JULY, 2);

        try {
            output = checkoutController.processRental(ToolCode.CHNS, 5, checkoutDate, 25);
        } catch (Exception e) {
            fail();
        }

        // Check that tool name/brand, a date, a dollar amount, and a discount print with correct format
        assertTrue(output.contains("Tool type: Chainsaw"));
        assertTrue(output.contains("Tool brand: Stihl"));
        assertTrue(output.contains("Due date: 07/07/15"));
        assertTrue(output.contains("Daily rental charge: $1.49"));
        assertTrue(output.contains("Discount percent: 25%"));

        // Check that final charge reflects correct calculation
        assertTrue(output.contains("Final charge: $3.35"));
    }

    @Test
    public void testCase4() {
        String output = null;
        checkoutDate.set(2015, Calendar.SEPTEMBER, 3);

        try {
            output = checkoutController.processRental(ToolCode.JAKD, 6, checkoutDate, 0);
        } catch (Exception e) {
            fail();
        }

        // Check that tool name/brand, a date, a dollar amount, and a discount print with correct format
        assertTrue(output.contains("Tool type: Jackhammer"));
        assertTrue(output.contains("Tool brand: DeWalt"));
        assertTrue(output.contains("Due date: 09/09/15"));
        assertTrue(output.contains("Daily rental charge: $2.99"));
        assertTrue(output.contains("Discount percent: 0%"));

        // Check that final charge reflects correct calculation
        assertTrue(output.contains("Final charge: $8.97"));
    }

    @Test
    public void testCase5() {
        String output = null;
        checkoutDate.set(2015, Calendar.JULY, 2);

        try {
            output = checkoutController.processRental(ToolCode.JAKR, 9, checkoutDate, 0);
        } catch (Exception e) {
            fail();
        }

        // Check that tool name/brand, a date, a dollar amount, and a discount print with correct format
        assertTrue(output.contains("Tool type: Jackhammer"));
        assertTrue(output.contains("Tool brand: Ridgid"));
        assertTrue(output.contains("Due date: 07/11/15"));
        assertTrue(output.contains("Daily rental charge: $2.99"));
        assertTrue(output.contains("Discount percent: 0%"));

        // Check that final charge reflects correct calculation
        assertTrue(output.contains("Final charge: $14.95"));
    }

    @Test
    public void testCase6() {
        String output = null;
        checkoutDate.set(2020, Calendar.JULY, 2);

        try {
            output = checkoutController.processRental(ToolCode.JAKR, 4, checkoutDate, 50);
        } catch (Exception e) {
            fail();
        }

        // Check that tool name/brand, a date, a dollar amount, and a discount print with correct format
        assertTrue(output.contains("Tool type: Jackhammer"));
        assertTrue(output.contains("Tool brand: Ridgid"));
        assertTrue(output.contains("Due date: 07/06/20"));
        assertTrue(output.contains("Daily rental charge: $2.99"));
        assertTrue(output.contains("Discount percent: 50%"));

        // Check that final charge reflects correct calculation
        assertTrue(output.contains("Final charge: $1.49"));
    }
}
