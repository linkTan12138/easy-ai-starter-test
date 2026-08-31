package com.link.easyai.test.task.intenttest;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请假申请。
 * <p>
 * 测试用户表达相关需求时，意图识别能否准确进入此场景。
 * 纯动作场景，无需参数收集。
 */
@AiTask(value = "LEAVE_APPLICATION",
        name = "请假申请",
        description = "申请请假，包括事假、病假、年假等类型",
        triggers = {"请假", "申请请假", "我要请假", "休假申请", "请个假"})
public class LeaveApplicationTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(LeaveApplicationTask.class);


    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[LeaveApplicationTask] 意图识别成功，进入场景: LEAVE_APPLICATION");
        Map<String, Object> data = Map.of("taskType", "LEAVE_APPLICATION", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请假申请）", "LEAVE_APPLICATION");
        return TaskResult.success(message, data);
    }
}
