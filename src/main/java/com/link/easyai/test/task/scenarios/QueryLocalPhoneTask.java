package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：查询当地电话。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "QUERY_LOCAL_PHONE",
        name = "查询当地电话",
        description = "查询物流承运商目的国客服电话",
        triggers = {"客服电话", "联系方式", "当地电话", "电话"})
public class QueryLocalPhoneTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(QueryLocalPhoneTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[QueryLocalPhoneTask] 意图识别成功，进入场景: QUERY_LOCAL_PHONE");
        Map<String, Object> data = Map.of("taskType", "QUERY_LOCAL_PHONE", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（查询当地电话）", "QUERY_LOCAL_PHONE");
        return TaskResult.success(message, data);
    }
}
