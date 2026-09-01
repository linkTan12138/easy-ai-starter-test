package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：问题件回复。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "PROBLEM_REPLY",
        name = "问题件回复",
        description = "通过引用消息回复已知问题件",
        triggers = {"回复问题件", "问题件回复", "引用此消息"})
public class ProblemReplyTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(ProblemReplyTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[ProblemReplyTask] 意图识别成功，进入场景: PROBLEM_REPLY");
        Map<String, Object> data = Map.of("taskType", "PROBLEM_REPLY", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（问题件回复）", "PROBLEM_REPLY");
        return TaskResult.success(message, data);
    }
}
