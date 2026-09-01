package com.link.easyai.test.task.channelRegister;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 渠道登记任务（AiMapping 测试场景）。
 * <p>
 * 当 channelName、phone 两个字段收集完成后，由框架自动调用。
 * <p>
 * 通过 {@code context.getParameters()} 读取映射引擎组装好的参数：
 * <ul>
 *   <li>channelCode：来自 {@code $data.channelCode}（ChannelNormalizer 标准化时解析的渠道编码）</li>
 *   <li>channelName：来自 {@code $value}（校验/标准化后的渠道名称）</li>
 *   <li>channelSource：来自字面常量 {@code "MANUAL"}</li>
 *   <li>phone：来自 {@code $value}（PhoneValidator 标准化后的纯数字）</li>
 *   <li>phoneRaw：来自 {@code $rawValue}（LLM 原始抽取值）</li>
 * </ul>
 */
@AiTask(value = "CHANNEL_REGISTER",
        name = "渠道登记",
        description = "通过对话收集渠道信息并登记，演示字段映射（AiMapping）能力",
        triggers = {"渠道登记", "登记渠道", "我要登记渠道", "添加渠道"},
        postActions = {
        "SEND_MSG",
        "DEMO_LOG"
        })
public class ChannelRegisterTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(ChannelRegisterTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        Map<String, Object> params = context.getParameters();
        log.info("[ChannelRegisterTask] 渠道登记参数={}", params);

        // 从映射引擎组装后的参数中取值
        String channelId = String.valueOf(params.getOrDefault("channelId", ""));
        String channelCode = String.valueOf(params.getOrDefault("channelCode", ""));
        String channelName = String.valueOf(params.getOrDefault("channelName", ""));
        String channelSource = String.valueOf(params.getOrDefault("channelSource", ""));
        String phone = String.valueOf(params.getOrDefault("phone", ""));
        String phoneRaw = String.valueOf(params.getOrDefault("phoneRaw", ""));

        // 模拟登记，生成渠道登记编号
        String registerNo = "CR" + System.currentTimeMillis() % 1000000;

        Map<String, Object> result = Map.of(
                "channelId", channelId,
                "registerNo", registerNo,
                "channelCode", channelCode,
                "channelName", channelName,
                "channelSource", channelSource,
                "phone", phone,
                "phoneRaw", phoneRaw,
                "status", "REGISTERED",
                "createdAt", java.time.LocalDateTime.now().toString()
        );

        log.info("[ChannelRegisterTask] 渠道登记成功, 编码={}, 电话标准化={} (原始={})",
                channelCode, phone, phoneRaw);

        String message = String.format(
                "渠道登记成功！\n登记编号：%s\n渠道编码（$data.channelCode）：%s\n" +
                "渠道id（$data.channelId）：%s\n" +
                "渠道名称（$value）：%s\n登记来源（字面常量）：%s\n\n" +
                "联系电话（$value 标准化后）：%s\n联系电话原始值（$rawValue）：%s\n\n" +
                "（以上展示了 @AiMapping 的 4 种 source 表达式）",
                registerNo, channelCode,channelId, channelName, channelSource, phone, phoneRaw);

        return TaskResult.success(message, result);
    }
}
