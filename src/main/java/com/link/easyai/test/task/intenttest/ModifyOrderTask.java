package com.link.easyai.test.task.intenttest;

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
 * 测试用户表达相关需求时，意图识别能否准确进入此场景。
 * 纯动作场景，无需参数收集。
 */
@AiTask(value = "MODIFY_ORDER",
        name = "修改订单",
        description = "修改已下单的订单信息，如收货地址、商品数量等",
        triggers = {"修改订单", "改订单", "订单修改", "更改订单", "改地址"})
public class ModifyOrderTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(ModifyOrderTask.class);


    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[ModifyOrderTask] 意图识别成功，进入场景: MODIFY_ORDER");
        Map<String, Object> data = Map.of("taskType", "MODIFY_ORDER", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（修改订单）", "MODIFY_ORDER");
        return TaskResult.success(message, data);
    }
}
