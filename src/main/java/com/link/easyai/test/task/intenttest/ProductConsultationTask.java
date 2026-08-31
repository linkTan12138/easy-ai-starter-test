package com.link.easyai.test.task.intenttest;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：产品咨询。
 * <p>
 * 测试用户表达相关需求时，意图识别能否准确进入此场景。
 * 纯动作场景，无需参数收集。
 */
@AiTask(value = "PRODUCT_CONSULTATION",
        name = "产品咨询",
        description = "咨询产品功能、规格、价格、使用方法等信息",
        triggers = {"产品咨询", "咨询产品", "产品问题", "了解产品", "产品介绍"})
public class ProductConsultationTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(ProductConsultationTask.class);


    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[ProductConsultationTask] 意图识别成功，进入场景: PRODUCT_CONSULTATION");
        Map<String, Object> data = Map.of("taskType", "PRODUCT_CONSULTATION", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（产品咨询）", "PRODUCT_CONSULTATION");
        return TaskResult.success(message, data);
    }
}
