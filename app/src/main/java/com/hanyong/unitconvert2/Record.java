package com.hanyong.unitconvert2;

public class Record {

    private long id;
    private double from_amount;
    private double to_amount;
    private String from_unit;
    private String to_unit;

    public Record(long id, double from_amount, double to_amount,
                  String from_unit, String to_unit) {
        this.id = id;
        this.from_amount = from_amount;
        this.to_amount = to_amount;
        this.from_unit = from_unit;
        this.to_unit = to_unit;
    }

    public long getId() {
        return id;
    }

    public double getFrom_amount() {
        return from_amount;
    }

    public double getTo_amount() {
        return to_amount;
    }

    public String getFrom_unit() {
        return from_unit;
    }

    public String getTo_unit() {
        return to_unit;
    }
}
