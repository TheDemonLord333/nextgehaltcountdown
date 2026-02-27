package com.gehalt.countdown;

import java.time.LocalDate;

public class SalaryDateItem {
    public final LocalDate date;
    public final int index; // 1-based position in the list

    public SalaryDateItem(LocalDate date, int index) {
        this.date = date;
        this.index = index;
    }
}
