package com.link.easyai.test.task.intenttest;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：投诉反馈。
 * <p>
 * 测试用户表达相关需求时，意图识别能否准确进入此场景。
 * 纯动作场景，无需参数收集。
 */
@AiTask(value = "COMPLAINT_FEEDBACK",
        name = "投诉反馈",
        description = "提交投诉或反馈意见，客服会跟进处理",
        triggers = {"投诉", "反馈", "我要投诉", "投诉建议", "意见反馈"})
public class ComplaintFeedbackTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(ComplaintFeedbackTask.class);


    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[ComplaintFeedbackTask] 意图识别成功，进入场景: COMPLAINT_FEEDBACK");
        Map<String, Object> data = Map.of("taskType", "COMPLAINT_FEEDBACK", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（投诉反馈）", "COMPLAINT_FEEDBACK");
        return TaskResult.success(message, data);
    }
}
