package com.woniu.mvc;

import java.util.HashMap;

public class Results extends HashMap<String, Object> {

    public static final String SUCCESS_STATUS = "0";
    public static final String ERROR_STATUS = "-1";

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";
    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";
    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    public Results() {
    }

    public Results(String code) {
        super.put(CODE_TAG, code);
    }

    public Results(String code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    public Results(String code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
    }

    /**
     * 成功
     *
     * @return
     */
    public static Results success() {
        return new Results(SUCCESS_STATUS);
    }

    /**
     * 成功数据
     */
    public static Results success(Object data) {
        Results results = success();
        results.put(DATA_TAG, data);

        return results;
    }

    /**
     * 返回错误消息
     */
    public static Results error(String msg) {
        return new Results(ERROR_STATUS, msg);
    }

    /**
     * 返回错误消息
     */
    public static Results error(String code, String msg) {
        return new Results(code, msg);
    }
}
