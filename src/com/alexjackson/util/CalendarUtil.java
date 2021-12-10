package com.alexjackson.util;

import com.alexjackson.model.ToolType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtil {

    /**
     * Produce a Calendar set to the given date
     *
     * @param s A string containing the desired date
     * @return A Calendar set to the date in the input string
     * @throws ParseException When given string does not match expected format
     */
    public static Calendar getCalendar(String s) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatter.parse(s));
        return cal;
    }

    /**
     * Produce a Calendar containing calculated due date for the checkout date and rental days of a given rental
     *
     * @param checkOutDate Calendar containing a rental checkout date
     * @param rentalDays   The duration of the rental in days
     * @return A Calendar containing the due date for the rental
     */
    public static Calendar calculateDueDate(Calendar checkOutDate, int rentalDays) {
        Calendar dueDate = (Calendar) checkOutDate.clone();
        dueDate.add(Calendar.DATE, rentalDays);
        return dueDate;
    }

    /**
     * Calculate the number of days the customer should be charged based on the tool and the rental dates
     *
     * @param checkOutDate The date on which the rental is being checked out
     * @param dueDate The date on which the rental will be returned
     * @param toolType The type of tool that is being rented
     * @return An int representing the number of days the daily rental fee will be charged
     */
    public static int calculateChargeDays(Calendar checkOutDate, Calendar dueDate, ToolType toolType) {
        Calendar cal = (Calendar) checkOutDate.clone();
        int chargeDays = 0;
        boolean chargeable;
        while (cal.before(dueDate)) {
            // Roll to next day
            cal.roll(Calendar.DATE, true);
            // Decide if the day is chargeable based on the tool type and holiday/weekend status
            chargeable = ((toolType.holidayCharge || !isHoliday(cal))
                    && (toolType.weekendCharge || !isWeekend(cal)));
            // Increment chargeDays as needed
            chargeDays += chargeable ? 1 : 0;
        }
        return chargeDays;
    }

    /**
     * Determine if the given date falls on a weekend
     *
     * @param cal An instance of Calendar containing the date to check
     * @return True if the given day falls on a weekend
     */
    public static boolean isWeekend(Calendar cal) {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY;
    }

    /**
     * Determine if the given date is a holiday for charge calculations
     *
     * @param cal An instance of Calendar for the date to check against holidays
     * @return True if the given date falls on a holiday
     */
    public static boolean isHoliday(Calendar cal) {
        int month = cal.get(Calendar.MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeekInMonth = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);

        switch (month) {
            case Calendar.JULY:
                // If it's July, check if it is Independence Day
                if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                    // Independence Day is not observed on weekends
                    return false;
                } else if (dayOfMonth == 3 && dayOfWeek == Calendar.FRIDAY) {
                    // If it's a Friday and the 3rd, return true (Saturday 4th)
                    return true;
                } else if (dayOfMonth == 5 && dayOfWeek == Calendar.MONDAY) {
                    // If it's a Monday and the 5th, return true (Sunday 4th)
                    return true;
                } else {
                    // Otherwise, return true if it is the 4th
                    return dayOfMonth == 4;
                }
            case Calendar.SEPTEMBER:
                // If it's September, check if it is Labor Day
                // If it is Monday of the first week, return true
                return dayOfWeekInMonth == 1 && dayOfWeek == Calendar.MONDAY;
            default:
                // No other months have holidays, so return false
                return false;
        }
    }

    /**
     * Produce a String containing a Calendar date in the format mm/dd/yy
     *
     * @param cal The Calendar to convert
     * @return A String containing a formatted date
     */
    public static String formatCalendar(Calendar cal) {
        // mm/dd/yy
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        return formatter.format(cal.getTime());
    }
}
