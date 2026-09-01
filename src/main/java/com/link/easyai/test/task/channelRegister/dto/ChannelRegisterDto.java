package com.link.easyai.test.task.channelRegister.dto;

import com.link.easyai.starter.engine.annotation.AiExtract;
import com.link.easyai.starter.engine.annotation.AiField;
import com.link.easyai.starter.engine.annotation.AiMapping;
import com.link.easyai.starter.engine.annotation.AiTaskParam;
import com.link.easyai.starter.engine.annotation.AiValid;
import com.link.easyai.starter.engine.annotation.Mapping;
import com.link.easyai.test.validator.ChannelValidator;
import com.link.easyai.test.validator.PhoneValidator;
import lombok.Data;

/**
 * 渠道登记任务参数 DTO（AiMapping 测试场景）。
 * <p>
 * 测试场景覆盖 {@code @AiMapping} 的全部 source 表达式：
 * <ul>
 *   <li>{@code $value}：标准化/校验后的值</li>
 *   <li>{@code $rawValue}：LLM 原始抽取值</li>
 *   <li>{@code $data.xxx}：Normalizer 解析时写入 data 的业务数据</li>
 *   <li>字面字符串：固定常量</li>
 * </ul>
 * <p>
 * 两个字段（channelName、phone）收集完成后，映射引擎会将结果组装成
 * 扁平参数 map 传给 Task 执行器（context.getParameters()）。
 */
@Data
@AiTaskParam(type = "CHANNEL_REGISTER")
public class ChannelRegisterDto {

    /**
     * 渠道名称，必填。
     * 演示三种映射来源：
     * <ul>
     *   <li>{@code $data.channelCode} → channelCode：ChannelNormalizer 标准化时解析出的渠道编码</li>
     *   <li>{@code $value} → channelName：标准化后的渠道名称（原样保留）</li>
     *   <li>{@code "MANUAL"} → channelSource：字面常量，标识登记来源</li>
     * </ul>
     */
    @AiField(name = "渠道名称", required = true, normalize = "CHANNEL")
    @AiExtract(
            description = "货代公司提供给客户的物流服务渠道名称或渠道代码",
            examples = {"U009-HKUPS红单5000价", "D011", "C260518005"},
            rules = {
                    "提取用户明确提供的物流渠道名称或渠道代码",
                    "保持用户原始表述完整，不翻译、不改写、不缩短",
                    "渠道名称通常包含始发地、承运商、服务类型、目的地等要素，常以短码开头",
                    "类似D011、C260518005的字母数字组合，在上下文明确表示为渠道时，直接作为渠道提取",
                    "无法明确识别渠道时，不要猜测或编造"
            }
    )
    @AiValid(by = ChannelValidator.class)
    @AiMapping({
            @Mapping(target = "channelCode", source = "$data.channelCode"),
            @Mapping(target = "channelId", source = "$data.channelId"),
            @Mapping(target = "channelName", source = "$value"),
            @Mapping(target = "channelSource", source = "MANUAL")
    })
    private String channelName;

    /**
     * 联系电话，必填。
     * 演示 $value 与 $rawValue 的区别：
     * <ul>
     *   <li>{@code $value} → phone：PhoneValidator 校验通过后标准化为纯数字（如 13800138000）</li>
     *   <li>{@code $rawValue} → phoneRaw：LLM 抽取的原始值（如 138-0013-8000）</li>
     * </ul>
     */
    @AiField(name = "联系电话", required = true)
    @AiExtract(
            description = "客户的联系电话或手机号码",
            examples = {"13800138000", "138-0013-8000", "联系电话：13800138000"},
            rules = {
                    "提取用户明确提供的联系电话",
                    "保留用户提供的电话号码原始内容，不要修改",
                    "不要将订单号、渠道编码、邮编等数字信息识别为联系电话",
                    "无法明确识别联系电话时，不要猜测或编造"
            }
    )
    @AiValid(by = PhoneValidator.class)
    @AiMapping({
            @Mapping(target = "phone", source = "$value"),
            @Mapping(target = "phoneRaw", source = "$rawValue")
    })
    private String phone;
}
