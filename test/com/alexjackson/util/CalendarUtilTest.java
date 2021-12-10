package com.alexjackson.util;

import com.alexjackson.model.ToolType;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class CalendarUtilTest {

    @Test
    public void getCalendar() {
        try {
            Calendar actual = CalendarUtil.getCalendar("09/01/2021");
            assertEquals(2021, actual.get((Calendar.YEAR)));
            assertEquals(Calendar.SEPTEMBER, actual.get((Calendar.MONTH)));
            assertEquals(1, actual.get((Calendar.DATE)));
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void formatCalendarShouldReturnStringWithSpecifiedFormatWithSingleDigitMonthAndDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2021, Calendar.SEPTEMBER, 9);

        assertEquals("09/09/21", CalendarUtil.formatCalendar(cal));
    }

    @Test
    public void formatCalendarShouldReturnStringWithSpecifiedFormatWithDoubleDigitMonthAndDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2021, Calendar.OCTOBER, 10);

        assertEquals("10/10/21", CalendarUtil.formatCalendar(cal));
    }

    @Test
    public void calculateDueDateShouldReturnDateGivenNumberOfDaysAfterCheckout() {
        Calendar checkout = Calendar.getInstance();
        checkout.set(2021, Calendar.NOVEMBER, 29);

        Calendar expectedDueDate = (Calendar) checkout.clone();
        expectedDueDate.set(2021, Calendar.DECEMBER, 6);

        int rentalDays = 7;

        assertEquals(expectedDueDate, CalendarUtil.calculateDueDate(checkout, rentalDays));
    }

    @Test
    public void calculateChargeDaysShouldReturnChargeableDaysBetweenCheckoutAndDueDate() {
        // Long weekend - 2 weekend days and 1 holiday
        Calendar checkout = Calendar.getInstance();
        checkout.set(2021, Calendar.JULY, 1);

        Calendar dueDate = (Calendar) checkout.clone();
        dueDate.set(2021, Calendar.JULY, 8);

        // Jackhammer - 2 free weekend days and 1 free holiday
        assertEquals(4, CalendarUtil.calculateChargeDays(checkout, dueDate, ToolType.JACKHAMMER));

        // Chainsaw - 2 free weekend days
        assertEquals(5, CalendarUtil.calculateChargeDays(checkout, dueDate, ToolType.CHAINSAW));

        // Ladder - 1 free holiday
        assertEquals(6, CalendarUtil.calculateChargeDays(checkout, dueDate, ToolType.LADDER));
    }

    @Test
    public void isWeekendShouldReturnTrueWhenCalenderDayFallsOnWeekend() {
        Calendar cal = Calendar.getInstance();
        cal.set(2021, Calendar.DECEMBER, 11);

        assertTrue(CalendarUtil.isWeekend(cal));
    }

    @Test
    public void isWeekendShouldReturnFalseWhenCalendarDayFallsOnWeekday() {
        Calendar cal = Calendar.getInstance();
        cal.set(2021, Calendar.DECEMBER, 10);

        assertFalse(CalendarUtil.isWeekend(cal));
    }

    @Test
    public void isHolidayShouldReturnTrueWhenCalenderDateFallsOnIndependenceDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.JULY, 4);

        assertTrue(CalendarUtil.isHoliday(cal));
    }

    @Test
    public void isHolidayShouldReturnTrueWhenCalenderDateFallsOnIndependenceDayObservedFriday() {
        Calendar cal = Calendar.getInstance();
        cal.set(2020, Calendar.JULY, 3);

        assertTrue(CalendarUtil.isHoliday(cal));
    }

    @Test
    public void isHolidayShouldReturnTrueWhenCalenderDateFallsOnIndependenceDayObservedMonday() {
        Calendar cal = Calendar.getInstance();
        cal.set(2021, Calendar.JULY, 5);

        assertTrue(CalendarUtil.isHoliday(cal));
    }

    @Test
    public void isHolidayShouldReturnFalseWhenSaturdayAndIndependenceDayObservedFriday() {
        Calendar cal = Calendar.getInstance();
        cal.set(2020, Calendar.JULY, 4);

        assertEquals(Calendar.SATURDAY, cal.get(Calendar.DAY_OF_WEEK));
        assertFalse(CalendarUtil.isHoliday(cal));
    }

    @Test
    public void isHolidayShouldReturnFalseWhenSundayAndIndependenceDayObservedMonday() {
        Calendar cal = Calendar.getInstance();
        cal.set(2021, Calendar.JULY, 4);

        assertEquals(Calendar.SUNDAY, cal.get(Calendar.DAY_OF_WEEK));
        assertFalse(CalendarUtil.isHoliday(cal));
    }

    @Test
    public void isHolidayShouldReturnTrueWhenCalenderDateFallsOnLaborDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(2021, Calendar.SEPTEMBER, 6);

        assertTrue(CalendarUtil.isHoliday(cal));
    }

    @Test
    public void isHolidayShouldReturnFalseWhenCalenderDateFallsOutsideHolidays() {
        Calendar cal = Calendar.getInstance();
        cal.set(2021, Calendar.SEPTEMBER, 7);

        assertFalse(CalendarUtil.isHoliday(cal));
    }
}
