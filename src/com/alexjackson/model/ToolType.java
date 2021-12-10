package com.alexjackson.model;

public enum ToolType {
    LADDER(1.99, "Ladder", true, true, false),
    CHAINSAW(1.49, "Chainsaw", true, false, true),
    JACKHAMMER(2.99, "Jackhammer", true, false, false);

    public final double dailyCharge;
    public final String printName;
    public final boolean weekdayCharge;
    public final boolean weekendCharge;
    public final boolean holidayCharge;

    ToolType(double dailyCharge,
             String printName,
             boolean weekdayCharge,
             boolean weekendCharge,
             boolean holidayCharge) {
        this.dailyCharge = dailyCharge;
        this.printName = printName;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

}
