package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：请求查货图片。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "GET_CHECK_PHOTO",
        name = "请求查货图片",
        description = "索取仓库查货拍摄的图片",
        triggers = {"查货图", "查货照片", "查货图片", "帮忙查下货拍个照", "查下货"})
public class GetCheckPhotoTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(GetCheckPhotoTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[GetCheckPhotoTask] 意图识别成功，进入场景: GET_CHECK_PHOTO");
        Map<String, Object> data = Map.of("taskType", "GET_CHECK_PHOTO", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（请求查货图片）", "GET_CHECK_PHOTO");
        return TaskResult.success(message, data);
    }
}
