package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求入仓标签。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_WAREHOUSE_LABEL",
        name = "请求入仓标签",
        description = "索取入仓标签文件",
        triggers = {"入仓标签发一下", "给下入仓label", "入仓标签", "入仓label"})
public class GetWarehouseLabelTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetWarehouseLabelTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetWarehouseLabelTask] 意图识别成功，进入场景: GET_WAREHOUSE_LABEL");
        Map<String, Object> data = Map.of("taskType", "GET_WAREHOUSE_LABEL", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求入仓标签）", "GET_WAREHOUSE_LABEL");
        return TaskResult.success(message, data);
    }
}
