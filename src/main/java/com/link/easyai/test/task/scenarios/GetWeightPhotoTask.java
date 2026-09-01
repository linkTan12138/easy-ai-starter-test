package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求称重图片。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_WEIGHT_PHOTO",
        name = "请求称重图片",
        description = "索取货物称重的图片",
        triggers = {"称重图", "称重照片", "称重图片", "重量照片"})
public class GetWeightPhotoTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetWeightPhotoTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetWeightPhotoTask] 意图识别成功，进入场景: GET_WEIGHT_PHOTO");
        Map<String, Object> data = Map.of("taskType", "GET_WEIGHT_PHOTO", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求称重图片）", "GET_WEIGHT_PHOTO");
        return TaskResult.success(message, data);
    }
}
