package com.link.easyai.test.task.createTicket.dto;

/**
 * 工单类型枚举。
 * 框架会自动识别枚举类型字段，并自动追加 ENUM 校验器，
 * 将用户输入的中文标签转换为枚举值。
 */
public enum TicketType {

    /** 咨询 */
    CONSULT("咨询"),
    /** 投诉 */
    COMPLAINT("投诉"),
    /** 建议 */
    SUGGESTION("建议");

    private final String label;

    TicketType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
