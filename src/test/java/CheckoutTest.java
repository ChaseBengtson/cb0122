import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test suite for testing the behavior of methods in the {@link Checkout} class.
 */
public class CheckoutTest {
    private final String invalidRentalDaysMessage = "The Rental Day Amount was not 1 or greater.  Please restart and enter a value that is 1 or greater.";

    Tool duplicateChainsaw = new Tool.Builder("CHNS").setToolType(Tool.ToolType.CHAINSAW).setBrand("Duplicate").build();
    List<Tool> inventory;

    @BeforeEach
     void beforeEach(){
        inventory = List.of(TestConstants.stihlChainsaw, TestConstants.wernerLadder, TestConstants.dewaltJackhammer, TestConstants.ridgidJackhammer);
    }

    @Test
    @DisplayName("Should return Tool object when input is valid.")
    void collectToolTest(){
        Tool resultTool = Checkout.processToolCode(inventory, "CHNS");
        assertEquals("CHNS", resultTool.getToolCode());
    }

    @Test
    @DisplayName("Should throw Runtime Exception when two or more matching tools are found.")
    void collectToolMultipleMatchingToolsTest(){
        ArrayList<Tool> duplicateList = new ArrayList<>(inventory);
        duplicateList.add(duplicateChainsaw);
        RuntimeException e = assertThrows(RuntimeException.class, () -> Checkout.processToolCode(duplicateList, "CHNS"));
        String duplicateToolMessage = "More than one tool was found with the provided Tool Code.  Please ensure all Tool Codes are unique.";
        assertEquals(duplicateToolMessage, e.getMessage());
    }

    @Test
    @DisplayName("Should throw Runtime Exception when no matching tool is found.")
    void collectToolNoMatchingToolFoundTest(){
        RuntimeException e = assertThrows(RuntimeException.class, () -> Checkout.processToolCode(inventory, "ABCD"));
        String noToolMatchMessage = "No tools with the provided Tool Code were found.  Please try again with a registered Tool Code.";
        assertEquals(noToolMatchMessage, e.getMessage());
    }

    @Test
    @DisplayName("Should return number of rental days when input is valid.")
    void collectRentalDaysTest(){
        int resultRentalDays = Checkout.processRentalDays("4");
        assertEquals(4, resultRentalDays);
    }

    @Test
    @DisplayName("Should throw Runtime Exception when input is negative.")
    void collectRentalDaysNegativeTest(){
        RuntimeException e = assertThrows(RuntimeException.class, () -> Checkout.processRentalDays("-4"));
        assertEquals(invalidRentalDaysMessage, e.getMessage());
    }

    @Test
    @DisplayName("Should throw Runtime Exception when input is zero.")
    void collectRentalDaysZeroTest(){
        RuntimeException e = assertThrows(RuntimeException.class, () -> Checkout.processRentalDays("0"));
        assertEquals(invalidRentalDaysMessage, e.getMessage());
    }

    @Test
    @DisplayName("Should throw Runtime Exception when input is non-numeric.")
    void collectRentalDaysNonNumericTest(){
        RuntimeException e = assertThrows(RuntimeException.class, () -> Checkout.processRentalDays("four"));
        String nonNumericRentalDaysMessage = "A non-numeric entry for Rental Day Count was entered.  Please restart and enter a whole number amount.";
        assertEquals(nonNumericRentalDaysMessage, e.getMessage());
    }

    @Test
    @DisplayName("Should return the discount amount as a whole integer.")
    void collectDiscountAmountTest(){
        int resultDiscountAmount =  Checkout.processDiscountPercentage("20");
        assertEquals(20, resultDiscountAmount);
    }

    @Test
    @DisplayName("Should throw a Runtime Exception when the input is non numeric.")
    void collectDiscountAmountNonNumericInputTest(){
        RuntimeException e = assertThrows(RuntimeException.class, () -> Checkout.processDiscountPercentage("twenty percent"));
        String nonNumericDiscountAmountMessage =
            "An non-numeric entry for Discount Amount was entered.  The entry should be a whole number between 0 and 100 inclusively.  " + "Please restart and enter a valid amount.";
        assertEquals(nonNumericDiscountAmountMessage, e.getMessage());
    }

    @Test
    @DisplayName("Should throw a Runtime Exception when the input is negative.")
    void collectDiscountAmountNegativeInputTest(){
        RuntimeException e = assertThrows(RuntimeException.class, () -> Checkout.processDiscountPercentage("-20"));
        assertEquals(TestConstants.discountAmountOutOfRangeMessage, e.getMessage());
    }

    @Test
    @DisplayName("Should throw a Runtime Exception when the input is larger than 100.")
    void collectDiscountAmountTooLargeInputTest(){
        RuntimeException e = assertThrows(RuntimeException.class, () -> Checkout.processDiscountPercentage("120"));
        assertEquals(TestConstants.discountAmountOutOfRangeMessage, e.getMessage());
    }

    @Test
    @DisplayName("Should return the checkout date as a LocalDate object.")
    void collectCheckoutDateTest(){
        LocalDate resultDate = Checkout.processCheckoutDate("02/17/22");
        assertNotNull(resultDate);
        assertEquals(Month.FEBRUARY, resultDate.getMonth());
        assertEquals(17, resultDate.getDayOfMonth());
        assertEquals(2022, resultDate.getYear());
    }

    @Test
    @DisplayName("Should throw DateTimeParseException when checkout date cannot be parsed.")
    void collectCheckoutDateCannotParseTest(){
        DateTimeParseException e = assertThrows(DateTimeParseException.class, () -> Checkout.processCheckoutDate("2022/02/17"));
    }
}
