package com.link.easyai.test.normalizer;

import com.link.easyai.starter.engine.context.FieldContext;
import com.link.easyai.starter.engine.normalization.FieldNormalizer;
import com.link.easyai.starter.engine.normalization.NormalizationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 电话号码标准化器。
 * <p>
 * 将用户输入的各种电话号码格式统一标准化为纯数字格式：
 * <ul>
 *   <li>138-0013-8000 → 13800138000</li>
 *   <li>138 0013 8000 → 13800138000</li>
 *   <li>(86)13800138000 → 13800138000（去掉国家代码前缀）</li>
 *   <li>+86 138 0013 8000 → 13800138000</li>
 * </ul>
 * <p>
 * type() = "PHONE"，与 @AiField(normalize = "PHONE") 对应。
 */
@Component
public class PhoneNormalizer implements FieldNormalizer {

    private static final Logger log = LoggerFactory.getLogger(PhoneNormalizer.class);

    /** 匹配中国大陆手机号的正则（标准化后应为11位数字，以1开头） */
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^1\\d{10}$");

    @Override
    public String type() {
        return "PHONE";
    }

    @Override
    public NormalizationResult normalize(Object value, FieldContext context, Map<String, Object> params) {
        if (value == null) {
            return NormalizationResult.fail("电话号码为空");
        }

        String raw = String.valueOf(value).trim();
        log.info("[PhoneNormalizer] 原始电话号码: {}", raw);

        // 1. 去掉国家代码前缀（+86、0086、86）
        String normalized = raw;
        if (normalized.startsWith("+86")) {
            normalized = normalized.substring(3);
        } else if (normalized.startsWith("0086")) {
            normalized = normalized.substring(4);
        } else if (normalized.startsWith("86") && normalized.length() > 11) {
            normalized = normalized.substring(2);
        }

        // 2. 去掉所有非数字字符（横线、空格、括号等）
        normalized = normalized.replaceAll("[^0-9]", "");

        // 3. 校验标准化后的格式
        if (!MOBILE_PATTERN.matcher(normalized).matches()) {
            log.warn("[PhoneNormalizer] 标准化后格式不正确: {}", normalized);
            return NormalizationResult.fail("电话号码格式不正确，标准化后为: " + normalized);
        }

        log.info("[PhoneNormalizer] 标准化结果: {} -> {}", raw, normalized);

        // 返回标准化结果，同时在 data 中记录原始值，便于追溯
        return NormalizationResult.success("我管你这那的！", Map.of(
                "rawValue", raw,
                "normalizedBy", "PhoneNormalizer"
        ));
    }
}
