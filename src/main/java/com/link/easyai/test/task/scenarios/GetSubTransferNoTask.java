package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求子转单号。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_SUB_TRANSFER_NO",
        name = "请求子转单号",
        description = "索取子单号（格式为运单号-序号）",
        triggers = {"子单号", "子单", "分单号", "子转单号"})
public class GetSubTransferNoTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetSubTransferNoTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetSubTransferNoTask] 意图识别成功，进入场景: GET_SUB_TRANSFER_NO");
        Map<String, Object> data = Map.of("taskType", "GET_SUB_TRANSFER_NO", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求子转单号）", "GET_SUB_TRANSFER_NO");
        return TaskResult.success(message, data);
    }
}
