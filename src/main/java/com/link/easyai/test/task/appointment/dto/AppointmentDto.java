package com.link.easyai.test.task.appointment.dto;

import com.link.easyai.starter.engine.annotation.AiField;
import com.link.easyai.starter.engine.annotation.AiTaskParam;
import com.link.easyai.starter.engine.annotation.AiValid;
import com.link.easyai.test.validator.PhoneValidator;
import lombok.Data;

/**
 * 预约登记任务参数 DTO。
 * <p>
 * 测试场景覆盖：
 * <ul>
 *   <li>字段标准化（Normalization）：phone 字段配置 normalize="PHONE"，
 *       将用户输入的各种格式（138-0013-8000、138 0013 8000、(86)13800138000）
 *       统一标准化为纯数字格式 13800138000</li>
 *   <li>字段标准化（Normalization）：appointmentDate 字段配置 normalize="DATE"，
 *       将用户输入的各种日期格式（2024年1月1日、2024/1/1、2024-1-1）
 *       统一标准化为 yyyy-MM-dd 格式 2024-01-01</li>
 *   <li>自定义校验器：phone 使用 PhoneValidator 验证手机号格式</li>
 *   <li>多轮参数收集：3个字段，分多轮收集</li>
 *   <li>Task 执行：所有字段收集完成后执行 AppointmentTask</li>
 * </ul>
 */
@Data
@AiTaskParam(type = "APPOINTMENT")
public class AppointmentDto {

    /**
     * 客户姓名，必填。
     */
    @AiField(name = "客户姓名", required = true)
    private String customerName;

    /**
     * 联系电话，必填。
     * 先使用 PhoneValidator 校验手机号格式，
     * 校验通过后由 PhoneNormalizer 标准化为纯数字格式。
     */
    @AiField(name = "联系电话", required = true, normalize = "PHONE")
    @AiValid(by = PhoneValidator.class)
    private String phone;

    /**
     * 预约日期，必填。
     * 用户可输入各种格式（2024年1月1日、2024/1/1、2024-1-1），
     * 由 DateNormalizer 统一标准化为 yyyy-MM-dd 格式。
     */
    @AiField(name = "预约日期", required = true, normalize = "DATE")
    private String appointmentDate;
}
