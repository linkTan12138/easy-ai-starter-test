package com.link.easyai.test.task.queryBill.dto;

import com.link.easyai.starter.engine.annotation.AiExtract;
import com.link.easyai.starter.engine.annotation.AiField;
import com.link.easyai.starter.engine.annotation.AiTaskParam;
import lombok.Data;

/**
 * 查询账单任务参数 DTO。
 * <p>
 * 测试场景覆盖：
 * <ul>
 *   <li>上下文变量注入：billDateRange 字段配置 contextVars = {"currentDate"}，
 *       框架自动注入当前日期到抽取 prompt，帮助 LLM 理解"今天"、"这周"等相对日期</li>
 *   <li>时间范围标准化：billDateRange 字段配置 normalize = "DATE_RANGE"，
 *       DateRangeNormalizer 将"今天"、"这周"、"2026-08-01至2026-08-31"等表达
 *       统一解析为 startTime / endTime，存入 FieldState.data</li>
 *   <li>单字段任务：只有一个必填字段，收集完成后直接执行</li>
 *   <li>Task 从 data 取值：QueryBillTask 从 FieldState.data 中取 startTime / endTime 查询账单</li>
 * </ul>
 */
@Data
@AiTaskParam(type = "QUERY_BILL")
public class QueryBillDto {

    /**
     * 账单时间范围，必填。
     * <p>
     * 用户可输入：
     * <ul>
     *   <li>相对时间：今天、昨天、这周、上周、本月、上个月</li>
     *   <li>自定义范围：2026-08-01至2026-08-31、8月1号到8月31号</li>
     * </ul>
     * <p>
     * contextVars = {"currentDate"} 注入当前日期，帮助 LLM 理解相对时间。
     * normalize = "DATE_RANGE" 由 DateRangeNormalizer 解析为 startTime / endTime。
     */
    @AiField(name = "账单时间范围", required = true, normalize = "DATE_RANGE")
    @AiExtract(
            description = "用户要查询的账单时间范围",
            examples = {"今天", "这周", "上个月", "2026-08-01至2026-08-31", "8月1号到8月31号"},
            rules = {
                    "提取用户明确表达的账单查询时间范围",
                    "支持相对时间（今天、昨天、这周、上周、本月、上个月）和自定义范围",
                    "如果用户说'今天的账单'、'查今天的'，则 billDateRange 的值为'今天'",
                    "如果用户说'这周的账单'、'查本周的'，则 billDateRange 的值为'这周'",
                    "保持用户原始表述，不要自行转换为具体日期",
                    "如果用户没有明确时间范围，不要猜测"
            },
            contextVars = {"currentDate"}
    )
    private String billDateRange;
}
