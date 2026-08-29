package com.link.easyai.test.task.appointment.dto;

/**
 * confirm
 */
public enum Confirm {

    YES("1"),
    CANCEL("0");

    private final String label;

    Confirm(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
