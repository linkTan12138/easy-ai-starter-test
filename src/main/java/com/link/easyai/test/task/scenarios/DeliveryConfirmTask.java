package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：收货确认。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "DELIVERY_CONFIRM",
        name = "收货确认",
        description = "用户对收货、数据、重量、费用等进行确认",
        triggers = {"数据确认一下", "重量没问题", "费用确认", "收货确认"})
public class DeliveryConfirmTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(DeliveryConfirmTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[DeliveryConfirmTask] 意图识别成功，进入场景: DELIVERY_CONFIRM");
        Map<String, Object> data = Map.of("taskType", "DELIVERY_CONFIRM", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（收货确认）", "DELIVERY_CONFIRM");
        return TaskResult.success(message, data);
    }
}
