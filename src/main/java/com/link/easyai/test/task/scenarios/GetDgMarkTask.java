package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求DG唛头文件。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_DG_MARK",
        name = "请求DG唛头文件",
        description = "索取危险品唛头标识文件",
        triggers = {"DG唛头", "危险品唛头"})
public class GetDgMarkTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetDgMarkTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetDgMarkTask] 意图识别成功，进入场景: GET_DG_MARK");
        Map<String, Object> data = Map.of("taskType", "GET_DG_MARK", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求DG唛头文件）", "GET_DG_MARK");
        return TaskResult.success(message, data);
    }
}
