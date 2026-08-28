package com.link.easyai.test.validator;

import com.link.easyai.starter.engine.context.FieldContext;
import com.link.easyai.starter.engine.validation.AiValidator;
import com.link.easyai.starter.engine.validation.FieldValidator;
import com.link.easyai.starter.engine.validation.ValidationResult;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 中国大陆手机号校验器。
 * <p>
 * 校验规则：
 * <ul>
 *   <li>11位数字</li>
 *   <li>以1开头</li>
 *   <li>第二位为3-9</li>
 * </ul>
 * 校验通过时，同时去除手机号中的空格、横线等分隔符，返回标准化的值。
 */
@AiValidator("PHONE")
public class PhoneValidator implements FieldValidator {

    /** 中国大陆手机号正则 */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    @Override
    public String type() {
        return "PHONE";
    }

    @Override
    public ValidationResult validate(Object rawValue, FieldContext context, Map<String, Object> params) {
        if (rawValue == null) {
            return ValidationResult.fail(null, "PHONE_EMPTY", "手机号不能为空");
        }

        // 标准化：去除空格、横线、括号等分隔符
        String normalized = String.valueOf(rawValue)
                .replaceAll("[\\s\\-()（）]", "")
                .trim();

        if (normalized.isEmpty()) {
            return ValidationResult.fail(rawValue, "PHONE_EMPTY", "手机号不能为空");
        }

        if (!PHONE_PATTERN.matcher(normalized).matches()) {
            return ValidationResult.fail(rawValue, "PHONE_INVALID",
                    String.format("手机号格式不正确：%s（请输入11位中国大陆手机号）", rawValue));
        }

        // 校验通过，返回标准化后的值
        return ValidationResult.success(rawValue, normalized, normalized, null);
    }
}
