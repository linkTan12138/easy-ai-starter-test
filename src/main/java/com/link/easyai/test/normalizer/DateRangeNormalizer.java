package com.link.easyai.test.normalizer;

import com.link.easyai.starter.engine.context.FieldContext;
import com.link.easyai.starter.engine.normalization.FieldNormalizer;
import com.link.easyai.starter.engine.normalization.NormalizationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间范围标准化器。
 * <p>
 * 将用户输入的时间范围表达解析为 startTime / endTime，存入 data 中：
 * <ul>
 *   <li>相对时间：今天、昨天、明天、这周、上周、下周、本月、上个月、下个月</li>
 *   <li>自定义范围：2026-08-01至2026-08-31、2026年8月1日到8月31日、8月1号-8月31号</li>
 * </ul>
 * <p>
 * value 保留用户原始表达，data 中存解析后的时间范围：
 * <pre>
 * {
 *   "startTime": "2026-08-01 00:00:00",
 *   "endTime": "2026-08-31 23:59:59",
 *   "rangeType": "CUSTOM",
 *   "rawValue": "2026-08-01至2026-08-31"
 * }
 * </pre>
 * <p>
 * type() = "DATE_RANGE"，与 @AiField(normalize = "DATE_RANGE") 对应。
 */
@Component
public class DateRangeNormalizer implements FieldNormalizer {

    private static final Logger log = LoggerFactory.getLogger(DateRangeNormalizer.class);

    private static final DateTimeFormatter DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** 匹配 "日期 分隔符 日期" 模式，分隔符支持：至、到、-、~、—、-- */
    private static final Pattern RANGE_PATTERN = Pattern.compile(
            "(\\d{4}[-/年.]\\d{1,2}[-/月.]\\d{1,2}日?)" +
            "\\s*(?:至|到|-|~|—|--|至到)\\s*" +
            "(\\d{4}[-/年.]\\d{1,2}[-/月.]\\d{1,2}日?)"
    );

    /** 匹配只有月日的范围，如 "8月1号到8月31号" */
    private static final Pattern MONTH_DAY_RANGE_PATTERN = Pattern.compile(
            "(\\d{1,2})月(\\d{1,2})[日号]?" +
            "\\s*(?:至|到|-|~|—|--|至到)\\s*" +
            "(?:(\\d{1,2})月)?(\\d{1,2})[日号]?"
    );

    @Override
    public String type() {
        return "DATE_RANGE";
    }

    @Override
    public NormalizationResult normalize(Object value, FieldContext context, Map<String, Object> params) {
        if (value == null) {
            return NormalizationResult.fail("时间范围为空");
        }

        String raw = String.valueOf(value).trim();
        log.info("[DateRangeNormalizer] 原始表达: {}", raw);

        LocalDate today = LocalDate.now();

        // 1. 尝试相对时间解析
        NormalizationResult relative = parseRelativeTime(raw, today);
        if (relative != null) {
            return relative;
        }

        // 2. 尝试自定义范围解析（完整日期）
        NormalizationResult custom = parseCustomRange(raw);
        if (custom != null) {
            return custom;
        }

        // 3. 尝试只有月日的范围
        NormalizationResult monthDay = parseMonthDayRange(raw, today);
        if (monthDay != null) {
            return monthDay;
        }

        log.warn("[DateRangeNormalizer] 无法识别的时间范围: {}", raw);
        return NormalizationResult.fail("无法识别的时间范围: " + raw
                + "，请使用如 今天、这周、2026-08-01至2026-08-31 等格式");
    }

    /**
     * 解析相对时间表达。
     */
    private NormalizationResult parseRelativeTime(String raw, LocalDate today) {
        LocalDateTime start, end;
        String rangeType;

        switch (raw) {
            case "今天":
            case "今日":
                start = today.atStartOfDay();
                end = today.atTime(23, 59, 59);
                rangeType = "TODAY";
                break;
            case "昨天":
            case "昨日":
                LocalDate yesterday = today.minusDays(1);
                start = yesterday.atStartOfDay();
                end = yesterday.atTime(23, 59, 59);
                rangeType = "YESTERDAY";
                break;
            case "明天":
            case "明日":
                LocalDate tomorrow = today.plusDays(1);
                start = tomorrow.atStartOfDay();
                end = tomorrow.atTime(23, 59, 59);
                rangeType = "TOMORROW";
                break;
            case "这周":
            case "本周":
            case "这一周":
                start = today.with(DayOfWeek.MONDAY).atStartOfDay();
                end = today.with(DayOfWeek.SUNDAY).atTime(23, 59, 59);
                rangeType = "THIS_WEEK";
                break;
            case "上周":
            case "上一周":
                LocalDate lastWeek = today.minusWeeks(1);
                start = lastWeek.with(DayOfWeek.MONDAY).atStartOfDay();
                end = lastWeek.with(DayOfWeek.SUNDAY).atTime(23, 59, 59);
                rangeType = "LAST_WEEK";
                break;
            case "下周":
            case "下一周":
                LocalDate nextWeek = today.plusWeeks(1);
                start = nextWeek.with(DayOfWeek.MONDAY).atStartOfDay();
                end = nextWeek.with(DayOfWeek.SUNDAY).atTime(23, 59, 59);
                rangeType = "NEXT_WEEK";
                break;
            case "本月":
            case "这个月":
                start = today.withDayOfMonth(1).atStartOfDay();
                end = today.with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);
                rangeType = "THIS_MONTH";
                break;
            case "上个月":
            case "上月":
                LocalDate lastMonth = today.minusMonths(1);
                start = lastMonth.withDayOfMonth(1).atStartOfDay();
                end = lastMonth.with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);
                rangeType = "LAST_MONTH";
                break;
            case "下个月":
            case "下月":
                LocalDate nextMonth = today.plusMonths(1);
                start = nextMonth.withDayOfMonth(1).atStartOfDay();
                end = nextMonth.with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);
                rangeType = "NEXT_MONTH";
                break;
            default:
                return null;
        }

        log.info("[DateRangeNormalizer] 相对时间解析: {} -> {} ~ {}", raw, start, end);
        return buildSuccess(raw, start, end, rangeType);
    }

    /**
     * 解析自定义日期范围（完整日期）。
     */
    private NormalizationResult parseCustomRange(String raw) {
        Matcher matcher = RANGE_PATTERN.matcher(raw);
        if (!matcher.find()) {
            return null;
        }

        LocalDate startDate = parseDate(matcher.group(1));
        LocalDate endDate = parseDate(matcher.group(2));

        if (startDate == null || endDate == null) {
            return null;
        }

        if (startDate.isAfter(endDate)) {
            return NormalizationResult.fail("开始日期不能晚于结束日期");
        }

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        log.info("[DateRangeNormalizer] 自定义范围解析: {} -> {} ~ {}", raw, start, end);
        return buildSuccess(raw, start, end, "CUSTOM");
    }

    /**
     * 解析只有月日的范围（默认当前年份）。
     */
    private NormalizationResult parseMonthDayRange(String raw, LocalDate today) {
        Matcher matcher = MONTH_DAY_RANGE_PATTERN.matcher(raw);
        if (!matcher.find()) {
            return null;
        }

        try {
            int startMonth = Integer.parseInt(matcher.group(1));
            int startDay = Integer.parseInt(matcher.group(2));
            int endMonth = matcher.group(3) != null
                    ? Integer.parseInt(matcher.group(3))
                    : startMonth;
            int endDay = Integer.parseInt(matcher.group(4));

            int year = today.getYear();
            LocalDate startDate = LocalDate.of(year, startMonth, startDay);
            LocalDate endDate = LocalDate.of(year, endMonth, endDay);

            if (startDate.isAfter(endDate)) {
                return NormalizationResult.fail("开始日期不能晚于结束日期");
            }

            LocalDateTime start = startDate.atStartOfDay();
            LocalDateTime end = endDate.atTime(23, 59, 59);

            log.info("[DateRangeNormalizer] 月日范围解析: {} -> {} ~ {}", raw, start, end);
            return buildSuccess(raw, start, end, "CUSTOM");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解析各种格式的日期字符串。
     */
    private LocalDate parseDate(String dateStr) {
        String cleaned = dateStr.replace("年", "-")
                .replace("月", "-")
                .replace("日", "")
                .replace(".", "-")
                .replace("/", "-");

        // 补全年份（如果只有月日，默认当前年）
        String[] parts = cleaned.split("-");
        if (parts.length == 2) {
            cleaned = LocalDate.now().getYear() + "-" + cleaned;
        }

        try {
            return LocalDate.parse(cleaned);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * 构建成功结果。
     */
    private NormalizationResult buildSuccess(String raw,
                                              LocalDateTime start,
                                              LocalDateTime end,
                                              String rangeType) {
        Map<String, Object> data = new HashMap<>();
        data.put("startTime", start.format(DATETIME_FORMAT));
        data.put("endTime", end.format(DATETIME_FORMAT));
        data.put("rangeType", rangeType);
        data.put("rawValue", raw);
        data.put("normalizedBy", "DateRangeNormalizer");
        return NormalizationResult.success(raw, data);
    }
}
