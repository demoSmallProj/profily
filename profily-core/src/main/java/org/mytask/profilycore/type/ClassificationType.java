package org.mytask.profilycore.type;

public enum ClassificationType {
    AFTERNOON_PERSON("Afternoon Person"),
    BIG_SPENDER("Big Spender"),
    BIG_TICKET_SPENDER("Big Ticket Spender"),
    FAST_SPENDER("Fast Spender"),
    MORNING_PERSON("Morning Person"),
    POTENTIAL_SAVER("Potential Saver"),
    POTENTIAL_LOAN("Potential Loan");

    private String description;

    ClassificationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
