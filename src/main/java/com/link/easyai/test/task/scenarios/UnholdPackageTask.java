package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：取消暂扣。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "UNHOLD_PACKAGE",
        name = "取消暂扣",
        description = "对包裹进行取消扣件操作（取消暂停发出）",
        triggers = {"取消暂扣", "取消扣件", "解扣", "帮忙取消暂扣"})
public class UnholdPackageTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(UnholdPackageTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[UnholdPackageTask] 意图识别成功，进入场景: UNHOLD_PACKAGE");
        Map<String, Object> data = Map.of("taskType", "UNHOLD_PACKAGE", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（取消暂扣）", "UNHOLD_PACKAGE");
        return TaskResult.success(message, data);
    }
}
