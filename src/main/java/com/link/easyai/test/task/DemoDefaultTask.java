package com.link.easyai.test.task;

import com.link.easyai.starter.engine.DefaultTaskContext;
import com.link.easyai.starter.engine.task.DefaultTaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.springframework.stereotype.Component;

/**
 * 默认（兜底）任务示例。
 * <p>
 * 当用户消息未匹配到任何场景（意图识别 NoMatch）时，框架会流转到本实现。
 * <p>
 * <b>启用/禁用</b>：保留 {@code @Component} 注解即启用本兜底任务；
 * 删除该注解（或删除本类）即恢复框架内置提示（"抱歉，我不太确定您想做什么..."）。
 */
//@Component
public class DemoDefaultTask implements DefaultTaskExecutor {

    @Override
    public TaskResult execute(DefaultTaskContext context) {
        String reply = "抱歉，我暂时无法理解您的需求。您说的是：\"" + context.getMessage()
                + "\"。什么也不处理";
        return TaskResult.success(reply, null);
    }
}
