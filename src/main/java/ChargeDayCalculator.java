import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQuery;

/**
 * A class for determining how many days between the checkout and due date of a {@link Tool} rental should be charged.
 */
public class ChargeDayCalculator implements TemporalQuery<Long> {
    private final LocalDate checkoutDate;
    private final boolean chargeWeekends;
    private final boolean chargeHolidays;

    /**
     * Constructor
     * @param checkoutDate A {@link LocalDate} containing the date that the tool will be checked out on.
     * @param chargeWeekends Whether or not weekend days will be charged.
     * @param chargeHolidays Whether or not holidays will be charged.
     */
    public ChargeDayCalculator(LocalDate checkoutDate, boolean chargeWeekends, boolean chargeHolidays){
        this.checkoutDate = checkoutDate;
        this.chargeWeekends = chargeWeekends;
        this.chargeHolidays = chargeHolidays;
    }

    @Override
    public Long queryFrom (TemporalAccessor dueDate) {
        LocalDate localDueDate = LocalDate.from(dueDate);
        long daysBetween = ChronoUnit.DAYS.between(checkoutDate, localDueDate);

        if(chargeWeekends && chargeHolidays){
            return daysBetween;
        }else if(chargeWeekends){
            daysBetween -= countHolidaysNotCharged(localDueDate);
        }else if(chargeHolidays){
            daysBetween -= countWeekendsNotCharged(localDueDate);
        }else{
            daysBetween -= (countHolidaysNotCharged(localDueDate) + countWeekendsNotCharged(localDueDate));
        }
        return daysBetween;
    }

    /**
     * Counts the number of holidays between the checkout date and the due date.  Only the 4th of July and Labor Day
     * are considered for counting.  The 4th of July is counted on the nearest weekday if it occurs on a weekend.
     * @param dueDate The end date of the range to be checked
     * @return The number of holidays between the checkout date and due date
     */
    private int countHolidaysNotCharged(LocalDate dueDate){
        LocalDate independenceDay = LocalDate.of(checkoutDate.getYear(), Month.JULY.getValue(), 4);
        LocalDate laborDay = LocalDate.of(checkoutDate.getYear(), Month.SEPTEMBER.getValue(), 1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        int chargeDays = 0;
        if(checkoutDate.isBefore(independenceDay) && dueDate.isAfter(independenceDay)){
            long daysToIndpDay = ChronoUnit.DAYS.between(checkoutDate, independenceDay);
            long daysFromIndpDay = ChronoUnit.DAYS.between(independenceDay, dueDate);
            switch(independenceDay.getDayOfWeek()){
                case SATURDAY:
                    if(daysToIndpDay > 0){
                        chargeDays++;
                    }
                case SUNDAY:
                    if(daysFromIndpDay > 0){
                        chargeDays++;
                    }
                default:
                    chargeDays++;
            }
        }else if(checkoutDate.equals(independenceDay)){
            //Only lose a charge day if 4th of July is on a Sunday
            if(checkoutDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                chargeDays++;
            }
        }else if(dueDate.equals(independenceDay)){
            if(dueDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                chargeDays++;
            }
        }

        if(checkoutDate.isBefore(laborDay) && dueDate.isAfter(laborDay)){
            chargeDays++;
        }else if(dueDate.equals(laborDay)){
            chargeDays++;
        }

        return chargeDays;
    }

    /**
     * Counts the number of weekend days between the checkout date and the due date.
     * @param dueDate The end date of the range to be checked
     * @return The number of weekend days between the checkout date and the due date.
     */
    private int countWeekendsNotCharged(LocalDate dueDate){
        int chargeDays = 0;
        //Determine if checkout is on sat.  If not, subtract 1
        if(!checkoutDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
            chargeDays++;
        }

        //Count weeks between first sun and due day and subtract 2 for each week
        LocalDate firstSundayAfterCheckout = checkoutDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        long weeksBetween = ChronoUnit.WEEKS.between(firstSundayAfterCheckout, dueDate);
        chargeDays += (weeksBetween * 2);

        //Determine if there is a sat after counted weeks but before/on due day
        LocalDate dateAfterWeeks = firstSundayAfterCheckout.plusWeeks(weeksBetween);
        LocalDate nextSaturday = dateAfterWeeks.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        if(nextSaturday.isBefore(dueDate) || nextSaturday.isEqual(dueDate)){
            chargeDays--;
        }
        return chargeDays;
    }
}
