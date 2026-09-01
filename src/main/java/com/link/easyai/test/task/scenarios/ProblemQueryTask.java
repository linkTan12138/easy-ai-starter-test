package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：问题件查询。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "PROBLEM_QUERY",
        name = "问题件查询",
        description = "查询是否存在问题件、问题件状态",
        triggers = {"这票有问题件吗", "问题件什么情况", "有没有待处理的问题", "问题件"})
public class ProblemQueryTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(ProblemQueryTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[ProblemQueryTask] 意图识别成功，进入场景: PROBLEM_QUERY");
        Map<String, Object> data = Map.of("taskType", "PROBLEM_QUERY", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（问题件查询）", "PROBLEM_QUERY");
        return TaskResult.success(message, data);
    }
}
