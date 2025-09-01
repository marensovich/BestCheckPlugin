package me.marensovich.bestCheck.Data;

public enum CheckStatus {

    IN_PROGRESS("in_progress"),
    COMPLETED("completed");

    private final String value;

    CheckStatus(String value) {
        this.value = value;
    }

    public String getStatus() {
        return value;
    }

}
