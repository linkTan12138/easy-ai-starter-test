package com.link.easyai.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * EasyAI Starter 集成测试应用启动类。
 * <p>
 * 测试场景：客服工单创建（CREATE_TICKET）
 * 覆盖框架功能：意图识别、多轮参数收集、字段校验、前置条件、Action执行、PostAction、
 * 会话管理、状态持久化、对话历史、分布式锁、限流熔断、SSE流式输出。
 */
@SpringBootApplication
public class EasyAiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyAiTestApplication.class, args);
    }
}
