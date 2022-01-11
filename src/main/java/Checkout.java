import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Class for checking out a tool.
 */
public class Checkout {
    //Create Tools to fill an 'inventory' to be used by the checkout application
    private final Tool stihlChainsaw = new Tool.Builder("CHNS").setToolType(Tool.ToolType.CHAINSAW).setBrand("Stihl").build();
    private final Tool wernerLadder = new Tool.Builder("LADW").setToolType(Tool.ToolType.LADDER).setBrand("Werner").build();
    private final Tool dewaltJackhammer = new Tool.Builder("JAKD").setToolType(Tool.ToolType.JACKHAMMER).setBrand("DeWalt").build();
    private final Tool ridgidJackhammer = new Tool.Builder("JAKR").setToolType(Tool.ToolType.JACKHAMMER).setBrand("Ridgid").build();

    private final List<Tool> inventory = List.of(stihlChainsaw, wernerLadder, dewaltJackhammer, ridgidJackhammer);
    private final Tool tool;
    private final int rentalDays;
    private final int discountPercentage;
    private final LocalDate checkoutDate;
    private final RentalAgreement rentalAgreement;

    /**
     * Main method for the tool rental application.
     * @param args arguments included when calling the main function.
     */
    public static void main (String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Tool Rental Application loaded...");

        try{
            //Collect user input
            System.out.println("What is the Tool Code of the tool to be rented?");
            String toolCode = reader.readLine();
            System.out.println("How many days will the tool be rented for?");
            String rentalDays = reader.readLine();
            System.out.println("What percentage of a discount should be applied? (e.g. 20 = 20%)");
            String discountPercentage = reader.readLine();
            System.out.println("What is the date that the tool will be checked out on?  Please enter in mm/dd/yy format.");
            String checkoutDate = reader.readLine();

            Checkout checkout = new Checkout(toolCode, rentalDays, discountPercentage, checkoutDate);

            checkout.printRentalAgreement();
        }catch(IOException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Constructor
     * @param toolCode The tool code String of the tool being rented
     * @param rentalDays The number of days the Tool is being rented for as a String.
     * @param discountPercentage The percentage as a whole number between 0 (inclusive) and 100 (inclusive) to be discounted from the charge as a String
     * @param checkoutDate The date that the tool is being checked out on as a String in MM/dd/yy format
     */
    public Checkout(String toolCode, String rentalDays, String discountPercentage, String checkoutDate){
        this.tool = processToolCode(inventory, toolCode);
        this.rentalDays = processRentalDays(rentalDays);
        this.discountPercentage = processDiscountPercentage(discountPercentage);
        this.checkoutDate = processCheckoutDate(checkoutDate);
        this.rentalAgreement = new RentalAgreement(this.tool, this.rentalDays, this.discountPercentage, this.checkoutDate);
    }

    /**
     * Prints the rental agreement generated for the checkout instance.
     */
    public void printRentalAgreement(){
        rentalAgreement.print();
    }

    /**
     * Parses user input of a tool code into a {@link Tool} object.
     * @param inventory A {@link List<Tool>} of Tools that can be rented.
     * @param enteredToolCode The string entered by the user for the tool code.
     * @return The Tool object that is being rented.
     * @throws RuntimeException if the tool is not found or if there are multiple tools with the given code.
     */
    public static Tool processToolCode (List<Tool> inventory, String enteredToolCode) throws RuntimeException{
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
     * Parses user input of rental days.
     * @return The number of days the tool will be rented for.
     * @throws RuntimeException if the input is invalid.
     */
    public static int processRentalDays (String enteredRentalDays) throws RuntimeException{
        int rentalDaysNum = 0;

        try{
            rentalDaysNum = Integer.parseInt(enteredRentalDays);
        }catch(NumberFormatException e){
            throw new RuntimeException("A non-numeric entry for Rental Day Count was entered.  Please restart and enter a whole number amount.");
        }
        if(rentalDaysNum < 1){
            throw new RuntimeException("The Rental Day Amount was not 1 or greater.  Please restart and enter a value that is 1 or greater.");
        }
        return rentalDaysNum;
    }

    /**
     * Parses user input of discount percentage.
     * @return The amount of the discount percentage.
     * @throws RuntimeException if the entered amount is non-numeric or not between 0-100 inclusively
     */
    public static int processDiscountPercentage (String enteredDiscountAmount) throws RuntimeException{
        int discountAmountNum = 0;

        try{
            discountAmountNum = Integer.parseInt(enteredDiscountAmount);
        }catch(NumberFormatException e){
            throw new RuntimeException("An non-numeric entry for Discount Amount was entered.  The entry should be a whole number between 0 and 100 inclusively.  "
                + "Please restart and enter a valid amount.");
        }
        if(discountAmountNum < 0 || discountAmountNum > 100){
            throw new RuntimeException("The Discount Amount was not between 0 and 100 inclusively.  Please restart and enter a value between 0 and 100.");
        }
        return discountAmountNum;
    }

    /**
     * Parses user input for the checkout date.
     * @return A {@link LocalDate} object containing the date the tool will be checkout out on
     * @throws DateTimeParseException when date is not in a readable format
     */
    public static LocalDate processCheckoutDate (String enteredCheckoutDate) throws DateTimeParseException{
        LocalDate checkoutDate = null;
        try{
            checkoutDate = LocalDate.parse(enteredCheckoutDate, DateTimeFormatter.ofPattern("M/d/yy", Locale.ENGLISH));
        }catch(DateTimeParseException e){
            System.out.println("The entered checkout date could not be parsed.  Please ensure the date is entered in 'mm/dd/yy' format.");
            throw e;
        }
        return checkoutDate;
    }

    public RentalAgreement getRentalAgreement () {
        return rentalAgreement;
    }
}
