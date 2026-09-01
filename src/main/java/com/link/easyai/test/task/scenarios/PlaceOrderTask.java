package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：代下单。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "PLACE_ORDER",
        name = "代下单",
        description = "客户委托货代帮忙下单、预报",
        triggers = {"帮我下个单", "帮忙预报一下", "这票帮我下单", "帮我下单", "帮忙下个单", "预报一下"})
public class PlaceOrderTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(PlaceOrderTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[PlaceOrderTask] 意图识别成功，进入场景: PLACE_ORDER");
        Map<String, Object> data = Map.of("taskType", "PLACE_ORDER", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（代下单）", "PLACE_ORDER");
        return TaskResult.success(message, data);
    }
}
