package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求提单。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_BILL_OF_LADING",
        name = "请求提单",
        description = "索取提单文件（专线头程常用）",
        triggers = {"提单"})
public class GetBillOfLadingTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetBillOfLadingTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetBillOfLadingTask] 意图识别成功，进入场景: GET_BILL_OF_LADING");
        Map<String, Object> data = Map.of("taskType", "GET_BILL_OF_LADING", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求提单）", "GET_BILL_OF_LADING");
        return TaskResult.success(message, data);
    }
}
