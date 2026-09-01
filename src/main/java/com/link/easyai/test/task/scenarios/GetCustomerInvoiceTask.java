package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求客户发票。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_CUSTOMER_INVOICE",
        name = "请求客户发票",
        description = "索取客户提供的发票",
        triggers = {"客户发票", "原发票", "发票"})
public class GetCustomerInvoiceTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetCustomerInvoiceTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetCustomerInvoiceTask] 意图识别成功，进入场景: GET_CUSTOMER_INVOICE");
        Map<String, Object> data = Map.of("taskType", "GET_CUSTOMER_INVOICE", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求客户发票）", "GET_CUSTOMER_INVOICE");
        return TaskResult.success(message, data);
    }
}
