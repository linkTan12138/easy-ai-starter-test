package com.link.easyai.test.task.createTicket.dto;

import com.link.easyai.starter.engine.annotation.AiField;
import com.link.easyai.starter.engine.annotation.AiPremise;
import com.link.easyai.starter.engine.annotation.AiTaskParam;
import com.link.easyai.starter.engine.annotation.AiValid;
import com.link.easyai.test.validator.PhoneValidator;
import lombok.Data;

/**
 * 客服工单创建任务参数 DTO。
 * <p>
 * 测试场景覆盖：
 * <ul>
 *   <li>多轮参数收集：5个字段，分多轮收集</li>
 *   <li>枚举字段：ticketType / priority 自动 ENUM 校验</li>
 *   <li>自定义校验器：phone 使用 PhoneValidator 验证手机号格式</li>
 *   <li>前置依赖：description 依赖 ticketType 先收集</li>
 *   <li>必填/非必填：priority 为非必填字段</li>
 *   <li>Task 执行：所有字段收集完成后执行 CreateTicketTask</li>
 *   <li>PostTask：执行后记录日志</li>
 * </ul>
 */
@Data
@AiTaskParam(type = "CREATE_TICKET")
public class CreateTicketDto {

    /**
     * 工单类型（枚举：咨询/投诉/建议），必填。
     * 枚举字段自动获得 ENUM 校验器，将用户输入的中文标签转换为枚举值。
     */
    @AiField(name = "工单类型(咨询/投诉/建议)", required = true)
    private TicketType ticketType;

    /**
     * 客户姓名，必填。
     */
    @AiField(name = "客户姓名", required = true)
    private String customerName;

    /**
     * 联系电话，必填。
     * 使用自定义 PhoneValidator 校验中国大陆手机号格式。
     */
    @AiField(name = "联系电话", required = true)
    @AiValid(by = PhoneValidator.class)
    private String phone;

    /**
     * 问题描述，必填。
     * 前置依赖：只有 ticketType 收集完成后，才会开始收集 description。
     * 这样可以避免在用户还没说清工单类型时就追问问题描述。
     */
    @AiField(name = "问题描述", required = true)
    @AiPremise("ticketType != null")
    private String description;

    /**
     * 优先级（枚举：高/中/低），非必填。
     * 用户不提供时使用默认值"中"。
     */
    @AiField(name = "优先级（高/中/低）", required = false)
    private Priority priority;
}
