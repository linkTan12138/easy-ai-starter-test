package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求客户账单。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_CUSTOMER_BILL",
        name = "请求客户账单",
        description = "索取客户的费用账单、对账单",
        triggers = {"账单", "对账单"})
public class GetCustomerBillTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetCustomerBillTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetCustomerBillTask] 意图识别成功，进入场景: GET_CUSTOMER_BILL");
        Map<String, Object> data = Map.of("taskType", "GET_CUSTOMER_BILL", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求客户账单）", "GET_CUSTOMER_BILL");
        return TaskResult.success(message, data);
    }
}
