package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：发送收款码。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "SEND_PAYMENT_CODE",
        name = "发送收款码",
        description = "要求获取收款码",
        triggers = {"发一下收款码", "支付码给一下", "发一下收款账号", "收款码"})
public class SendPaymentCodeTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(SendPaymentCodeTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[SendPaymentCodeTask] 意图识别成功，进入场景: SEND_PAYMENT_CODE");
        Map<String, Object> data = Map.of("taskType", "SEND_PAYMENT_CODE", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（发送收款码）", "SEND_PAYMENT_CODE");
        return TaskResult.success(message, data);
    }
}
