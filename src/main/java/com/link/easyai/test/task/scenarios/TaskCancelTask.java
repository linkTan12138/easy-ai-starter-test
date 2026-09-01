package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：任务终止。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "TASK_CANCEL",
        name = "任务终止",
        description = "用户要求终止当前任务、取消操作",
        triggers = {"不用了", "取消", "算了", "不需要了"})
public class TaskCancelTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(TaskCancelTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[TaskCancelTask] 意图识别成功，进入场景: TASK_CANCEL");
        Map<String, Object> data = Map.of("taskType", "TASK_CANCEL", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（任务终止）", "TASK_CANCEL");
        return TaskResult.success(message, data);
    }
}
