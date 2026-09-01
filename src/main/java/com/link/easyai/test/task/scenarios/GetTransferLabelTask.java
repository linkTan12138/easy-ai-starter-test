package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求转单标签。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_TRANSFER_LABEL",
        name = "请求转单标签",
        description = "索取转单标签文件（面单/LB/Label）",
        triggers = {"给一下LB", "发一下面单", "申请面单", "给下面单", "转单标签有了吗", "面单", "标签"})
public class GetTransferLabelTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetTransferLabelTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetTransferLabelTask] 意图识别成功，进入场景: GET_TRANSFER_LABEL");
        Map<String, Object> data = Map.of("taskType", "GET_TRANSFER_LABEL", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求转单标签）", "GET_TRANSFER_LABEL");
        return TaskResult.success(message, data);
    }
}
