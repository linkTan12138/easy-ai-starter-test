package com.link.easyai.test.normalizer;

import com.link.easyai.starter.engine.context.FieldContext;
import com.link.easyai.starter.engine.normalization.FieldNormalizer;
import com.link.easyai.starter.engine.normalization.NormalizationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * 日期标准化器。
 * <p>
 * 将用户输入的各种日期格式统一标准化为 yyyy-MM-dd 格式：
 * <ul>
 *   <li>2024年1月1日 → 2024-01-01</li>
 *   <li>2024/1/1 → 2024-01-01</li>
 *   <li>2024-1-1 → 2024-01-01</li>
 *   <li>2024.01.01 → 2024-01-01</li>
 *   <li>20240101 → 2024-01-01</li>
 * </ul>
 * <p>
 * type() = "DATE"，与 @AiField(normalize = "DATE") 对应。
 */
@Component
public class DateNormalizer implements FieldNormalizer {

    private static final Logger log = LoggerFactory.getLogger(DateNormalizer.class);

    /** 支持的输入日期格式 */
    private static final DateTimeFormatter[] INPUT_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/M/d"),
            DateTimeFormatter.ofPattern("yyyy.M.d"),
            DateTimeFormatter.ofPattern("yyyy年M月d日"),
            DateTimeFormatter.ofPattern("yyyyMMdd"),
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("d/M/yyyy")
    };

    /** 输出标准格式 */
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String type() {
        return "DATE";
    }

    @Override
    public NormalizationResult normalize(Object value, FieldContext context, Map<String, Object> params) {
        if (value == null) {
            return NormalizationResult.fail("日期为空");
        }

        String raw = String.valueOf(value).trim();
        log.info("[DateNormalizer] 原始日期: {}", raw);

        // 尝试用各种格式解析
        LocalDate date = null;
        for (DateTimeFormatter format : INPUT_FORMATS) {
            try {
                date = LocalDate.parse(raw, format);
                break;
            } catch (DateTimeParseException ignored) {
                // 尝试下一个格式
            }
        }

        if (date == null) {
            log.warn("[DateNormalizer] 无法解析日期: {}", raw);
            return NormalizationResult.fail("无法识别的日期格式: " + raw + "，请使用如 2024-01-01、2024年1月1日 等格式");
        }

        String normalized = date.format(OUTPUT_FORMAT);
        log.info("[DateNormalizer] 标准化结果: {} -> {}", raw, normalized);

        // 返回标准化结果，同时在 data 中记录原始值和解析出的年月日
        return NormalizationResult.success(normalized, Map.of(
                "rawValue", raw,
                "year", date.getYear(),
                "month", date.getMonthValue(),
                "day", date.getDayOfMonth(),
                "normalizedBy", "DateNormalizer"
        ));
    }
}
