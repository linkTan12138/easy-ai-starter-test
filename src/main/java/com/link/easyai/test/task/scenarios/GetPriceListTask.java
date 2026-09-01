package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求报价表。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_PRICE_LIST",
        name = "请求报价表",
        description = "直接索要报价表/价格表文件",
        triggers = {"给下报价", "发一下价格表", "报价表能提供吗", "报价", "价格表"})
public class GetPriceListTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetPriceListTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetPriceListTask] 意图识别成功，进入场景: GET_PRICE_LIST");
        Map<String, Object> data = Map.of("taskType", "GET_PRICE_LIST", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求报价表）", "GET_PRICE_LIST");
        return TaskResult.success(message, data);
    }
}
