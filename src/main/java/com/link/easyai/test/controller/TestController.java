package com.link.easyai.test.controller;

import com.link.easyai.starter.engine.AiChatService;
import com.link.easyai.starter.engine.ChatResponse;
import com.link.easyai.starter.engine.context.TaskContext;
import com.link.easyai.starter.engine.session.SessionManager;
import com.link.easyai.starter.engine.state.FieldState;
import com.link.easyai.starter.engine.state.TaskState;
import com.link.easyai.starter.engine.state.TaskStateManager;
import com.link.easyai.starter.domain.entity.AiChatSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 测试控制器。
 * 提供全面的测试接口，覆盖框架的各项功能。
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private AiChatService aiChatService;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private TaskStateManager taskStateManager;

    /**
     * 自动意图识别聊天。
     */
    @PostMapping("/chat")
    public Map<String, Object> chat(@RequestBody Map<String, String> request) {
        String sessionId = request.getOrDefault("sessionId", "test-session-001");
        String message = request.get("message");

        log.info("[TestController] chat: sessionId={}, message={}", sessionId, message);

        TaskContext context = TaskContext.builder()
                .tenantId(0L)
                .sessionId(sessionId)
                .data(new HashMap<>())
                .build();

        ChatResponse response = aiChatService.chat(message, sessionId, context);
        return toResponseMap(response);
    }

    /**
     * 指定任务类型聊天。
     */
    @PostMapping("/chat/{taskType}")
    public Map<String, Object> chatWithTaskType(
            @PathVariable String taskType,
            @RequestBody Map<String, String> request) {
        String sessionId = request.getOrDefault("sessionId", "test-session-001");
        String message = request.get("message");

        log.info("[TestController] chatWithTaskType: taskType={}, sessionId={}, message={}",
                taskType, sessionId, message);

        TaskContext context = TaskContext.builder()
                .tenantId(0L)
                .sessionId(sessionId)
                .data(new HashMap<>())
                .build();

        ChatResponse response = aiChatService.chatWithTaskType(message, sessionId, taskType, context);
        return toResponseMap(response);
    }

    /**
     * 查看会话状态。
     */
    @GetMapping("/session/{sessionId}")
    public Map<String, Object> getSession(@PathVariable String sessionId) {
        AiChatSession session = sessionManager.loadOrCreate(sessionId, 0L);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("sessionId", session.getSessionId());
        result.put("status", session.getStatus());
        result.put("currentTaskId", session.getCurrentTaskId());
        result.put("currentTaskType", session.getCurrentTaskType());
        result.put("turnCount", session.getTurnCount());
        result.put("lastActiveTime", session.getLastActiveTime());
        return result;
    }

    /**
     * 查看任务状态。
     */
    @GetMapping("/task/{taskId}")
    public Map<String, Object> getTaskState(@PathVariable String taskId) {
        // load 需要 taskType 和 configVersion，这里用 null 尝试加载
        TaskState state = taskStateManager.load(taskId, null, null);
        if (state == null || state.getTaskId() == null) {
            return Map.of("error", "任务不存在", "taskId", taskId);
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("taskId", state.getTaskId());
        result.put("taskType", state.getTaskType());
        result.put("status", state.getStatus() != null ? state.getStatus().name() : null);
        result.put("configVersion", state.getConfigVersion());
        result.put("turnCount", state.getTurnCount());
        result.put("version", state.getVersion());

        // 收集已收集的字段
        Map<String, Object> collectedFields = new LinkedHashMap<>();
        if (state.getFields() != null) {
            for (Map.Entry<String, FieldState> entry : state.getFields().entrySet()) {
                FieldState fs = entry.getValue();
                Map<String, Object> fieldInfo = new LinkedHashMap<>();
                fieldInfo.put("value", fs.getValue());
                fieldInfo.put("rawValue", fs.getRawValue());
                fieldInfo.put("status", fs.getStatus() != null ? fs.getStatus().name() : null);
                collectedFields.put(entry.getKey(), fieldInfo);
            }
        }
        result.put("fields", collectedFields);
        return result;
    }

    /**
     * 重置会话。
     */
    @PostMapping("/session/{sessionId}/reset")
    public Map<String, Object> resetSession(@PathVariable String sessionId) {
        sessionManager.reset(sessionId);
        return Map.of("success", true, "message", "会话已重置", "sessionId", sessionId);
    }

    /**
     * 取消当前任务（清除会话绑定）。
     */
    @PostMapping("/session/{sessionId}/cancel")
    public Map<String, Object> cancelTask(@PathVariable String sessionId) {
        AiChatSession session = sessionManager.loadOrCreate(sessionId, 0L);
        if (session.getCurrentTaskId() != null) {
            String taskId = session.getCurrentTaskId();
            sessionManager.clearTask(sessionId);
            return Map.of("success", true, "message", "任务已取消", "taskId", taskId);
        }
        return Map.of("success", false, "message", "当前没有活跃任务");
    }

    /**
     * 健康检查。
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "easy-ai-starter-test",
                "time", java.time.LocalDateTime.now().toString()
        );
    }

    private Map<String, Object> toResponseMap(ChatResponse response) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("message", response.getMessage());
        result.put("taskId", response.getTaskId());
        result.put("taskType", response.getTaskType());
        result.put("completed", response.isCompleted());
        result.put("needMore", response.isNeedMore());
        result.put("clarification", response.isClarification());
        if (response.getActionResult() != null) {
            result.put("actionResult", response.getActionResult());
        }
        return result;
    }
}
