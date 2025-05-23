package com.camel.common;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    public static Map<String, Object> convertJsonStringToMap(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
    }

    public static String getLocalTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return now.format(formatter);
    }

    // 현재 시간보다 10분 전의 시간을 반환
    public static long getTenMinutesAgoLocalTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenMinutesAgo = now.minusMinutes(10);
        return Timestamp.valueOf(tenMinutesAgo).getTime() / 1000;
    }

    public static long getFirstDayOfMonthLocalTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDayOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        return Timestamp.valueOf(firstDayOfMonth).getTime() / 1000;
    }

    public static long getLocalTimestamp() {
        return Instant.now().getEpochSecond();
    }

    public static String nullSafeString(String value) {
        return value != null ? value : "";
    }

    public static String nullSafeString(Integer value, String defaultValue) {
        return value != null ? value.toString() : defaultValue;
    }

    public static int getIntValueSafely(JsonNode node, String fieldName, int defaultValue) {
        JsonNode fieldNode = node.get(fieldName);
        if (fieldNode != null && fieldNode.isInt()) {
            return fieldNode.asInt();
        }
        return defaultValue;
    }

    // Masking 처리 메소드
    // Ex) 길이가 2인 문자열 -> hi -> h*
    // 길이가 3인 문자열 -> abc -> a*c
    // 길이가 4인 문자열 -> abcd -> a**d
    // 길이가 5인 문자열 -> abcde -> ab**e
    // 길이가 6인 문자열 -> abcdef -> ab**ef
    // 길이가 7인 문자열 -> abcdefg -> ab****fg
    // 길이가 8인 문자열 -> abcdefgh -> abc****gh
    // 이후부턴 앞3글자 , 뒤 2글자 제외 모두 마스킹.
    public static String maskString(String str) {
        
        char[] chars = str.toCharArray();

        if(chars.length == 2){
            chars[1] = '*';
        } else if(chars.length == 3){
            chars[1] = '*';
        } else if(chars.length == 4){
            chars[1] = '*';
            chars[2] = '*';
        } else if(chars.length == 5){
            chars[2] = '*';
            chars[3] = '*';
        } else if(chars.length == 6){
            chars[2] = '*';
            chars[3] = '*';
        } else if(chars.length == 7){
            chars[2] = '*';
            chars[3] = '*';
            chars[4] = '*';
            chars[5] = '*';
        } else if(chars.length == 8){
            chars[3] = '*';
            chars[4] = '*';
            chars[5] = '*';
            chars[6] = '*';
        } else {
            int length = chars.length;
            for(int i = 0; i<length; i++) {
                if(i<=2){
                    continue;
                } else if (i>=length-2) {
                    continue;
                } else {
                    chars[i] = '*';
                }
            }
        }

        String result = new String(chars);

        return result;
    }

    public static String cleanString(String input) {
        return input.replaceAll("[,]", "").trim();
    }

    public static String getKoreanDayOfWeek(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> "월";
            case TUESDAY -> "화";
            case WEDNESDAY -> "수";
            case THURSDAY -> "목";
            case FRIDAY -> "금";
            case SATURDAY -> "토";
            case SUNDAY -> "일";
            default -> "";
        };
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        // Date를 Instant로 변환
        Instant instant = date.toInstant();
        // Instant를 LocalDateTime으로 변환 (기본 시스템 시간대 사용)
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static String convertToFormattedLocalDateTime(Date date) {
        // Date를 Instant로 변환
        Instant instant = date.toInstant();
        // Instant를 LocalDateTime으로 변환 (기본 시스템 시간대 사용)
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // DateTimeFormatter로 T 없이 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }


    public static CustomMap removeHeaderCustomMap(CustomMap param) {
        if(param.containsKey("headers")){
            param.remove("headers");
        }

        return param;
    }


    public static String makeBearerTokenFormat(String token){
        return String.format("Bearer %s", token);
    }
 }
