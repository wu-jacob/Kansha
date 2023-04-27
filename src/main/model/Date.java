package model;

import exceptions.InvalidDateException;

// Represents a date (MM/DD/YYYY)
public class Date {
    private final int month; // the month (MM)
    private final int day;   // the day (DD)
    private final int year;  // the year (YYYY)

    // REQUIRES: date be in format (MM/DD/YYYY) and
    // must be a valid date that exists on a calendar
    // EFFECTS: creates a date with a month, day, and year
    public Date(String date) throws InvalidDateException {
        if (date.length() > 10) {
            throw new InvalidDateException();
        }
        try {
            int month = Integer.parseInt(date.substring(0, 2));
            int day = Integer.parseInt(date.substring(3, 5));
            int year = Integer.parseInt(date.substring(6, 10));
            if (month < 1 || month > 12 || day < 1 || day > 31) {
                throw new InvalidDateException();
            } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
                throw new InvalidDateException();
            } else if (month == 2 && day == 29 && year % 4 != 0) {
                throw new InvalidDateException();
            } else if (month == 2 && day > 29) {
                throw new InvalidDateException();
            } else {
                this.month = month;
                this.day = day;
                this.year = year;
            }
        } catch (NumberFormatException e) {
            throw new InvalidDateException();
        }
    }

    // EFFECTS: returns the given date in string format (MM/DD/YYYY)
    public String dateString() {
        String monthString;
        String dayString;
        StringBuilder yearString;
        if (month / 10 == 0) {
            monthString = "0" + month;
        } else {
            monthString = String.valueOf(month);
        }
        if (day / 10 == 0) {
            dayString = "0" + day;
        } else {
            dayString = String.valueOf(day);
        }
        yearString = new StringBuilder(String.valueOf(year));
        for (int i = 0; i < 3; i++) {
            if (year - Math.pow(10, i) < 0) {
                for (int j = 0; j < (4 - i); j++) {
                    yearString.insert(0, "0");
                }
                break;
            }
        }
        return monthString + "/" + dayString + "/" + yearString;
    }
}
