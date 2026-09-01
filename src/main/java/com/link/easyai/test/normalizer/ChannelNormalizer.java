package com.link.easyai.test.normalizer;

import com.link.easyai.starter.engine.context.FieldContext;
import com.link.easyai.starter.engine.normalization.FieldNormalizer;
import com.link.easyai.starter.engine.normalization.NormalizationResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 渠道名称标准化器（AiMapping 测试场景专用）。
 * <p>
 * 负责从渠道名称中<b>解析渠道编码</b>并写入 {@code data}，
 * 供 {@code @AiMapping(source = "$data.channelCode")} 映射使用。
 * <p>
 * 与 {@code ChannelValidator} 职责分离：
 * <ul>
 *   <li>Validator：判定渠道名称格式是否合法（通过/不通过）</li>
 *   <li>Normalizer：解析/转换——提取渠道编码，返回标准化值 + 业务数据</li>
 * </ul>
 * <p>
 * type() = "CHANNEL"，与 {@code @AiField(normalize = "CHANNEL")} 对应。
 * 校验通过后、映射前执行，data 会合并进 FieldState.data。
 */
@Component
public class ChannelNormalizer implements FieldNormalizer {

    /** 渠道编码：字母开头 + 2~9 位数字（如 U009-HKUPS红单5000价 → U009、D011 → D011） */
    private static final Pattern CODE_PATTERN = Pattern.compile("^([A-Za-z]\\d{2,9})");

    @Override
    public String type() {
        return "CHANNEL";
    }

    @Override
    public NormalizationResult normalize(Object value, FieldContext context, Map<String, Object> params) {
        if (value == null) {
            return NormalizationResult.fail("渠道名称为空");
        }
        String name = String.valueOf(value).trim();
        if (name.isEmpty()) {
            return NormalizationResult.fail("渠道名称为空");
        }

        Matcher matcher = CODE_PATTERN.matcher(name);
        if (!matcher.find()) {
            return NormalizationResult.fail("无法提取渠道编码：" + name);
        }

        String channelCode = matcher.group(1);
        // 标准化值保持渠道名原样，同时把解析出的编码写入 data，供 $data.channelCode 映射
        return NormalizationResult.success(name, Map.of("channelCode", channelCode,"channelId",12345678L));
    }
}
