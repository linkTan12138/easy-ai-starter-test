# Easy-AI Starter Test

> Easy-AI Starter 框架的集成测试与示例工程。通过一个"AI 业务助手"演示框架的完整能力：意图识别、多轮参数收集、字段校验、标准化（Normalization）、任务执行、后置任务、会话管理与多租户隔离、对话历史、状态持久化等。

## 功能特性

本工程覆盖框架的核心能力，并作为集成者参考的"标准写法"：

| 能力 | 说明 | 示例场景 |
|------|------|----------|
| **自动意图识别** | 根据用户自然语言自动判断进入哪个业务场景，并返回 `intentReason` / `intentConfidence` / `intentSource` 便于调试提示词 | 全部场景 |
| **指定任务路由** | 指定 `taskType` 直接进入某场景，跳过意图识别 | `chat/{taskType}` 接口 |
| **多轮参数收集** | 分多轮与用户对话，逐步补齐必填字段 | CREATE_TICKET（5 字段） |
| **字段校验** | 枚举自动 ENUM 校验、自定义校验器 | `TicketType` / `PhoneValidator` |
| **前置条件（@AiPremise）** | 字段满足条件后才开始收集，避免无效追问 | `description` 依赖 `ticketType` |
| **字段标准化（Normalization）** | 将用户输入的多种格式统一标准化，结果存入 `FieldState.data` | PHONE / DATE / DATE_RANGE |
| **上下文变量** | 向抽取提示词注入当前日期等上下文，帮助 LLM 理解"今天 / 这周"等相对时间 | QUERY_BILL |
| **后置任务（@AiPostTask）** | 主任务成功后 best-effort 执行日志等钩子 | `DEMO_LOG` |
| **会话 / 多租户** | `(tenantId, sessionId)` 复合隔离，会话超时过期、重置、取消 | 全部场景 |
| **状态持久化** | 任务状态、对话历史落库（MySQL），重启可恢复 | 全部场景 |
| **限流熔断** | 内置 LLM 调用限流 + 熔断保护 | 框架层 |
| **运行指标** | Actuator + Prometheus 暴露框架运行指标 | `/actuator/prometheus` |

## 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0（框架使用 Liquibase 自动建表）
- 可用的 LLM API Key（deepseek / doubao / kimi，任一即可）

## 快速开始

### 1. 构建并安装 Easy-AI Starter 到本地仓库

测试工程依赖 `com.link:easy-ai-starter:1.0.0-SNAPSHOT`（本地 jar），需先构建安装：

```bash
cd easy-ai-v2
mvn install -pl easy-ai-starter -Dmaven.test.skip=true
```

### 2. 配置 `application.yml`

编辑 `src/main/resources/application.yml`，替换为你的环境配置：

- **数据库**：修改 `spring.datasource.url / username / password`
- **LLM**：在 `llm.providers` 下配置你的 provider `api-key`（保留一个可用 provider 即可，默认使用 `llm.provider: deepseek`）

### 3. 启动应用

方式一：Maven 直接运行（端口 10010）

```bash
mvn spring-boot:run
```

方式二：打包后运行

```bash
mvn package -Dmaven.test.skip=true
java -jar target/easy-ai-starter-test-1.0.0.jar
```

方式三：使用一键脚本 `start-app.bat`（已配置 JDK17 路径，直接双击）

启动成功后访问健康检查：`http://localhost:10010/api/test/health`

## 目录结构

```
easy-ai-starter-test
├── pom.xml                                    # 依赖与构建配置
├── start-app.bat                              # 一键启动脚本
└── src/main
    ├── java/com/link/easyai/test
    │   ├── EasyAiTestApplication.java         # Spring Boot 启动类
    │   ├── controller/TestController.java     # 测试接口（聊天 / 会话 / 任务）
    │   ├── validator/PhoneValidator.java      # 自定义校验器示例（@AiValidator）
    │   ├── normalizer/                        # 自定义标准化器示例
    │   │   ├── PhoneNormalizer.java           #   PHONE：电话统一为纯数字
    │   │   ├── DateNormalizer.java            #   DATE：日期统一为 yyyy-MM-dd
    │   │   └── DateRangeNormalizer.java       #   DATE_RANGE：时间范围 → startTime/endTime
    │   └── task/                              # 业务任务（@AiTask + @AiTaskParam）
    │       ├── LogPostTask.java               # 后置任务（DEMO_LOG）
    │       ├── createTicket/                  # 场景1：创建工单（带参数）
    │       ├── appointment/                   # 场景2：预约登记（标准化 + 前置条件）
    │       ├── queryBill/                     # 场景3：查询账单（时间范围）
    │       └── intenttest/                    # 场景4-13：意图识别稳定性测试（无参数）
    └── resources/application.yml              # 应用配置
```

## 测试场景总览

工程注册了 13 个业务场景：

| # | taskType | 场景 | 是否带参数 | 覆盖重点 |
|---|----------|------|-----------|----------|
| 1 | `CREATE_TICKET` | 创建客服工单 | ✅ 5 字段 | 多轮收集、枚举校验、自定义校验、@AiPremise、后置任务 |
| 2 | `APPOINTMENT` | 预约登记 | ✅ 4+1 字段 | 标准化（PHONE/DATE）、自定义校验、条件字段确认 |
| 3 | `QUERY_BILL` | 查询账单 | ✅ 1 字段 | 上下文变量、时间范围标准化（DATE_RANGE）、从 data 取值 |
| 4 | `ACCOUNT_CANCELLATION` | 账户注销 | ❌ | 意图识别稳定性 |
| 5 | `APPOINTMENT_REGISTRATION` | 预约挂号 | ❌ | 意图识别稳定性 |
| 6 | `COMPLAINT_FEEDBACK` | 投诉反馈 | ❌ | 意图识别稳定性 |
| 7 | `EXPENSE_REIMBURSEMENT` | 费用报销 | ❌ | 意图识别稳定性 |
| 8 | `INVOICE_REQUEST` | 发票申请 | ❌ | 意图识别稳定性 |
| 9 | `LEAVE_APPLICATION` | 请假申请 | ❌ | 意图识别稳定性 |
| 10 | `LOGISTICS_QUERY` | 物流查询 | ❌ | 意图识别稳定性 |
| 11 | `MODIFY_ORDER` | 修改订单 | ❌ | 意图识别稳定性 |
| 12 | `PRODUCT_CONSULTATION` | 产品咨询 | ❌ | 意图识别稳定性 |
| 13 | `REFUND_REQUEST` | 退款申请 | ❌ | 意图识别稳定性 |

> 场景 4-13 为**纯动作场景**（无参数），仅用于验证用户表达相关需求时意图识别能否准确进入对应场景。场景 1-3 覆盖带参数收集的完整链路。

## API 接口

基础路径：`http://localhost:10010`

### POST /api/test/chat — 自动意图识别聊天

请求：

```json
{
  "message": "帮我创建一个投诉工单",
  "sessionId": "s001",
  "tenantId": "0"
}
```

`sessionId` / `tenantId` 缺省时分别默认 `test-session-001` / `0`。

响应（字段说明见下）：

```json
{
  "message": "创建工单\n请提供以下参数：\n  - 联系电话\n  - 工单类型(咨询/投诉/建议)\n  - 客户姓名",
  "taskId": "351921374236774400",
  "taskType": "CREATE_TICKET",
  "completed": false,
  "needMore": true,
  "clarification": false,
  "intentReason": "用户明确表达'创建工单'，与CREATE_TICKET意图直接匹配",
  "intentConfidence": 1.0,
  "intentSource": "LLM",
  "taskState": { "...": "当前任务状态" }
}
```

| 字段 | 说明 |
|------|------|
| `message` | AI 回复内容（收集提示 / 执行结果） |
| `taskId` / `taskType` | 当前任务标识 |
| `completed` | 任务是否已完成执行 |
| `needMore` | 是否仍需收集参数 |
| `clarification` | 是否为澄清/兜底回复 |
| `intentReason` | 意图识别依据（用于调试提示词） |
| `intentConfidence` | 意图置信度 0-1 |
| `intentSource` | 意图来源（如 `LLM`） |
| `taskState` | 任务状态快照（字段收集进度） |
| `taskResult` | 任务执行结果（完成时返回） |

### POST /api/test/chat/{taskType} — 指定任务类型聊天

直接进入指定场景，跳过意图识别。`taskType` 取上表中的类型：

```json
{
  "message": "我想查一下今天的账单",
  "sessionId": "s002",
  "tenantId": "0"
}
```

### POST /easyai/engine/chat/stream — SSE 流式聊天

框架内置的 SSE 流式接口（自动意图识别），响应为 `text/event-stream`，事件序列：

| 事件 | 含义 | data |
|------|------|------|
| `start` | 对话开始 | `{"status":"started"}` |
| `thinking` | 处理中（意图识别/参数提取） | `{"status":"processing"}` |
| `token` | 内容片段（按字符推送，模拟打字机） | `{"content":"..."}` |
| `complete` | 完成，携带完整响应 | `{taskId, message, completed, needMore, clarification, taskType}` |
| `error` | 处理异常 | `{"message":"..."}` |

请求体与 `/api/test/chat` 一致（`message` / `sessionId` / `tenantId`）。

> 底层 LLM 目前为同步阻塞调用，`token` 事件是"分阶段 + 按字符推送"的模拟流式。未来接入流式 LLM 后可替换为真正的 token 级流式。

### 🧪 可视化 SSE 测试页

启动应用后直接访问同源页面（无需跨域配置）：

```
http://localhost:10010/sse-test.html
```

功能：输入 `message` / `sessionId` / `tenantId`，内置预设场景（创建工单、预约登记、查账单、物流查询等），实时展示打字机内容、SSE 事件流日志、complete 完整响应 JSON，支持手动断开连接。

### GET /api/test/session/{sessionId} — 查看会话状态

返回会话当前状态、绑定的任务、轮次、最后活跃时间。

### GET /api/test/task/{taskId} — 查看任务状态

返回任务状态与已收集字段（含 rawValue / value / status）。

### POST /api/test/session/{sessionId}/reset — 重置会话

清除会话与任务绑定。参数：`tenantId`（缺省 `0`）。

### POST /api/test/session/{sessionId}/cancel — 取消当前任务

清除会话绑定的活跃任务。参数：`tenantId`（缺省 `0`）。

### GET /api/test/health — 健康检查

### Actuator 端点

- `GET /actuator/health`
- `GET /actuator/metrics`
- `GET /actuator/prometheus`（框架运行指标，供 Prometheus 采集）

## 配置说明（application.yml）

```yaml
easy-ai:
  enabled: true
  task-engine:
    llm:
      provider: deepseek            # 默认 LLM provider
      model: deepseek-chat          # 默认模型
      max-retries: 2                # 重试次数
      initial-backoff-ms: 1000      # 首次退避间隔
      log-enabled: true             # 是否打印每次 LLM 调用日志（调试用）
    snowflake:
      worker-id: 1                  # 雪花 ID 生成器
      datacenter-id: 0
    resilience:
      enabled: true
      rate-limit-per-second: 20     # LLM 调用限流
    history:
      max-messages: 20              # 注入上下文的对话历史条数上限
    lifecycle:
      timeout-minutes: 30           # 会话超时（分钟）
      max-turns: 20                 # 单任务最大轮次
      expire-enabled: true
      expire-interval-ms: 60000     # 过期检查间隔（生产建议 600000）
```

`llm.providers` 支持多 provider（deepseek / doubao / kimi），通过 `easy-ai.task-engine.llm.provider` 切换默认提供方，配合内置限流熔断与模型回退。

## 数据表

框架通过 Liquibase 在启动时自动创建数据表（无需手工建表）：

| 表 | 说明 |
|----|------|
| `ai_chat_session` | 会话表（租户 + 会话隔离、超时、轮次） |
| `ai_chat_message` | 对话历史表（独立消息表，支持单独历史查询 API） |
| `ai_chat_session_task` | 任务状态表（字段收集进度、意图原因等） |
| `ai_task_config` | 字段提取规则覆盖集（DB 覆盖注解，全局 + 租户两级） |
| `ai_task_lock` | 分布式任务锁表（防止同一任务重复执行） |

## 常见问题

**Q1：启动报 `@AiTaskParam 找不到对应的 @AiTask 执行器`？**
检查 `@AiTaskParam(type = "...")` 的 `type` 是否与 `@AiTask(value = "...")` 一致。

**Q2：数据库连接失败？**
确认 `application.yml` 中 `spring.datasource` 已改为你的 MySQL 连接，且数据库允许远程连接。

**Q3：LLM 调用 401 / 404？**
检查 `llm.providers.<provider>.api-key` 是否正确、`endpoint` 是否为 base URL（不含 `/chat/completions` 后缀）。

**Q4：如何在真实验证意图识别是否稳定？**
使用场景 4-13（`intenttest`），对每个场景用不同的口语化表达调用 `/api/test/chat`，观察返回的 `taskType` 与 `intentReason`。

**Q5：会话为何被重置？**
默认 `lifecycle.timeout-minutes: 30`，超过 30 分钟未活跃的会话会被静默重置为新会话（不产生打扰提示）。可通过配置调整或关闭 `expire-enabled`。
