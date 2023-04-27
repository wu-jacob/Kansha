package model;

import exceptions.InvalidDateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test for the Date class
public class DateTest {
    @Test
    void testGoodConstructor() {
        try {
            Date testDate1 = new Date("02/28/2004");
            Date testDate2 = new Date("02/29/2012");
            Date testDate3 = new Date("01/01/0001");
            Date testDate4 = new Date("04/30/2023");
            assertEquals("02/28/2004", testDate1.dateString());
            assertEquals("02/29/2012", testDate2.dateString());
            assertEquals("01/01/0001", testDate3.dateString());
            assertEquals("04/30/2023", testDate4.dateString());
        } catch (InvalidDateException e) {
            fail("Date is out of bounds");
        }
    }

    @Test
    void testBadMonthConstructor() {
        try {
            Date testDate = new Date("00/24/2004");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
        try {
            Date testDate = new Date("14/24/2004");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
    }

    @Test
    void testBadDayConstructor() {
        try {
            Date testDate = new Date("04/00/2004");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
        try {
            Date testDate = new Date("04/79/2004");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
    }

    @Test
    void testBadYearConstructor() {
        try {
            Date testDate = new Date("04/24/35212");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
    }

    @Test
    void test30DayMonth() {
        try {
            Date testDate = new Date("04/31/2004");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
        try {
            Date testDate = new Date("06/31/2005");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
        try {
            Date testDate = new Date("09/31/2006");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
        try {
            Date testDate = new Date("11/31/0000");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
    }

    @Test
    void testLeapYear() {
        try {
            Date testDate = new Date("02/30/2004");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
    }

    @Test
    void testNoLeapYear() {
        try {
            Date testDate = new Date("02/29/2003");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
        try {
            Date testDate = new Date("02/29/0001");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
    }

    @Test
    void testInvalidDateFormat() {
        try {
            Date testDate = new Date("abcdefg");
            fail();
        } catch (InvalidDateException e) {
            // pass
        }
    }
}
