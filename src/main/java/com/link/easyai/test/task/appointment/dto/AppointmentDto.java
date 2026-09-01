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
            description = "客户的公司名称或个人姓名",
            examples = {
                    "深圳上汽有限公司",
                    "Justin"
            },
            rules = {
                    "客户姓名可以是公司名称，也可以是个人姓名",
                    "公司客户应提取完整的公司名称，例如“深圳上汽有限公司”",
                    "个人客户应提取完整的姓名，支持中文姓名和英文姓名",
                    "英文姓名保持原始拼写，不翻译、不改写",
                    "只提取客户名称本身，不包含地址、电话、订单号、客户编号等其他信息",
                    "存在多个名称时，根据上下文判断实际指代客户的名称",
                    "无法明确识别客户姓名时，不要猜测或编造"
            }
    )
    private String customerName;

    /**
     * 联系电话，必填。
     */
    @AiField(
            name = "联系电话",
            required = true,
            normalize = "PHONE"
    )
    @AiExtract(
            description = "客户的联系电话或手机号码",
            examples = {
                    "13800138000",
                    "138-0013-8000",
                    "联系电话：13800138000"
            },
            rules = {
                    "提取用户明确提供的联系电话",
                    "保留用户提供的电话号码内容，不要自行修改或编造",
                    "如果同时存在多个电话号码，根据上下文判断客户实际使用的联系电话",
                    "不要将订单号、客户编号、邮编等数字信息识别为联系电话",
                    "无法明确识别联系电话时，不要猜测或编造"
            }
    )
    @AiValid(by = PhoneValidator.class)
    private String phone;

    /**
     * 预约日期，必填。
     */
    @AiField(
            name = "预约日期",
            required = true,
            normalize = "DATE"
    )
    @AiExtract(
            description = "客户预约办理业务的日期",
            examples = {
                    "2024年1月1日",
                    "2024/1/1",
                    "2024-01-01"
            },
            rules = {
                    "提取用户明确表达的预约日期",
                    "支持中文日期、斜杠日期、短横线日期等常见日期格式",
                    "日期只提取用户明确表达的日期，不包含时间",
                    "如果用户使用“明天”“后天”“下周一”等相对日期，应根据当前日期计算实际日期",
                    "如果存在多个日期，根据上下文判断实际指代预约的日期",
                    "无法明确识别预约日期时，不要猜测或编造"
            },
            contextVars = {"currentDate"}
    )
    private String appointmentDate;

    /**
     * 收货渠道，必填。
     */
    @AiField(name = "收货渠道", required = true)
    @AiExtract(
            description = "货代公司提供给客户的物流服务渠道名称或渠道代码",
            examples = {
                    "U009-HKUPS红单5000价",
                    "D011",
                    "C260518005"
            },
            rules = {
                    "收货渠道是货代公司提供给客户的具体物流服务套餐或渠道配置",
                    "渠道可以是完整的渠道名称、渠道代码，也可以是代码与名称的组合",
                    "渠道名称通常包含始发地、承运商、服务类型、目的地、材积除数、危险品、清关、派送方式等业务要素",
                    "渠道名称可能包含字母、数字、中文、短横线以及“价”等标识",
                    "提取渠道时必须保留原始完整内容，不翻译、不改写、不缩短、不补充",
                    "如果同时出现渠道代码和渠道名称，应优先提取能够完整标识该渠道的内容",
                    "类似D011、C260518005的字母数字组合，在上下文明确表示为渠道时，应直接作为渠道提取",
                    "不要仅根据承运商名称、运输方式、目的地、价格或服务描述判断为收货渠道",
                    "存在多个候选渠道时，根据上下文判断实际使用的收货渠道",
                    "无法明确识别收货渠道时，不要猜测或编造"
            }
    )
    private String channelName;

    @AiField(
            name = "确认信息（请回复“确认”以进行下一步操作。）",
            required = true
    )
    @AiPremise("customerName != null || channelName != null")
    private Confirm confirm;
}
