package com.link.easyai.test.task.createTicket;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 创建工单任务。
 * <p>
 * 当所有必填字段收集完成后，由框架自动调用。
 * 这里模拟创建工单，返回假数据（工单编号）。
 */
@AiTask(value = "CREATE_TICKET",
        name = "创建工单",
        description = "根据收集的字段创建客服工单",
        triggers = {"创建工单", "我要投诉", "我要建议"},
        postActions = {"DEMO_LOG"})
public class CreateTicketTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(CreateTicketTask.class);


    @Override
    public TaskResult execute(ExecuteContext context) {
        Map<String, Object> params = context.getParameters();
        log.info("[CreateTicketTask] 开始创建工单, 参数={}", params);

        // 模拟创建工单，生成假工单编号
        String ticketNo = "TK" + System.currentTimeMillis() % 1000000;
        String ticketType = String.valueOf(params.getOrDefault("ticketType", "UNKNOWN"));
        String customerName = String.valueOf(params.getOrDefault("customerName", "未知"));
        String phone = String.valueOf(params.getOrDefault("phone", ""));
        String description = String.valueOf(params.getOrDefault("description", ""));
        String priority = String.valueOf(params.getOrDefault("priority", "MEDIUM"));

        // 模拟业务处理
        Map<String, Object> result = Map.of(
                "ticketNo", ticketNo,
                "ticketType", ticketType,
                "customerName", customerName,
                "phone", phone,
                "description", description,
                "priority", priority,
                "status", "CREATED",
                "createdAt", java.time.LocalDateTime.now().toString()
        );

        log.info("[CreateTicketTask] 工单创建成功, 工单编号={}", ticketNo);

        String message = String.format(
                "工单已创建成功！\n工单编号：%s\n工单类型：%s\n客户：%s\n优先级：%s\n我们会尽快处理您的%s。",
                ticketNo, ticketType, customerName, priority,
                "COMPLAINT".equals(ticketType) ? "投诉" : "CONSULT".equals(ticketType) ? "咨询" : "建议");

        return TaskResult.success(message, result);
    }
}
