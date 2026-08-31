package com.link.easyai.test.task.queryBill;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.state.FieldState;
import com.link.easyai.starter.engine.state.TaskState;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询账单任务。
 * <p>
 * 当账单时间范围收集完成后，由框架自动调用。
 * 从 FieldState.data 中取 startTime / endTime，模拟查询账单并返回假数据。
 * <p>
 * 演示了如何从 Normalizer 输出的 data 中获取附加数据（时间范围）。
 */
@AiTask(value = "QUERY_BILL",
        name = "查询账单",
        description = "根据时间范围查询用户账单",
        triggers = {"查账单", "账单", "查询账单", "我的账单", "消费记录"})
public class QueryBillTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(QueryBillTask.class);


    @Override
    public TaskResult execute(ExecuteContext context) {
        TaskState state = context.getState();

        // 从 FieldState.data 中取时间范围（DateRangeNormalizer 输出）
        FieldState rangeField = state.getField("billDateRange");
        String startTime = null;
        String endTime = null;
        String rangeType = null;
        String rawValue = null;

        if (rangeField != null && rangeField.getData() != null) {
            Map<String, Object> data = rangeField.getData();
            startTime = (String) data.get("startTime");
            endTime = (String) data.get("endTime");
            rangeType = (String) data.get("rangeType");
            rawValue = (String) data.get("rawValue");
        }

        log.info("[QueryBillTask] 查询账单, 原始表达={}, 范围={} ~ {}, 类型={}",
                rawValue, startTime, endTime, rangeType);

        // 模拟查询账单，生成假数据
        List<Map<String, Object>> bills = generateMockBills(startTime, endTime);

        double totalAmount = bills.stream()
                .mapToDouble(b -> ((Number) b.get("amount")).doubleValue())
                .sum();

        // 构建返回消息
        String message = String.format(
                "账单查询完成！\n" +
                "时间范围：%s ~ %s\n" +
                "账单笔数：%d 笔\n" +
                "总金额：¥%.2f\n\n" +
                "账单明细：\n%s",
                startTime, endTime, bills.size(), totalAmount,
                formatBillList(bills));

        Map<String, Object> result = Map.of(
                "startTime", startTime != null ? startTime : "",
                "endTime", endTime != null ? endTime : "",
                "rangeType", rangeType != null ? rangeType : "UNKNOWN",
                "billCount", bills.size(),
                "totalAmount", totalAmount,
                "bills", bills
        );

        log.info("[QueryBillTask] 账单查询完成, 笔数={}, 总金额={}", bills.size(), totalAmount);

        return TaskResult.success(message, result);
    }

    /**
     * 生成模拟账单数据。
     */
    private List<Map<String, Object>> generateMockBills(String startTime, String endTime) {
        List<Map<String, Object>> bills = new ArrayList<>();

        // 根据时间范围生成不同数量的假账单
        int count = 3 + (int) (Math.random() * 5);
        String[] categories = {"餐饮", "交通", "购物", "娱乐", "通讯", "医疗"};
        String[] merchants = {"星巴克", "美团外卖", "滴滴出行", "京东商城", "中国移动", "淘宝"};

        for (int i = 0; i < count; i++) {
            double amount = Math.round((10 + Math.random() * 500) * 100.0) / 100.0;
            bills.add(Map.of(
                    "billNo", "BL" + (100000 + i),
                    "category", categories[i % categories.length],
                    "merchant", merchants[i % merchants.length],
                    "amount", amount,
                    "status", "PAID",
                    "createTime", startTime != null ? startTime : "2026-08-01 10:00:00"
            ));
        }

        return bills;
    }

    /**
     * 格式化账单列表为文本。
     */
    private String formatBillList(List<Map<String, Object>> bills) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bills.size(); i++) {
            Map<String, Object> bill = bills.get(i);
            sb.append(String.format("  %d. [%s] %s - ¥%.2f (%s)\n",
                    i + 1,
                    bill.get("category"),
                    bill.get("merchant"),
                    ((Number) bill.get("amount")).doubleValue(),
                    bill.get("billNo")));
        }
        return sb.toString();
    }
}
