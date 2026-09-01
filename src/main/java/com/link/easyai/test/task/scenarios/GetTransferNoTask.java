package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求转单号。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_TRANSFER_NO",
        name = "请求转单号",
        description = "索取转单号（承运商生成的转运单号）、催促转单号",
        triggers = {"出个单", "转单号有了吗", "帮忙打一下单", "打一下单", "单号出了吗"})
public class GetTransferNoTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetTransferNoTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetTransferNoTask] 意图识别成功，进入场景: GET_TRANSFER_NO");
        Map<String, Object> data = Map.of("taskType", "GET_TRANSFER_NO", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求转单号）", "GET_TRANSFER_NO");
        return TaskResult.success(message, data);
    }
}
