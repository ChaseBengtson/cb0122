public class Tool {
    private String toolCode;
    private ToolType toolType;
    private String brand;

    /**
     * An enum for describing the different types of tools available for rent.  Includes name, price to rent daily, and
     * when the daily charge is applied.
     */
    enum ToolType {
        LADDER("Ladder", 1.99f, true, true, false),
        CHAINSAW("Chainsaw", 1.49f, true, false, true),
        JACHAMMER("Jackhammer", 2.99f, true, false, false);

        private final String name;
        private final float dailyCharge;
        private final boolean isChargedWeekday;
        private final boolean isChargedWeekend;
        private final boolean isChargedHoliday;
        public String getName() {return name;}
        public float getDailyCharge() {return dailyCharge;}
        public boolean isChargedWeekday () {return isChargedWeekday;}
        public boolean isChargedWeekend () {return isChargedWeekend;}
        public boolean isChargedHoliday () {return isChargedHoliday;}
        ToolType(String name, float dailyCharge, boolean isChargedWeekday, boolean isChargedWeekend, boolean isChargedHoliday){
            this.name = name;
            this.dailyCharge = dailyCharge;
            this.isChargedWeekday = isChargedWeekday;
            this.isChargedWeekend = isChargedWeekend;
            this.isChargedHoliday = isChargedHoliday;
        }
    }

    /**
     * Constructor for {@link Tool} class.
     * @param builder The {@link Tool.Builder} object created to build the Tool from.
     */
    private Tool(Builder builder){
        this.toolCode = builder.toolCode;
        this.toolType = builder.toolType;
        this.brand = builder.brand;
    }

    public boolean isChargedWeekday(){
        return this.toolType.isChargedWeekday;
    }

    public boolean isChargedWeekend(){
        return this.toolType.isChargedWeekday;
    }

    public boolean isChargedHoliday(){
        return this.toolType.isChargedHoliday;
    }

    public String getToolCode () {
        return toolCode;
    }

    public ToolType getToolType () {
        return toolType;
    }

    public String getBrand () {
        return brand;
    }

    /**
     * Builder inner class to be used for creating {@link Tool} objects.
     */
    public static class Builder {
        private String toolCode;
        private ToolType toolType;
        private String brand;

        public Builder setToolCode (String toolCode) {
            this.toolCode = toolCode;
            return this;
        }

        public Builder setToolType (ToolType toolType) {
            this.toolType = toolType;
            return this;
        }

        public Builder setBrand (String brand) {
            this.brand = brand;
            return this;
        }

        public Tool build(){
            return new Tool(this);
        }
    }

}
