package com.alexjackson;

import com.alexjackson.controller.CheckoutController;
import com.alexjackson.model.ToolCode;
import com.alexjackson.util.CalendarUtil;

import java.text.ParseException;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) throws ParseException {
        // Get parameters
        ToolCode toolCode = ToolCode.valueOf(args[0]);
        int rentalDays = Integer.parseInt(args[1]);
        Calendar checkOutDate = CalendarUtil.getCalendar(args[2]);
        int discountPercent = Integer.parseInt(args[3]);

        // Initialize controller
        CheckoutController controller = new CheckoutController();

        // Attempt to process input
        try {
            controller.processRental(toolCode, rentalDays, checkOutDate, discountPercent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
