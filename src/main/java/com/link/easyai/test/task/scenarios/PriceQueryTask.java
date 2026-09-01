package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：查价。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "PRICE_QUERY",
        name = "查价",
        description = "提供具体参数（重量、目的地等）查询运费价格",
        triggers = {"20kg到美国多少钱", "发英国DHL什么价", "帮我查下这个价格", "多少钱", "什么价"})
public class PriceQueryTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(PriceQueryTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[PriceQueryTask] 意图识别成功，进入场景: PRICE_QUERY");
        Map<String, Object> data = Map.of("taskType", "PRICE_QUERY", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（查价）", "PRICE_QUERY");
        return TaskResult.success(message, data);
    }
}
