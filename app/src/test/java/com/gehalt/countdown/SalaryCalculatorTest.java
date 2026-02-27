package com.gehalt.countdown;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

public class SalaryCalculatorTest {

    @Test
    public void testSalaryDay_january2025() {
        // Jan 2025: 1=Wed, 2=Thu, 3=Fri, 6=Mon, 7=Tue, 8=Wed, 9=Thu
        // Workdays: 2,3,6,7,8,9,10 -> 7th workday = 10 Jan 2025 (Fri)
        LocalDate result = SalaryCalculator.getSalaryDay(2025, 1);
        assertEquals(LocalDate.of(2025, Month.JANUARY, 10), result);
    }

    @Test
    public void testSalaryDay_february2025() {
        // Feb 2025: 3=Mon, 4=Tue, 5=Wed, 6=Thu, 7=Fri, 10=Mon, 11=Tue
        // 7th workday = 11 Feb 2025 (Tue)
        LocalDate result = SalaryCalculator.getSalaryDay(2025, 2);
        assertEquals(LocalDate.of(2025, Month.FEBRUARY, 11), result);
    }

    @Test
    public void testSalaryDay_march2025() {
        // Mar 2025: 3=Mon, 4=Tue, 5=Wed, 6=Thu, 7=Fri, 10=Mon, 11=Tue
        // 7th workday = 11 Mar 2025
        LocalDate result = SalaryCalculator.getSalaryDay(2025, 3);
        assertEquals(LocalDate.of(2025, Month.MARCH, 11), result);
    }

    @Test
    public void testSalaryDay_monthStartingOnSaturday() {
        // March 2025 starts on Saturday: first workday = Monday 3rd
        // Workdays: 3,4,5,6,7,10,11 -> 7th = 11 Mar 2025
        LocalDate result = SalaryCalculator.getSalaryDay(2025, 3);
        assertEquals(LocalDate.of(2025, 3, 11), result);
    }

    @Test
    public void testBreakDownMillis_exactDay() {
        long millis = 1 * 24 * 60 * 60 * 1000L; // exactly 1 day
        long[] parts = SalaryCalculator.breakDownMillis(millis);
        assertEquals(1, parts[0]); // days
        assertEquals(0, parts[1]); // hours
        assertEquals(0, parts[2]); // minutes
        assertEquals(0, parts[3]); // seconds
    }

    @Test
    public void testBreakDownMillis_mixed() {
        // 2 days + 3 hours + 15 minutes + 30 seconds
        long millis = (2L * 86400 + 3L * 3600 + 15L * 60 + 30L) * 1000L;
        long[] parts = SalaryCalculator.breakDownMillis(millis);
        assertEquals(2, parts[0]);
        assertEquals(3, parts[1]);
        assertEquals(15, parts[2]);
        assertEquals(30, parts[3]);
    }

    @Test
    public void testBreakDownMillis_zero() {
        long[] parts = SalaryCalculator.breakDownMillis(0);
        assertArrayEquals(new long[]{0, 0, 0, 0}, parts);
    }

    @Test
    public void testGetUpcomingSalaryDates_count() {
        java.util.List<LocalDate> dates = SalaryCalculator.getUpcomingSalaryDates(6);
        assertEquals(6, dates.size());
    }

    @Test
    public void testGetUpcomingSalaryDates_allInFuture() {
        LocalDate next = SalaryCalculator.getNextSalaryDate();
        java.util.List<LocalDate> dates = SalaryCalculator.getUpcomingSalaryDates(6);
        for (LocalDate d : dates) {
            assertTrue("Upcoming date should be after next salary", d.isAfter(next));
        }
    }
}
