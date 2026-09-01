package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求DGD文件。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_DGD",
        name = "请求DGD文件",
        description = "索取危险品申报单（Dangerous Goods Declaration）",
        triggers = {"DGD", "危险品申报单"})
public class GetDgdTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetDgdTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetDgdTask] 意图识别成功，进入场景: GET_DGD");
        Map<String, Object> data = Map.of("taskType", "GET_DGD", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求DGD文件）", "GET_DGD");
        return TaskResult.success(message, data);
    }
}
