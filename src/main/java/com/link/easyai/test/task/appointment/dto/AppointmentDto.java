package com.link.easyai.test.task.appointment.dto;

import com.link.easyai.starter.engine.annotation.*;
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
    @AiExtract(
            examples = {"深圳上汽有限公司", "Justin"},
            rules = {
                    "客户姓名可以是公司名称或个人姓名",
                    "公司客户提取完整公司名称，通常包含“公司”“有限公司”“集团”等企业名称特征",
                    "个人客户提取完整姓名，支持中文姓名和英文姓名",
                    "英文姓名保持原始拼写，不翻译、不改写",
                    "仅提取客户名称本身，不包含地址、电话、订单号、客户编号等无关信息",
                    "存在多个名称时，根据上下文判断哪个名称实际指代客户",
                    "无法明确识别时，不猜测、不编造"
            }
    )
    private String customerName;

    /**
     * 联系电话，必填。
     * 先使用 PhoneValidator 校验手机号格式，
     * 校验通过后由 PhoneNormalizer 标准化为纯数字格式。
     */
    @AiField(name = "联系电话", normalize = "PHONE")
    @AiValid(by = PhoneValidator.class)
    private String phone;

    /**
     * 预约日期，必填。
     * 用户可输入各种格式（2024年1月1日、2024/1/1、2024-1-1），
     * 由 DateNormalizer 统一标准化为 yyyy-MM-dd 格式。
     */
    @AiField(name = "预约日期", normalize = "DATE")
    private String appointmentDate;

    @AiField(name = "收货渠道", required = true)
    @AiExtract(description = "收货渠道",
            examples = {"U009-HKUPS红单5000价","D011","C260518005"},
            rules={"收货渠道是货代公司整合资源后提供给客户的服务套餐，除了提供价格和服务，还会有约束和规定。",
            "渠道名称一般以始发地、承运商名称、承运商服务、专线目的国、材积除数、危险品服务、清关服务、派送方式等要素组合，渠道名常以“价”作为后缀，有时以短码作为前缀"})
    private String channelName;

    @AiField(name = "确认信息（请回复“确认”以进行下一步操作。）", required = true)
    @AiPremise("customerName != null || channelName != null")
    private Confirm confirm;
}
