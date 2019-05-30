package com.callan.service.provider.config;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class ObjectUtil {
    /**
     * Object对象转成String，若Object为null，则返回defValue(空串不变)
     *
     * @param obj
     * @param defValue
     */
    public static String objToString(Object obj, String defValue) {
        if (obj == null) {
            return defValue;
        }

        String value = StringUtils.trim(obj.toString());
        return value == null ? defValue : value;
    }

    /**
     * Object对象转成String，若Object为null，则返回空串
     *
     * @param obj
     */
    public static String objToString(Object obj) {
        return objToString(obj, "");
    }

    /**
     * Object对象转成Integer，若Object为null或者为空串""，则返回默认值defValue
     *
     * @param obj
     * @param defValue
     */
    public static Integer objToInt(Object obj, Integer defValue) {
        if (obj == null) {
            return defValue;
        } else if (obj instanceof Integer) {
            return (Integer) obj;
        }

        String value = StringUtils.trim(obj.toString());
        if (value == null || value.isEmpty()) {
            return defValue;
        }

        return Integer.parseInt(value);
    }

    /**
     * Object对象转成Integer，若Object为null或者为空串""，则返回默认值0
     *
     * @param obj
     */
    public static Integer objToInt(Object obj) {
        try {
            return objToInt(obj, 0);
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * Object对象转成Short，若Object为null或者为空串""，则返回默认值defValue
     *
     * @param obj
     * @param defValue
     */
    public static Short objToShort(Object obj, Short defValue) {
        if (obj == null) {
            return defValue;
        } else if (obj instanceof Short) {
            return (Short) obj;
        }

        String value = StringUtils.trim(obj.toString());
        if (value == null || value.isEmpty()) {
            return defValue;
        }

        return Short.parseShort(value);
    }

    /**
     * Object对象转成Short，若Object为null或者为空串""，则返回默认值0
     *
     * @param obj
     */
    public static Short objToShort(Object obj) {
        return objToShort(obj, (short) 0);
    }

    /**
     * Object对象转成Long，若Object为null或者为空串""，则返回默认值defValue
     *
     * @param obj
     * @param defValue
     */
    public static Long objToLong(Object obj, Long defValue) {
        if (obj == null) {
            return defValue;
        } else if (obj instanceof Long) {
            return (Long) obj;
        }

        String value = StringUtils.trim(obj.toString());
        if (value == null || value.isEmpty()) {
            return defValue;
        }

        return Long.parseLong(value);
    }

    /**
     * Object对象转成Long，若Object为null或者为空串""，则返回默认值0
     *
     * @param obj
     */
    public static Long objToLong(Object obj) {
        return objToLong(obj, 0L);
    }

    /**
     * Object对象转成Float，若Object为null或者为空串""，则返回默认值0
     *
     * @param obj
     */
    public static Float objToFloat(Object obj) {
        return objToFloat(obj, 0f);
    }

    private static Float objToFloat(Object obj, float defValue) {
        if (obj == null) {
            return defValue;
        } else if (obj instanceof Float) {
            return (Float) obj;
        }

        String value = StringUtils.trim(obj.toString());
        if (value == null || value.isEmpty()) {
            return defValue;
        }

        return Float.parseFloat(value);
    }

    /**
     * Object对象转成Long，若Object为null或者为空串""，则返回默认值defValue
     *
     * @param obj
     * @param defValue
     */
    public static Double objToDouble(Object obj, Double defValue) {
        if (obj == null) {
            return defValue;
        } else if (obj instanceof Double) {
            return (Double) obj;
        }

        String value = StringUtils.trim(obj.toString());
        if (value == null || value.isEmpty()) {
            return defValue;
        }

        return Double.parseDouble(value);
    }

    /**
     * Object对象转成Long，若Object为null或者为空串""，则返回默认值0
     *
     * @param obj
     */
    public static Double objToDouble(Object obj) {
        return objToDouble(obj, 0.0);
    }

    /**
     * Object对象转成Date，若Object为null，则返回默认值，会尝试用format格式解析字符串
     *
     * @param obj
     * @param defValue
     * @param format
     */
    public static Date objToDate(Object obj, Date defValue, String format) {
        if (obj == null) {
            return defValue;
        } else if (obj instanceof Date) {
            return (Date) obj;
        }

        if (format != null && !format.isEmpty()) {
            String value = StringUtils.trim(obj.toString());
            if (value == null || value.isEmpty()) {
                return defValue;
            }

            DateFormat dateFormat = new SimpleDateFormat(format);
            try {
                return dateFormat.parse(value);
            } catch (ParseException e) {
                System.out.println("parse obj to date fail, obj: " + value + ", format: " + format + ", errorMessage: "
                        + e.getMessage() + ", errorOffset: " + e.getErrorOffset());
                return defValue;
            }
        }

        System.out.println("objToDate fail, obj=" + obj);
        return defValue;
    }

    /**
     * Object对象转成Date，若Object为null，则返回默认值
     *
     * @param obj
     * @param defValue
     */
    public static Date objToDate(Object obj, Date defValue) {
        return objToDate(obj, defValue, null);
    }

    /**
     * Object对象转成Date，若Object为null，则返回当前日期
     *
     * @param obj
     */
    public static Date objToDate(Object obj) {
        return objToDate(obj, new Date());
    }

    /**
     * Object对象转成Boolean，若Object为null或非布尔值，则返回默认值
     *
     * @param obj
     * @param defValue
     */
    public static Boolean objToBool(Object obj, Boolean defValue) {
        if (obj == null) {
            return defValue;
        } else if (obj instanceof Boolean) {
            return (Boolean) obj;
        }

        String value = obj.toString();
        if (value != null) {
            value = value.trim().toLowerCase();
            if (value.equals("true")) {
                return true;
            } else if (value.equals("false")) {
                return false;
            }
        }

        return defValue;
    }

    public static void main(String[] args) throws Exception {
        Object obj1 = null;
        Object obj2 = "";
        Object obj3 = "123";
        Object obj4 = true;

        System.out.println("bool=" + objToBool(obj3, false));
        System.out.println("date=" + objToDate(obj3, new Date()));
        System.out.println("bool=" + objToBool(obj4, false));
        System.out.println("obj1=" + objToString(obj1));
        System.out.println("obj2=" + objToString(obj2));
        System.out.println("obj3=" + objToString(obj3));
        System.out.println("obj1=" + objToInt(obj1));
        System.out.println("obj2=" + objToInt(obj2));
        System.out.println("obj3=" + objToInt(obj3));
        System.out.println("obj1=" + objToLong(obj1));
        System.out.println("obj2=" + objToLong(obj2));
        System.out.println("obj3=" + objToLong(obj3));
    }
}
