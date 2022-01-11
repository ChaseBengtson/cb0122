import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A suite of integration tests for demonstrating the overall functionality of the application.
 */
public class DemonstrationTest {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy", Locale.ENGLISH);
    private final LocalDate checkoutDate2 = LocalDate.parse("7/2/20", formatter);
    private final LocalDate checkoutDate3 = LocalDate.parse("7/2/15", formatter);

    @Test
    @DisplayName("Test 1")
    void appTest1(){
        RuntimeException e = assertThrows(RuntimeException.class,
            () -> new Checkout("JAKR", "5", "101", "9/3/15"));
        assertEquals(TestConstants.discountAmountOutOfRangeMessage, e.getMessage());
    }

    @Test
    @DisplayName("Test 2")
    void appTest2(){
        LocalDate dueDate2 = checkoutDate2.plus(3, ChronoUnit.DAYS);
        Checkout resultCheckout = new Checkout("LADW", "3", "10", "7/2/20");
        assertNotNull(resultCheckout);
        RentalAgreement resultAgreement = resultCheckout.getRentalAgreement();
        assertNotNull(resultAgreement);
        assertToolsAreEqual(TestConstants.wernerLadder, resultAgreement.getTool());
        assertEquals(3, resultAgreement.getRentalDays());
        assertEquals(checkoutDate2, resultAgreement.getCheckoutDate());
        assertEquals(dueDate2, resultAgreement.getDueDate());
        assertEquals(2, resultAgreement.getChargeDays());
        assertEquals(3.98f, resultAgreement.getSubTotal());
        assertEquals(10, resultAgreement.getDiscountPercent());
        assertEquals(0.40f, resultAgreement.getDiscountAmount());
        assertEquals(3.58f, resultAgreement.getFinalCharge());
    }

    @Test
    @DisplayName("Test 3")
    void appTest3(){
        LocalDate dueDate3 = checkoutDate3.plus(5, ChronoUnit.DAYS);
        Checkout resultCheckout = new Checkout("CHNS", "5", "25", "7/2/15");
        assertNotNull(resultCheckout);
        RentalAgreement resultAgreement = resultCheckout.getRentalAgreement();
        assertNotNull(resultAgreement);
        assertToolsAreEqual(TestConstants.stihlChainsaw, resultAgreement.getTool());
        assertEquals(5, resultAgreement.getRentalDays());
        assertEquals(checkoutDate3, resultAgreement.getCheckoutDate());
        assertEquals(dueDate3, resultAgreement.getDueDate());
        assertEquals(3, resultAgreement.getChargeDays());
        assertEquals(4.47f, resultAgreement.getSubTotal());
        assertEquals(25, resultAgreement.getDiscountPercent());
        assertEquals(1.12f, resultAgreement.getDiscountAmount());
        assertEquals(3.35f, resultAgreement.getFinalCharge());
    }

    @Test
    @DisplayName("Test 4")
    void appTest4(){
        LocalDate checkoutDate1 = LocalDate.parse("9/3/15", formatter);
        LocalDate dueDate4 = checkoutDate1.plus(6, ChronoUnit.DAYS);
        Checkout resultCheckout = new Checkout("JAKD", "6", "0", "9/3/15");
        assertNotNull(resultCheckout);
        RentalAgreement resultAgreement = resultCheckout.getRentalAgreement();
        assertNotNull(resultAgreement);
        assertToolsAreEqual(TestConstants.dewaltJackhammer, resultAgreement.getTool());
        assertEquals(6, resultAgreement.getRentalDays());
        assertEquals(checkoutDate1, resultAgreement.getCheckoutDate());
        assertEquals(dueDate4, resultAgreement.getDueDate());
        assertEquals(3, resultAgreement.getChargeDays());
        assertEquals(8.97f, resultAgreement.getSubTotal());
        assertEquals(0, resultAgreement.getDiscountPercent());
        assertEquals(0.00f, resultAgreement.getDiscountAmount());
        assertEquals(8.97f, resultAgreement.getFinalCharge());
    }

    @Test
    @DisplayName("Test 5")
    void appTest5(){
        LocalDate dueDate5 = checkoutDate3.plus(9, ChronoUnit.DAYS);
        Checkout resultCheckout = new Checkout("JAKR", "9", "0", "7/2/15");
        assertNotNull(resultCheckout);
        RentalAgreement resultAgreement = resultCheckout.getRentalAgreement();
        assertNotNull(resultAgreement);
        assertToolsAreEqual(TestConstants.ridgidJackhammer, resultAgreement.getTool());
        assertEquals(9, resultAgreement.getRentalDays());
        assertEquals(checkoutDate3, resultAgreement.getCheckoutDate());
        assertEquals(dueDate5, resultAgreement.getDueDate());
        assertEquals(5, resultAgreement.getChargeDays());
        assertEquals(14.95f, resultAgreement.getSubTotal());
        assertEquals(0, resultAgreement.getDiscountPercent());
        assertEquals(0.00f, resultAgreement.getDiscountAmount());
        assertEquals(14.95f, resultAgreement.getFinalCharge());
    }

    @Test
    @DisplayName("Test 6")
    void appTest6(){
        LocalDate dueDate6 = checkoutDate2.plus(4, ChronoUnit.DAYS);
        Checkout resultCheckout = new Checkout("JAKR", "4", "50", "7/2/20");
        assertNotNull(resultCheckout);
        RentalAgreement resultAgreement = resultCheckout.getRentalAgreement();
        assertNotNull(resultAgreement);
        assertToolsAreEqual(TestConstants.ridgidJackhammer, resultAgreement.getTool());
        assertEquals(4, resultAgreement.getRentalDays());
        assertEquals(checkoutDate2, resultAgreement.getCheckoutDate());
        assertEquals(dueDate6, resultAgreement.getDueDate());
        assertEquals(1, resultAgreement.getChargeDays());
        assertEquals(2.99f, resultAgreement.getSubTotal());
        assertEquals(50, resultAgreement.getDiscountPercent());
        assertEquals(1.50f, resultAgreement.getDiscountAmount());
        assertEquals(1.49f, resultAgreement.getFinalCharge());
    }

    /**
     * Asserts that all fields of two {@link Tool} objects are the same.
     * @param expectedTool The expected state of the Tool
     * @param actualTool The actual state of the Tool
     */
    private void assertToolsAreEqual(Tool expectedTool, Tool actualTool){
        assertEquals(expectedTool.getToolCode(), actualTool.getToolCode());
        assertEquals(expectedTool.getToolType(), actualTool.getToolType());
        assertEquals(expectedTool.getBrand(), actualTool.getBrand());
    }
}
