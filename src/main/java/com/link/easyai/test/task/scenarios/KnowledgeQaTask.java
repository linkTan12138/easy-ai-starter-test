package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：知识库答疑。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "KNOWLEDGE_QA",
        name = "知识库答疑",
        description = "走货咨询、系统操作问题、入仓延误、行业知识咨询、公开信息咨询、交货仓库地址、附加费收取标准",
        triggers = {"交货地点在哪", "偏远费怎么收", "系统怎么打不了单", "你们香港仓库地址给一下", "纺织品有附加费吗", "仓库地址", "包装要求", "重量限制"})
public class KnowledgeQaTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(KnowledgeQaTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[KnowledgeQaTask] 意图识别成功，进入场景: KNOWLEDGE_QA");
        Map<String, Object> data = Map.of("taskType", "KNOWLEDGE_QA", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（知识库答疑）", "KNOWLEDGE_QA");
        return TaskResult.success(message, data);
    }
}
