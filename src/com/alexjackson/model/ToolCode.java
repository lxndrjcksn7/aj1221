package com.alexjackson.model;

public enum ToolCode {
    CHNS(ToolType.CHAINSAW, "Stihl"),
    LADW(ToolType.LADDER, "Werner"),
    JAKD(ToolType.JACKHAMMER, "DeWalt"),
    JAKR(ToolType.JACKHAMMER, "Ridgid");

    public final ToolType type;
    public final String brand;

    ToolCode(ToolType type, String brand) {
        this.type = type;
        this.brand = brand;
    }
}
