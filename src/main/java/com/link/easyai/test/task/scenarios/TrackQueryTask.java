package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：轨迹查询。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "TRACK_QUERY",
        name = "轨迹查询",
        description = "查询包裹物流轨迹、运输状态",
        triggers = {"查下轨迹", "这票到哪了", "物流信息查一下", "包裹状态怎么样了", "轨迹"})
public class TrackQueryTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(TrackQueryTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[TrackQueryTask] 意图识别成功，进入场景: TRACK_QUERY");
        Map<String, Object> data = Map.of("taskType", "TRACK_QUERY", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（轨迹查询）", "TRACK_QUERY");
        return TaskResult.success(message, data);
    }
}
