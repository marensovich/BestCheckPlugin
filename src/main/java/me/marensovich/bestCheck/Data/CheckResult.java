package me.marensovich.bestCheck.Data;

public enum CheckResult {

    BANNED("banned"),
    RELEASED("released"),;

    private final String value;

    CheckResult(String value) {
        this.value = value;
    }

    public String getResult(String str) {
        return value;
    }

}
