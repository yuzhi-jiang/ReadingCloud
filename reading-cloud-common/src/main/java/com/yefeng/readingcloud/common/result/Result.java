package com.yefeng.readingcloud.common.result;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 操作结果集封装
 * @author yefeng
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Result<T>  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer code;
    private String msg;
    private T data;

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result() {

    }
    /**
     * 得到json字符串
     *
     * @return {@link String}
     */
    private String getJsonString() {
        String res = "{";
        if (code != null) res += "\"errCode\":" + code;
        if (msg != null) res += ",\"errMsg\":\"" + msg + "\"";
        if (data != null) res += ",\"data\":" + data;
        res += "}";
        return res;
    }
    public JSONObject toJsonObject() {
        JSONObject jsonObject = JSONObject.parseObject(getJsonString());
        return jsonObject;
    }

    @Override
    public String toString() {
        return getJsonString();
    }
    /**
     * 构建消息内容
     * @param msg
     * @return
     */
    public Result buildMessage(String msg){
        this.setMsg(msg);
        return this;
    }

    /**
     * 构建消息data的值，key默认为data
     * @param obj data值
     * @return
     */
    public Result buildData(T obj){
        this.setData(obj);
        return this;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
