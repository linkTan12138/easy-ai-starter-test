package com.link.easyai.test.task.intenttest;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：发票申请。
 * <p>
 * 测试用户表达相关需求时，意图识别能否准确进入此场景。
 * 纯动作场景，无需参数收集。
 */
@AiTask(value = "INVOICE_REQUEST",
        name = "发票申请",
        description = "申请开具发票，支持增值税普通发票和专用发票",
        triggers = {"发票", "开发票", "申请发票", "我要发票", "开票"})
public class InvoiceRequestTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(InvoiceRequestTask.class);

    @Override
    public String type() {
        return "INVOICE_REQUEST";
    }

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[InvoiceRequestTask] 意图识别成功，进入场景: INVOICE_REQUEST");
        Map<String, Object> data = Map.of("taskType", "INVOICE_REQUEST", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（发票申请）", "INVOICE_REQUEST");
        return TaskResult.success(message, data);
    }
}
