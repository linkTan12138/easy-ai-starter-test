package com.link.easyai.test.task.intenttest;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：物流查询。
 * <p>
 * 测试用户表达相关需求时，意图识别能否准确进入此场景。
 * 纯动作场景，无需参数收集。
 */
@AiTask(value = "LOGISTICS_QUERY",
        name = "物流查询",
        description = "查询订单物流状态和配送进度",
        triggers = {"物流", "查物流", "快递到哪了", "物流查询", "配送进度"})
public class LogisticsQueryTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(LogisticsQueryTask.class);


    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[LogisticsQueryTask] 意图识别成功，进入场景: LOGISTICS_QUERY");
        Map<String, Object> data = Map.of("taskType", "LOGISTICS_QUERY", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（物流查询）", "LOGISTICS_QUERY");
        return TaskResult.success(message, data);
    }
}
