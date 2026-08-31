package com.link.easyai.test.task.intenttest;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：费用报销。
 * <p>
 * 测试用户表达相关需求时，意图识别能否准确进入此场景。
 * 纯动作场景，无需参数收集。
 */
@AiTask(value = "EXPENSE_REIMBURSEMENT",
        name = "费用报销",
        description = "申请费用报销，提交报销单和相关凭证",
        triggers = {"报销", "费用报销", "报销申请", "我要报销", "报销单"})
public class ExpenseReimbursementTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(ExpenseReimbursementTask.class);


    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[ExpenseReimbursementTask] 意图识别成功，进入场景: EXPENSE_REIMBURSEMENT");
        Map<String, Object> data = Map.of("taskType", "EXPENSE_REIMBURSEMENT", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（费用报销）", "EXPENSE_REIMBURSEMENT");
        return TaskResult.success(message, data);
    }
}
