package com.link.easyai.test.task.intenttest;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：账户注销。
 * <p>
 * 测试用户表达相关需求时，意图识别能否准确进入此场景。
 * 纯动作场景，无需参数收集。
 */
@AiTask(value = "ACCOUNT_CANCELLATION",
        name = "账户注销",
        description = "注销用户账户，清除个人信息和账户数据",
        triggers = {"注销账户", "销户", "注销账号", "删除账户", "注销"})
public class AccountCancellationTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(AccountCancellationTask.class);

    @Override
    public String type() {
        return "ACCOUNT_CANCELLATION";
    }

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[AccountCancellationTask] 意图识别成功，进入场景: ACCOUNT_CANCELLATION");
        Map<String, Object> data = Map.of("taskType", "ACCOUNT_CANCELLATION", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（账户注销）", "ACCOUNT_CANCELLATION");
        return TaskResult.success(message, data);
    }
}
