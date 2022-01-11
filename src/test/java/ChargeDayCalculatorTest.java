import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test suite for testing the {@link ChargeDayCalculator} class.
 */
public class ChargeDayCalculatorTest {
    ChargeDayCalculator calculator;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy", Locale.ENGLISH);
    LocalDate indpDayWeekday = LocalDate.of(2017, Month.JULY, 4);
    LocalDate indpDayWeekend = LocalDate.of(2020, Month.JULY, 4);
    LocalDate laborDay = LocalDate.of(2021, Month.SEPTEMBER, 6);

    @BeforeEach
    void setUp(){
        calculator = null;
    }

    @Test
    @DisplayName("Counts charge days correctly with no holidays or weekends.")
    void countChargeDaysNoHolidaysOrWeekendsTest(){
        calculator = new ChargeDayCalculator(indpDayWeekday.minusDays(2), false, false);
        long resultChargeDays = calculator.queryFrom(indpDayWeekday.plusDays(5));
        assertEquals(4, resultChargeDays);
    }

    @Test
    @DisplayName("Counts charge days correctly with July 4th holiday during week and no weekends.")
    void countChargeDaysWithJuly4thDuringWeekNoWeekendTest(){
        calculator = new ChargeDayCalculator(indpDayWeekday.minusDays(2), false, true);
        long resultChargeDays = calculator.queryFrom(indpDayWeekday.plusDays(5));
        assertEquals(5, resultChargeDays);
    }

    @Test
    @DisplayName("Counts charge days correctly with July 4th holiday during weekend and no weekends.")
    void countChargeDaysWithJuly4thDuringWeekendNoWeekendTest(){
        calculator = new ChargeDayCalculator(indpDayWeekend.minusDays(2), false, true);
        long resultChargeDays = calculator.queryFrom(indpDayWeekend.plusDays(5));
        assertEquals(5, resultChargeDays);
    }

    @Test
    @DisplayName("Counts charge days correctly with Labor Day holiday and no weekends.")
    void countChargeDaysWithLaborDayNoWeekendTest(){
        calculator = new ChargeDayCalculator(laborDay.minusDays(2), false, true);
        long resultChargeDays = calculator.queryFrom(laborDay.plusDays(5));
        assertEquals(5, resultChargeDays);
    }

    @Test
    @DisplayName("Counts charge days correctly with weekends but no holidays.")
    void countChargeDaysWithWeekendsNoHolidaysTest(){
        calculator = new ChargeDayCalculator(indpDayWeekday.minusDays(2), true, false);
        long resultChargeDays = calculator.queryFrom(indpDayWeekday.plusDays(5));
        assertEquals(6, resultChargeDays);
    }
}
