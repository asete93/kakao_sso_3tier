package com.camel.common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomMap extends HashMap<String, Object> {

    // Map을 반환하는 메소드
    public CustomMap getMap(String key) {
        Object value = this.get(key);
        if (value instanceof Map) {
            return new CustomMap((Map) value);
        }
        throw new IllegalArgumentException("Value is not a Map");
    }

    // List를 반환하는 메소드
    public List<Object> getArray(String key) {
        Object value = this.get(key);
        if (value instanceof List) {
            return (List<Object>) value;
        }
        throw new IllegalArgumentException("Value is not a List");
    }

    // List<CustomMap>을 반환하는 메소드
    public List<CustomMap> getCustomArray(String key) {
        Object value = this.get(key);
        if (value instanceof List) {
            List<CustomMap> list = new ArrayList<>();
            for (Object item : (List<?>) value) {
                if (item instanceof Map) {
                    list.add(new CustomMap((Map<String, Object>) item));
                } else {
                    throw new IllegalArgumentException("List item is not a Map");
                }
            }
            return list;
        }
        throw new IllegalArgumentException("Value is not a List");
    }

    // String을 반환하는 메소드
    public String getString(String key) {
        Object value = this.get(key);
        if (value instanceof String) {
            return (String) value;
        }
        throw new IllegalArgumentException("Value is not a String");
    }

    public Long getLong(String key) {
        Object value = this.get(key);
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Integer) {
            return ((Integer) value).longValue();
        } else if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Value is not a Long");
            }
        }
        throw new IllegalArgumentException("Value is not a Long");
    }

    public int getInt(String key) {
        Object value = this.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
               throw new IllegalArgumentException("Value is not a Int");
            }
        }

        throw new IllegalArgumentException("Value is not a Int");
    }

    public Boolean getBoolean(String key) {
        Object value = this.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Integer) {
            // 1을 true, 0을 false로 간주
            return (Integer) value == 1;
        } else if (value instanceof String) {
            // 문자열이 "true" 또는 "false"인지 확인
            String strValue = ((String) value).toLowerCase();
            if ("true".equals(strValue)) {
                return true;
            } else if ("false".equals(strValue)) {
                return false;
            }
        }

        throw new IllegalArgumentException("Value is not a Boolean");
    }

    // CustomMap 생성자: 기존 Map에서 CustomMap을 만들기 위함
    public CustomMap(Map<String, Object> map) {
        super(map);
    }

    // 기본 생성자
    public CustomMap() {
        super();
    }

    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this); // HashMap을 JSON 문자열로 변환
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 변환 실패 시 null 반환
        }
    }

    public CustomMap(String jsonString) {
        super();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {});
            this.putAll(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jsonStringToMap(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Object> rawMap = objectMapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {});
            this.clear();        // 기존 값 제거 (선택사항)
            this.putAll(rawMap); // 현재 객체에 넣기
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
