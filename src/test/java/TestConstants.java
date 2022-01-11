public class TestConstants {
    public static final String discountAmountOutOfRangeMessage = "The Discount Amount was not between 0 and 100 inclusively.  Please restart and enter a value between 0 and 100.";
    public static final Tool stihlChainsaw = new Tool.Builder("CHNS").setToolType(Tool.ToolType.CHAINSAW).setBrand("Stihl").build();
    public static final Tool wernerLadder = new Tool.Builder("LADW").setToolType(Tool.ToolType.LADDER).setBrand("Werner").build();
    public static final Tool dewaltJackhammer = new Tool.Builder("JAKD").setToolType(Tool.ToolType.JACKHAMMER).setBrand("DeWalt").build();
    public static final Tool ridgidJackhammer = new Tool.Builder("JAKR").setToolType(Tool.ToolType.JACKHAMMER).setBrand("Ridgid").build();
}
