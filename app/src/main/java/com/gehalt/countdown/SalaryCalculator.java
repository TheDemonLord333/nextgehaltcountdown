package com.gehalt.countdown;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Berechnet den 7. Werktag jedes Monats (= Gehaltszahlungstag).
 * Werktage = Montag bis Freitag (ohne Wochenende).
 */
public class SalaryCalculator {

    /**
     * Gibt den 7. Werktag des angegebenen Jahres/Monats zurück.
     */
    public static LocalDate getSalaryDay(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        int workdayCount = 0;
        while (true) {
            if (isWorkday(date)) {
                workdayCount++;
                if (workdayCount == 7) {
                    return date;
                }
            }
            date = date.plusDays(1);
        }
    }

    private static boolean isWorkday(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }

    /**
     * Gibt das nächste Gehaltsdatum zurück (heute oder in der Zukunft).
     */
    public static LocalDate getNextSalaryDate() {
        LocalDate today = LocalDate.now();
        LocalDate salaryThisMonth = getSalaryDay(today.getYear(), today.getMonthValue());
        if (!today.isAfter(salaryThisMonth)) {
            return salaryThisMonth;
        }
        // Nächsten Monat
        LocalDate nextMonth = today.plusMonths(1);
        return getSalaryDay(nextMonth.getYear(), nextMonth.getMonthValue());
    }

    /**
     * Gibt eine Liste der nächsten N Gehaltsdaten zurück (ab nächstem Monat nach dem nächsten).
     */
    public static List<LocalDate> getUpcomingSalaryDates(int count) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate nextSalary = getNextSalaryDate();

        // Startmonat: Monat nach dem nächsten Gehalt
        LocalDate cursor = nextSalary.plusMonths(1);
        for (int i = 0; i < count; i++) {
            dates.add(getSalaryDay(cursor.getYear(), cursor.getMonthValue()));
            cursor = cursor.plusMonths(1);
        }
        return dates;
    }

    /**
     * Berechnet die verbleibende Zeit bis zum nächsten Gehalt in Millisekunden.
     * Der Zahltag wird als Beginn des Tages (00:00 Uhr) gewertet.
     */
    public static long getMillisUntilNextSalary() {
        LocalDate nextSalary = getNextSalaryDate();
        LocalDateTime salaryDateTime = nextSalary.atStartOfDay();
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());

        if (now.isAfter(salaryDateTime) || now.isEqual(salaryDateTime)) {
            return 0;
        }

        long diffSeconds = java.time.Duration.between(now, salaryDateTime).getSeconds();
        return diffSeconds * 1000L;
    }

    /**
     * Zerlegt Millisekunden in Tage, Stunden, Minuten, Sekunden.
     */
    public static long[] breakDownMillis(long millis) {
        if (millis <= 0) return new long[]{0, 0, 0, 0};
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        long days    = TimeUnit.SECONDS.toDays(seconds);
        long hours   = TimeUnit.SECONDS.toHours(seconds) % 24;
        long minutes = (seconds % 3600) / 60;
        long secs    = seconds % 60;
        return new long[]{days, hours, minutes, secs};
    }
}
