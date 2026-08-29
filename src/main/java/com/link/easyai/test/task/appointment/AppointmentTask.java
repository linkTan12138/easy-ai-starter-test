package com.link.easyai.test.task.appointment;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 预约登记任务。
 * <p>
 * 当所有必填字段收集完成后，由框架自动调用。
 * 这里模拟预约登记，返回假数据（预约编号）。
 * <p>
 * 注意：传入的参数已经过 Normalization 标准化处理，
 * phone 为纯数字格式，appointmentDate 为 yyyy-MM-dd 格式。
 */
@AiTask(value = "APPOINTMENT",
        name = "预约登记",
        description = "通过对话收集客户信息并登记预约，支持电话号码和日期自动标准化",
        triggers = {"预约", "我要预约", "登记预约", "预约登记"})
public class AppointmentTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(AppointmentTask.class);

    @Override
    public String type() {
        return "APPOINTMENT";
    }

    @Override
    public TaskResult execute(ExecuteContext context) {
        Map<String, Object> params = context.getParameters();
        log.info("[AppointmentTask] 开始预约登记, 参数={}", params);

        // 这些参数已经过 Normalization 标准化
        String customerName = String.valueOf(params.getOrDefault("customerName", "未知"));
        String phone = String.valueOf(params.getOrDefault("phone", ""));
        String appointmentDate = String.valueOf(params.getOrDefault("appointmentDate", ""));
        String confirm = String.valueOf(params.getOrDefault("confirm", ""));

        // 模拟预约登记，生成假预约编号
        String appointmentNo = "APT" + System.currentTimeMillis() % 1000000;

        Map<String, Object> result = Map.of(
                "appointmentNo", appointmentNo,
                "customerName", customerName,
                "phone", phone,
                "appointmentDate", appointmentDate,
                "confirm", confirm,
                "status", "CONFIRMED",
                "createdAt", java.time.LocalDateTime.now().toString()
        );

        log.info("[AppointmentTask] 预约登记成功, 预约编号={}, 标准化后电话={}, 标准化后日期={}",
                appointmentNo, phone, appointmentDate);

        String message = String.format(
                "预约登记成功！\n预约编号：%s\n客户姓名：%s\n联系电话：%s\n预约日期：%s\n\n" +
                "（电话号码和日期已自动标准化为系统统一格式）",
                appointmentNo, customerName, phone, appointmentDate);

        return TaskResult.success(message, result);
    }
}
