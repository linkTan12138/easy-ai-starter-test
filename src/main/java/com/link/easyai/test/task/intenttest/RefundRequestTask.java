package com.link.easyai.test.task.intenttest;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：退款申请。
 * <p>
 * 测试用户表达相关需求时，意图识别能否准确进入此场景。
 * 纯动作场景，无需参数收集。
 */
@AiTask(value = "REFUND_REQUEST",
        name = "退款申请",
        description = "申请订单退款，包括全额退款、部分退款、退货退款等",
        triggers = {"退款", "申请退款", "我要退款", "退货退款", "退钱"})
public class RefundRequestTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(RefundRequestTask.class);

    @Override
    public String type() {
        return "REFUND_REQUEST";
    }

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[RefundRequestTask] 意图识别成功，进入场景: REFUND_REQUEST");
        Map<String, Object> data = Map.of("taskType", "REFUND_REQUEST", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（退款申请）", "REFUND_REQUEST");
        return TaskResult.success(message, data);
    }
}
