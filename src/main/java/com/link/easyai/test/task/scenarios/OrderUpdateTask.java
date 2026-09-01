package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：修改订单。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "ORDER_UPDATE",
        name = "修改订单",
        description = "修改已有订单信息（地址、重量、品名、数量等）",
        triggers = {"地址改一下", "品名要修改", "收件人信息变了", "帮我改下这个单"})
public class OrderUpdateTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(OrderUpdateTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[OrderUpdateTask] 意图识别成功，进入场景: ORDER_UPDATE");
        Map<String, Object> data = Map.of("taskType", "ORDER_UPDATE", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（修改订单）", "ORDER_UPDATE");
        return TaskResult.success(message, data);
    }
}
