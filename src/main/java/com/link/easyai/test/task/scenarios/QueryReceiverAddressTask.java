package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：查询收件人地址。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "QUERY_RECEIVER_ADDRESS",
        name = "查询收件人地址",
        description = "查询某单号的收件人地址信息",
        triggers = {"收件人地址查一下", "这票寄到哪里", "收件地址是什么", "收件人地址"})
public class QueryReceiverAddressTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(QueryReceiverAddressTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[QueryReceiverAddressTask] 意图识别成功，进入场景: QUERY_RECEIVER_ADDRESS");
        Map<String, Object> data = Map.of("taskType", "QUERY_RECEIVER_ADDRESS", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（查询收件人地址）", "QUERY_RECEIVER_ADDRESS");
        return TaskResult.success(message, data);
    }
}
