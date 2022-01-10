import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class Checkout {

    /**
     * Main method for the tool rental application.
     * @param args arguments included when calling the main function.
     */
    public static void main (String[] args) {
        //Create Tools to fill an 'inventory' to be used by the checkout application
        Tool stihlChainsaw = new Tool.Builder("CHNS").setToolType(Tool.ToolType.CHAINSAW).setBrand("Stihl").build();
        Tool wernerLadder = new Tool.Builder("LADW").setToolType(Tool.ToolType.LADDER).setBrand("Werner").build();
        Tool dewaltJackhammer = new Tool.Builder("JAKD").setToolType(Tool.ToolType.JACKHAMMER).setBrand("DeWalt").build();
        Tool ridgidJackhammer = new Tool.Builder("JAKR").setToolType(Tool.ToolType.JACKHAMMER).setBrand("Ridgid").build();

        List<Tool> inventory = List.of(stihlChainsaw, wernerLadder, dewaltJackhammer, ridgidJackhammer);

        System.out.println("Tool Rental Application loaded...");

        //Collect user input
        Tool tool = collectTool(inventory);
        int rentalDays = collectRentalDays();
        int discountAmount = collectDiscountAmount();
        LocalDate checkoutDate = collectCheckoutDate();

        //Generate and print Rental Agreement
        RentalAgreement rentalAgreement = new RentalAgreement(tool, rentalDays, discountAmount, checkoutDate);
        rentalAgreement.printRentalAgreement();
    }

    /**
     * Asks the user for the tool code of the {@link Tool} being rented.
     * @param inventory A {@link List<Tool>} of Tools that can be rented.
     * @return The Tool object that is being rented.
     * @throws RuntimeException if the tool is not found or if there are multiple tools with the given code.
     */
    public static Tool collectTool(List<Tool> inventory) throws RuntimeException{
        System.out.println("What is the Tool Code of the tool to be rented?");
        String enteredToolCode = System.console().readLine();

        List<Tool> matchingTools = inventory.stream().filter(tool -> tool.getToolCode().equals(enteredToolCode)).collect(Collectors.toList());
        if(matchingTools.size() == 1){
            return matchingTools.get(0);
        }else if(matchingTools.size() > 1){
            throw new RuntimeException("More than one tool was found with the provided Tool Code.  Please ensure all Tool Codes are unique.");
        }else{
            throw new RuntimeException("No tools with the provided Tool Code were found.  Please try again with a registered Tool Code.");
        }
    }

    /**
     * Asks the user for the number of days that the tool will be rented for and validates the input.
     * @return The number of days the tool will be rented for.
     * @throws RuntimeException if the input is invalid.
     */
    public static int collectRentalDays() throws RuntimeException{
        System.out.println("How many days will the tool be rented for?");
        String enteredRentalDays = System.console().readLine();
        int rentalDaysNum = 0;

        try{
            rentalDaysNum = Integer.parseInt(enteredRentalDays);
        }catch(NumberFormatException e){
            throw new RuntimeException("A non-numeric entry for Rental Day Count was entered.  Please restart and enter a whole number amount.");
        }
        validateRentalDays(rentalDaysNum);
        return rentalDaysNum;
    }

    /**
     * Asks the user for the discount percentage to be applied to the final sale.
     * @return The amount of the discount percentage.
     * @throws RuntimeException if the entered amount is non-numeric or not between 0-100 inclusively
     */
    public static int collectDiscountAmount() throws RuntimeException{
        System.out.println("What percentage of a discount should be applied? (e.g. 20 = 20%)");
        String enteredDiscountAmount = System.console().readLine();
        int discountAmountNum = 0;

        try{
            discountAmountNum = Integer.parseInt(enteredDiscountAmount);
        }catch(NumberFormatException e){
            throw new RuntimeException("An non-numeric entry for Discount Amount was entered.  The entry should be a whole number between 0 and 100 inclusively.  "
                + "Please restart and enter a valid amount.");
        }
        validateDiscountAmount(discountAmountNum);
        return discountAmountNum;
    }

    /**
     * Asks the user for the day that the tool will be checked out on.
     * @return A {@link LocalDate} object containing the date the tool will be checkout out on
     */
    public static LocalDate collectCheckoutDate(){
        System.out.println("What is the date that the tool will be checked out on?  Please enter in mm/dd/yyyy format.");
        String enteredCheckoutDate = System.console().readLine();
        LocalDate checkoutDate = null;
        try{
            checkoutDate = LocalDate.parse(enteredCheckoutDate, DateTimeFormatter.ofPattern("MM'/dd'/yyyy"));
        }catch(DateTimeParseException e){
            System.out.println("The entered checkout date could not be parsed.  Please ensure the date is entered in 'mm/dd/yyyy' format.");
            e.printStackTrace();
            System.exit(1);
        }
        return checkoutDate;
    }

    /**
     * Checks that the entered string for rental day count is a valid integer larger than 0.
     * @param rentalDays The rental day count.
     * @throws RuntimeException if the rental day count is not an integer larger than 0.
     */
    public static void validateRentalDays (int rentalDays) throws RuntimeException{
        if(rentalDays < 1){
            throw new RuntimeException("The Rental Day Amount was not 1 or greater.  Please restart and enter a value that is 1 or greater.");
        }
    }

    /**
     * Checks that the entered string for discount amount is a valid integer between 0 and 100 inclusively.
     * @param discountAmount The discount amount
     * @throws RuntimeException if the discount amount is not an integer between 0 and 100 inclusively.
     */
    public static void validateDiscountAmount (int discountAmount) throws RuntimeException{

        if(discountAmount < 0 || discountAmount > 100){
            throw new RuntimeException("The Discount Amount was not between 0 and 100 inclusively.  Please restart and enter a value between 0 and 100.");
        }
    }

}
