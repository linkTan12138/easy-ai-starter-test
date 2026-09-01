package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：扣件。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "HOLD_PACKAGE",
        name = "扣件",
        description = "对包裹进行扣件操作（暂停发出）",
        triggers = {"这票先扣下", "帮忙扣件", "先不要发", "暂停发货", "扣件"})
public class HoldPackageTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(HoldPackageTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[HoldPackageTask] 意图识别成功，进入场景: HOLD_PACKAGE");
        Map<String, Object> data = Map.of("taskType", "HOLD_PACKAGE", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（扣件）", "HOLD_PACKAGE");
        return TaskResult.success(message, data);
    }
}
