package com.link.easyai.test.task;

import com.link.easyai.starter.engine.action.AiPostAction;
import com.link.easyai.starter.engine.action.PostActionExecutor;
import com.link.easyai.starter.engine.context.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志记录 PostAction。
 * <p>
 * 在主 Action 执行成功后调用，best-effort 模式，失败不影响主结果。
 * 这里模拟记录操作日志。
 */
@AiPostAction("DEMO_LOG")
public class LogPostAction implements PostActionExecutor {

    private static final Logger log = LoggerFactory.getLogger(LogPostAction.class);

    @Override
    public String type() {
        return "DEMO_LOG";
    }

    @Override
    public void execute(ActionContext context) {
        log.info("[LogPostAction] 记录操作日志: taskId={}, taskType={}, params={}",
                context.getTaskId(),
                context.getConfig() != null ? context.getConfig().getTaskType() : "unknown",
                context.getParameters());
        // 模拟写入日志表
        log.info("[LogPostAction] 操作日志已写入");
    }
}
