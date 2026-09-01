package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求单件明细。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_ITEM_DETAIL",
        name = "请求单件明细",
        description = "索取运单的单件收货明细数据（按件展示收货重量、尺寸、材积、收货时间等）",
        triggers = {"单件明细", "单箱明细", "运单明细", "收货明细", "入库明细", "重量明细", "尺寸明细"})
public class GetItemDetailTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetItemDetailTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetItemDetailTask] 意图识别成功，进入场景: GET_ITEM_DETAIL");
        Map<String, Object> data = Map.of("taskType", "GET_ITEM_DETAIL", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求单件明细）", "GET_ITEM_DETAIL");
        return TaskResult.success(message, data);
    }
}
