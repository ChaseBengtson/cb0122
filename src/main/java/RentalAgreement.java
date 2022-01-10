import java.time.LocalDate;

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
        this.subTotal = chargeDays * tool.getToolType().getDailyCharge();
        this.discountPercent = discountPercent;
        this.discountAmount = subTotal * (discountPercent/100f);
        this.finalCharge = subTotal - discountAmount;
    }

    /**
     * Calculates how many days between the checkout date and the due date are chargeable
     * @return the number of chargeable days between the checkout date and the due date.
     */
    private long calculateChargeDays(){
        ChargeDayCalculator calculator = new ChargeDayCalculator(this.dueDate, tool.isChargedWeekend(), tool.isChargedHoliday());
        return calculator.queryFrom(checkoutDate);
    }

    /**
     * Prints the Rental Agreement and its values to the console.
     */
    public void printRentalAgreement(){
        System.out.println("Tool Code: " + tool.getToolCode());
        System.out.println("Tool Type: " + tool.getToolType().getName());
        System.out.println("Tool Brand: " + tool.getBrand());
        System.out.println("Rental Days: " + rentalDays);
        System.out.println("Checkout Date: " + checkoutDate.toString());
        System.out.println("Due Date: " + dueDate.toString());
        System.out.println("Daily Rental Charge: $" + tool.getToolType().getDailyCharge());
        System.out.println("Charge Days: " + chargeDays);
        System.out.println("Pre-Discount Charge: " + subTotal);
        System.out.println("Discount Percent: " + discountPercent + "%");
        System.out.println("Discount Amount: $" + discountAmount);
        System.out.println("Final Charge: $" + finalCharge);
    }
}
