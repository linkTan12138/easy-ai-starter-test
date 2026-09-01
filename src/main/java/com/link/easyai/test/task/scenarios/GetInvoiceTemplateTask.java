package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求发票模板。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_INVOICE_TEMPLATE",
        name = "请求发票模板",
        description = "索取发票模板文件（空白模板）",
        triggers = {"发票模板"})
public class GetInvoiceTemplateTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetInvoiceTemplateTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetInvoiceTemplateTask] 意图识别成功，进入场景: GET_INVOICE_TEMPLATE");
        Map<String, Object> data = Map.of("taskType", "GET_INVOICE_TEMPLATE", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求发票模板）", "GET_INVOICE_TEMPLATE");
        return TaskResult.success(message, data);
    }
}
