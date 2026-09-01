package com.link.easyai.test.validator;

import com.link.easyai.starter.engine.context.FieldContext;
import com.link.easyai.starter.engine.validation.AiValidator;
import com.link.easyai.starter.engine.validation.FieldValidator;
import com.link.easyai.starter.engine.validation.ValidationResult;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 渠道名称校验器（AiMapping 测试场景专用）。
 * <p>
 * 只负责<b>合法性校验</b>（非空 + 编码前缀格式），不负责解析。
 * 渠道编码的提取逻辑放在 {@code ChannelNormalizer}（标准化阶段），
 * 职责分离：Validator 判定"是否合法"，Normalizer 完成"解析/转换"。
 */
@AiValidator("CHANNEL")
public class ChannelValidator implements FieldValidator {

    /** 渠道编码格式：字母开头 + 2~9 位数字（如 U009、D011、C260518005） */
    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Za-z]\\d{2,9}");

    @Override
    public String type() {
        return "CHANNEL";
    }

    @Override
    public ValidationResult validate(Object rawValue, FieldContext context, Map<String, Object> params) {
        if (rawValue == null) {
            return ValidationResult.fail(null, "CHANNEL_EMPTY", "渠道名称不能为空");
        }
        String name = String.valueOf(rawValue).trim();
        if (name.isEmpty()) {
            return ValidationResult.fail(rawValue, "CHANNEL_EMPTY", "渠道名称不能为空");
        }
        if (!CODE_PATTERN.matcher(name).find()) {
            return ValidationResult.fail(rawValue, "CHANNEL_INVALID",
                    String.format("渠道名称格式不正确：%s（应以字母+数字短码开头，如 U009-HKUPS红单5000价、D011）", name));
        }
        // 只做校验，不解析编码；编码由 ChannelNormalizer 在标准化阶段提取
        return ValidationResult.success(rawValue, name, name, null);
    }
}
