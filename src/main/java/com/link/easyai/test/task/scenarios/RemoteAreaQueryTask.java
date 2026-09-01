package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：偏远查询。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "REMOTE_AREA_QUERY",
        name = "偏远查询",
        description = "查询某地址是否属于偏远地区（影响运费）",
        triggers = {"这个地址偏远吗", "帮查下是不是偏远", "偏远费要不要收", "偏远"})
public class RemoteAreaQueryTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(RemoteAreaQueryTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[RemoteAreaQueryTask] 意图识别成功，进入场景: REMOTE_AREA_QUERY");
        Map<String, Object> data = Map.of("taskType", "REMOTE_AREA_QUERY", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（偏远查询）", "REMOTE_AREA_QUERY");
        return TaskResult.success(message, data);
    }
}
