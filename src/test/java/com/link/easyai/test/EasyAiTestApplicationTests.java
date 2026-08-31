package com.link.easyai.test;

import com.link.easyai.starter.engine.AiChatService;
import com.link.easyai.starter.engine.ChatResponse;
import com.link.easyai.starter.engine.context.TaskContext;
import com.link.easyai.starter.engine.session.SessionManager;
import com.link.easyai.starter.engine.state.FieldState;
import com.link.easyai.starter.engine.state.TaskState;
import com.link.easyai.starter.engine.state.TaskStateManager;
import com.link.easyai.starter.engine.state.TaskStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * EasyAI Starter 集成测试。
 * 覆盖框架核心功能。
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EasyAiTestApplicationTests {

    @Autowired
    private AiChatService aiChatService;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private TaskStateManager taskStateManager;

    // ---- 1. 上下文加载 ----

    @Test
    @Order(1)
    @DisplayName("Spring上下文应成功加载")
    void contextLoads() {
        assertNotNull(aiChatService, "AiChatService 应被注入");
        assertNotNull(sessionManager, "SessionManager 应被注入");
        assertNotNull(taskStateManager, "TaskStateManager 应被注入");
    }

    // ---- 2. 完整多轮参数收集 + Action执行 ----

    @Test
    @Order(2)
    @DisplayName("完整流程：多轮收集所有字段并执行Action")
    void testFullConversation() {
        String sid = "full-" + UUID.randomUUID().toString().substring(0, 8);
        TaskContext ctx = TaskContext.builder()
                .tenantId("0").sessionId(sid).data(new HashMap<>()).build();

        // 第1轮：触发意图识别
        ChatResponse r1 = aiChatService.chat("我要投诉", sid, ctx);
        assertNotNull(r1.getTaskId(), "应创建任务");
        assertEquals("CREATE_TICKET", r1.getTaskType());
        assertTrue(r1.isNeedMore(), "应需要更多信息");

        // 第2轮：提供客户姓名
        ChatResponse r2 = aiChatService.chat("我叫张三", sid, ctx);
        assertTrue(r2.isNeedMore(), "仍需要更多信息");

        // 第3轮：提供联系电话
        ChatResponse r3 = aiChatService.chat("电话13800138000", sid, ctx);
        assertTrue(r3.isNeedMore(), "仍需要问题描述");

        // 第4轮：提供问题描述
        ChatResponse r4 = aiChatService.chat("问题是物流太慢了", sid, ctx);
        assertTrue(r4.isCompleted(), "第4轮应完成所有字段收集并执行Action");
        assertTrue(r4.getMessage().contains("工单已创建成功"), "应返回工单创建成功消息");
        assertNotNull(r4.getActionResult(), "应返回Action结果");

        // 验证任务状态
        TaskState state = taskStateManager.load(r4.getTaskId(), "CREATE_TICKET", 1);
        assertNotNull(state);
        assertEquals(TaskStatus.COMPLETED, state.getStatus(), "任务状态应为COMPLETED");
    }

    // ---- 3. 枚举字段自动校验 ----

    @Test
    @Order(3)
    @DisplayName("枚举字段：工单类型自动转换中文标签为枚举值")
    void testEnumField() {
        String sid = "enum-" + UUID.randomUUID().toString().substring(0, 8);
        TaskContext ctx = TaskContext.builder()
                .tenantId("0").sessionId(sid).data(new HashMap<>()).build();

        ChatResponse r1 = aiChatService.chat("我要建议", sid, ctx);
        assertNotNull(r1.getTaskId());

        // 验证任务状态中 ticketType 已转换为枚举值
        TaskState state = taskStateManager.load(r1.getTaskId(), "CREATE_TICKET", 1);
        assertNotNull(state);
        FieldState ticketTypeField = state.getField("ticketType");
        assertNotNull(ticketTypeField, "ticketType字段应存在");
        assertEquals("SUGGESTION", ticketTypeField.getValue(), "中文'建议'应转换为枚举值SUGGESTION");
    }

    // ---- 4. 自定义校验器（手机号） ----

    @Test
    @Order(4)
    @DisplayName("自定义校验器：无效手机号应被拒绝并提示")
    void testPhoneValidator() {
        String sid = "phone-" + UUID.randomUUID().toString().substring(0, 8);
        TaskContext ctx = TaskContext.builder()
                .tenantId("0").sessionId(sid).data(new HashMap<>()).build();

        // 先提供工单类型和姓名
        aiChatService.chat("我要投诉", sid, ctx);
        aiChatService.chat("我叫李四", sid, ctx);

        // 提供无效手机号
        ChatResponse r = aiChatService.chat("电话12345", sid, ctx);
        assertTrue(r.isNeedMore(), "无效手机号应仍需要更多信息");
        assertTrue(r.getMessage().contains("手机") || r.getMessage().contains("电话") || r.getMessage().contains("格式"),
                "应提示手机号格式错误");

        // 提供有效手机号
        ChatResponse r2 = aiChatService.chat("电话13900139000", sid, ctx);
        // 有效手机号后应继续收集下一个字段（问题描述）
        assertTrue(r2.isNeedMore() || r2.isCompleted(), "有效手机号后应继续或完成");
    }

    // ---- 5. 前置依赖（description依赖ticketType） ----

    @Test
    @Order(5)
    @DisplayName("前置依赖：ticketType未收集时description不应被提取")
    void testFieldDependency() {
        String sid = "dep-" + UUID.randomUUID().toString().substring(0, 8);
        TaskContext ctx = TaskContext.builder()
                .tenantId("0").sessionId(sid).data(new HashMap<>()).build();

        // 直接说问题描述，但ticketType还没收集，description不应被提取
        ChatResponse r = aiChatService.chat("问题是物流太慢", sid, ctx);

        // 意图识别后，第一轮应收集ticketType，而不是description
        assertNotNull(r.getTaskId());
        TaskState state = taskStateManager.load(r.getTaskId(), "CREATE_TICKET", 1);
        assertNotNull(state);
        // description不应被收集（因为ticketType还没收集，前置条件不满足）
        FieldState descriptionField = state.getField("description");
        assertNull(descriptionField, "ticketType未收集时，description不应被提取");
    }

    // ---- 6. 指定任务类型 ----

    @Test
    @Order(6)
    @DisplayName("指定任务类型：跳过意图识别，直接进入参数收集")
    void testChatWithTaskType() {
        String sid = "direct-" + UUID.randomUUID().toString().substring(0, 8);
        TaskContext ctx = TaskContext.builder()
                .tenantId("0").sessionId(sid).data(new HashMap<>()).build();

        ChatResponse response = aiChatService.chatWithTaskType(
                "开始", sid, "CREATE_TICKET", ctx);

        assertNotNull(response.getTaskId(), "应创建任务");
        assertEquals("CREATE_TICKET", response.getTaskType());
        assertTrue(response.isNeedMore(), "应需要更多信息");
    }

    // ---- 7. 会话管理 ----

    @Test
    @Order(7)
    @DisplayName("会话管理：重置会话后应清除任务绑定")
    void testSessionReset() {
        String sid = "reset-" + UUID.randomUUID().toString().substring(0, 8);
        TaskContext ctx = TaskContext.builder()
                .tenantId("0").sessionId(sid).data(new HashMap<>()).build();

        // 创建任务
        ChatResponse r1 = aiChatService.chat("我要投诉", sid, ctx);
        assertNotNull(r1.getTaskId());

        // 重置会话
        sessionManager.reset(sid, "0");

        // 验证会话已清除任务绑定
        var session = sessionManager.loadOrCreate(sid, "0");
        assertNull(session.getCurrentTaskId(), "重置后应清除当前任务ID");
        assertEquals(0, session.getStatus(), "重置后状态应为IDLE(0)");
    }

    // ---- 8. 非必填字段 ----

    @Test
    @Order(8)
    @DisplayName("非必填字段：priority不提供时任务仍可完成")
    void testOptionalField() {
        String sid = "optional-" + UUID.randomUUID().toString().substring(0, 8);
        TaskContext ctx = TaskContext.builder()
                .tenantId("0").sessionId(sid).data(new HashMap<>()).build();

        // 提供所有必填字段，但不提供priority（非必填）
        aiChatService.chat("我要咨询", sid, ctx);
        aiChatService.chat("我叫赵六", sid, ctx);
        aiChatService.chat("电话13900139000", sid, ctx);
        ChatResponse finalResponse = aiChatService.chat("问题是如何退款", sid, ctx);

        assertTrue(finalResponse.isCompleted(), "非必填字段不提供时任务仍应完成");
        assertTrue(finalResponse.getMessage().contains("工单已创建成功"));

        // 验证priority为null或默认值
        TaskState state = taskStateManager.load(finalResponse.getTaskId(), "CREATE_TICKET", 1);
        FieldState priorityField = state.getField("priority");
        // priority为非必填，用户未提供，应为null或默认值
        assertTrue(priorityField == null || priorityField.getValue() == null || "MEDIUM".equals(priorityField.getValue()),
                "非必填字段未提供时应为null或默认值");
    }

    // ---- 9. 意图识别关键词 ----

    @Test
    @Order(9)
    @DisplayName("意图识别：不同关键词应正确识别为CREATE_TICKET")
    void testIntentRecognition() {
        String[] keywords = {"我要投诉", "我要建议", "我要咨询", "帮我创建工单", "提交工单"};
        for (String keyword : keywords) {
            String sid = "intent-" + UUID.randomUUID().toString().substring(0, 8);
            TaskContext ctx = TaskContext.builder()
                    .tenantId("0").sessionId(sid).data(new HashMap<>()).build();
            ChatResponse r = aiChatService.chat(keyword, sid, ctx);
            assertEquals("CREATE_TICKET", r.getTaskType(),
                    "关键词 '" + keyword + "' 应识别为CREATE_TICKET");
        }
    }

    // ---- 10. 会话连续性 ----

    @Test
    @Order(10)
    @DisplayName("会话连续性：同一会话多轮对话应保持同一任务")
    void testSessionContinuity() {
        String sid = "cont-" + UUID.randomUUID().toString().substring(0, 8);
        TaskContext ctx = TaskContext.builder()
                .tenantId("0").sessionId(sid).data(new HashMap<>()).build();

        ChatResponse r1 = aiChatService.chat("我要投诉", sid, ctx);
        String taskId1 = r1.getTaskId();

        ChatResponse r2 = aiChatService.chat("我叫王五", sid, ctx);
        String taskId2 = r2.getTaskId();

        assertEquals(taskId1, taskId2, "同一会话多轮对话应保持同一任务ID");
    }
}
