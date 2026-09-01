package com.link.easyai.test.task.queryBill;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiPostTask;
import com.link.easyai.starter.engine.task.PostTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志记录 PostTask。
 * <p>
 * 在主任务执行成功后调用，best-effort 模式，失败不影响主结果。
 */
@AiPostTask("SEND_MSG")
public class SendMsgTask implements PostTaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(SendMsgTask.class);


    @Override
    public void execute(ExecuteContext context) {
        log.info("[SEND_MSG] 记录操作日志: taskId={}, taskType={}, params={}",
                context.getTaskId(),
                context.getConfig() != null ? context.getConfig().getTaskType() : "unknown",
                context.getParameters());
        log.info("[SEND_MSG] 发送消息");
    }
}
