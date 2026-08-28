package com.link.easyai.test.task.intenttest;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：预约挂号。
 * <p>
 * 测试用户表达相关需求时，意图识别能否准确进入此场景。
 * 纯动作场景，无需参数收集。
 */
@AiTask(value = "APPOINTMENT_REGISTRATION",
        name = "预约挂号",
        description = "在线预约挂号，选择科室和医生",
        triggers = {"预约", "挂号", "预约挂号", "我要挂号", "挂号预约"})
public class AppointmentRegistrationTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(AppointmentRegistrationTask.class);

    @Override
    public String type() {
        return "APPOINTMENT_REGISTRATION";
    }

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[AppointmentRegistrationTask] 意图识别成功，进入场景: APPOINTMENT_REGISTRATION");
        Map<String, Object> data = Map.of("taskType", "APPOINTMENT_REGISTRATION", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（预约挂号）", "APPOINTMENT_REGISTRATION");
        return TaskResult.success(message, data);
    }
}
