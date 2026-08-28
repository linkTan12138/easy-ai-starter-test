package com.link.easyai.test.task.createTicket.dto;

/**
 * 工单优先级枚举（非必填字段）。
 */
public enum Priority {

    HIGH("高"),
    MEDIUM("中"),
    LOW("低");

    private final String label;

    Priority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
