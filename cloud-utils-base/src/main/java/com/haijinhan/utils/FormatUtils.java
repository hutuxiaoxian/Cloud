package com.haijinhan.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public final class FormatUtils {

    private static final long SECOND_THRESHOLD = 10_000_000_000L; // 10^10

    private FormatUtils() {}

    // 1. 空数据与非空数据判断
    public static boolean isEmpty(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String) return ((String) obj).trim().isEmpty();
        if (obj instanceof Collection) return ((Collection<?>) obj).isEmpty();
        if (obj instanceof Map) return ((Map<?, ?>) obj).isEmpty();
        if (obj.getClass().isArray()) return java.lang.reflect.Array.getLength(obj) == 0;
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    // 2. 数据格式判断 (正则校验)
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^1[3-9]\\d{9}$");
    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", Pattern.CASE_INSENSITIVE);
    private static final Pattern ID_CARD_PATTERN = Pattern.compile(
            "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]$");

    public static boolean isEmail(String str) {
        return isNotEmpty(str) && EMAIL_PATTERN.matcher(str).matches();
    }

    public static boolean isPhone(String str) {
        return isNotEmpty(str) && PHONE_PATTERN.matcher(str).matches();
    }

    public static boolean isUrl(String str) {
        return isNotEmpty(str) && URL_PATTERN.matcher(str).matches();
    }

    public static boolean isIdCard(String str) {
        return isNotEmpty(str) && ID_CARD_PATTERN.matcher(str).matches();
    }

    public static String timestampToString(long timestamp) {
        boolean isMill = isMillisecond(timestamp);
        return LocalDateTime.ofInstant(
                isMill ? Instant.ofEpochMilli(timestamp): Instant.ofEpochSecond(timestamp),
                ZoneId.systemDefault()
        ).format(DateTimeFormatter.ofPattern(isMill? "yyyy-MM-dd HH:mm:ss.SSS":"yyyy-MM-dd HH:mm:ss"));
    }

    public static long stringToTimestamp(String dateTimeStr) {
        boolean isMill = isMillisecond(dateTimeStr);
        Instant instant = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(isMill ? "yyyy-MM-dd HH:mm:ss.SSS" : "yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return isMill ? instant.toEpochMilli() : instant.getEpochSecond();
    }

    /**
     * 判断时间戳是否为毫秒级
     * 秒级时间戳在当前时代是 10 位数（< 10^10），毫秒级是 13 位数（>= 10^12）
     */
    public static boolean isMillisecond(long timestamp) {
        return timestamp >= SECOND_THRESHOLD;
    }
    /**
     * 判断时间字符串是否包含毫秒部分
     * 例如: "2026-09-01 22:14:06.123" → true
     *       "2026-09-01 22:14:06"     → false
     *
     * @param str 时间字符串
     * @return 如果字符串包含小数点且小数点后恰好为3位（毫秒），返回 true
     */
    public static boolean isMillisecond(String str) {
        return str.contains(".") && str.indexOf(".") == str.length() - 3;
    }
}