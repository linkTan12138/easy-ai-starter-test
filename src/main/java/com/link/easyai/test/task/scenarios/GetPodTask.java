package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求POD签收单。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_POD",
        name = "请求POD签收单",
        description = "索取签收证明（Proof of Delivery）",
        triggers = {"POD", "签收单", "签收证明"})
public class GetPodTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetPodTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetPodTask] 意图识别成功，进入场景: GET_POD");
        Map<String, Object> data = Map.of("taskType", "GET_POD", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求POD签收单）", "GET_POD");
        return TaskResult.success(message, data);
    }
}
