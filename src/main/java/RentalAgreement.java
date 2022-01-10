import java.util.Date;

public class RentalAgreement {
    private Tool tool;
    private int rentalDays;
    private Date checkoutDate;
    private Date dueDate;
    private int chargeDays;
    private float subTotal; //Pre-discount charge on the specifications sheet
    private int discountPercent;
    private float discountAmount;
    private float finalCharge;

    public RentalAgreement(Tool tool, int rentalDays, int discountAmount, Date checkoutDate){
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.discountAmount = discountAmount;
        this.checkoutDate = checkoutDate;
    }

    public void printRentalAgreement(){

    }
}
