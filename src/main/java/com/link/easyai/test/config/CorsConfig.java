package com.link.easyai.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局 CORS 配置（仅测试工程使用）。
 * <p>
 * 允许本地 HTML 测试页直接跨域访问本服务：
 * <ul>
 *   <li>通过 {@code http://localhost:10010/sse-test.html} 同源访问（无需 CORS）</li>
 *   <li>直接双击打开 {@code sse-test.html}（file:// 协议，origin 为 null）跨域访问</li>
 * </ul>
 * 使用 {@code allowedOriginPatterns("*")} 以兼容 null origin（file:// 场景）。
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
