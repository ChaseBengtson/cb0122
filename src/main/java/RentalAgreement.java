import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Data structure for representing a Rental Agreement.
 */
public class RentalAgreement {
    private final Tool tool;
    private final int rentalDays;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private final long chargeDays;
    private final float subTotal; //Pre-discount charge on the specifications sheet
    private final int discountPercent;
    private final float discountAmount;
    private final float finalCharge;

    /**
     * Constructor.
     * @param tool The {@link Tool} being rented
     * @param rentalDays The number of days the Tool is being rented for.
     * @param discountPercent The percentage as a whole number between 0 (inclusive) and 100 (inclusive) to be discounted from the charge
     * @param checkoutDate The date that the tool is being checked out on.
     */
    public RentalAgreement(Tool tool, int rentalDays, int discountPercent, LocalDate checkoutDate){
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.dueDate = checkoutDate.plusDays(rentalDays);
        this.chargeDays = this.calculateChargeDays();
        this.subTotal = BigDecimal.valueOf(chargeDays * tool.getToolType().getDailyCharge()).setScale(2, RoundingMode.HALF_UP).floatValue();
        this.discountPercent = discountPercent;
        this.discountAmount = BigDecimal.valueOf(subTotal * (discountPercent/100f)).setScale(2, RoundingMode.HALF_UP).floatValue();
        this.finalCharge = BigDecimal.valueOf(subTotal - discountAmount).setScale(2, RoundingMode.HALF_UP).floatValue();
    }

    /**
     * Calculates how many days between the checkout date and the due date are chargeable
     * @return the number of chargeable days between the checkout date and the due date.
     */
    private long calculateChargeDays(){
        ChargeDayCalculator calculator = new ChargeDayCalculator(this.checkoutDate, tool.isChargedWeekend(), tool.isChargedHoliday());
        return calculator.queryFrom(dueDate);
    }

    /**
     * Prints the Rental Agreement and its values to the console.
     */
    public void print (){
        System.out.println("Tool Code: " + tool.getToolCode());
        System.out.println("Tool Type: " + tool.getToolType().getName());
        System.out.println("Tool Brand: " + tool.getBrand());
        System.out.println("Rental Days: " + rentalDays);
        System.out.println("Checkout Date: " + checkoutDate.format(DateTimeFormatter.ofPattern("M/d/yy")));
        System.out.println("Due Date: " + dueDate.format(DateTimeFormatter.ofPattern("M/d/yy")));
        System.out.println("Daily Rental Charge: $" + tool.getToolType().getDailyCharge());
        System.out.println("Charge Days: " + chargeDays);
        System.out.println("Pre-Discount Charge: $" + subTotal);
        System.out.println("Discount Percent: " + discountPercent + "%");
        System.out.println("Discount Amount: $" + discountAmount);
        System.out.println("Final Charge: $" + finalCharge);
    }

    public Tool getTool () {
        return tool;
    }

    public int getRentalDays () {
        return rentalDays;
    }

    public LocalDate getCheckoutDate () {
        return checkoutDate;
    }

    public LocalDate getDueDate () {
        return dueDate;
    }

    public long getChargeDays () {
        return chargeDays;
    }

    public float getSubTotal () {
        return subTotal;
    }

    public int getDiscountPercent () {
        return discountPercent;
    }

    public float getDiscountAmount () {
        return discountAmount;
    }

    public float getFinalCharge () {
        return finalCharge;
    }
}
