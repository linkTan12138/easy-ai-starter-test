package com.link.easyai.test.task.scenarios;

import com.link.easyai.starter.engine.context.ExecuteContext;
import com.link.easyai.starter.engine.task.AiTask;
import com.link.easyai.starter.engine.task.TaskExecutor;
import com.link.easyai.starter.engine.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 意图测试场景：文件上传。
 * <p>
 * 纯动作场景，无需参数收集，用于测试意图识别能否准确进入该场景。
 */
@AiTask(value = "FILE_UPLOAD",
        name = "文件上传",
        description = "用户上传自己的文件",
        triggers = {"上传发票", "提交文件", "传一下这个资料", "上传"})
public class FileUploadTask implements TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(FileUploadTask.class);

    @Override
    public TaskResult execute(ExecuteContext context) {
        log.info("[FileUploadTask] 意图识别成功，进入场景: FILE_UPLOAD");
        Map<String, Object> data = Map.of("taskType", "FILE_UPLOAD", "taskId", context.getTaskId());
        String message = String.format("意图识别成功，已进入场景：%s（文件上传）", "FILE_UPLOAD");
        return TaskResult.success(message, data);
    }
}
