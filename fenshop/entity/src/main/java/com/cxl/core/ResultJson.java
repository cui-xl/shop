package com.cxl.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResultJson<T> {
    private  Integer code;
    private  String  message;
    private  T content;

    public ResultJson(ResultCode code, String message, T content) {
        this.code = code.getCode();
        this.message = message;
        this.content = content;
    }
    public static <T>ResultJson<T> getInstance(ResultCode code,String message,T content){
        return new ResultJson(code,message,content);

    }
    public static  <T> ResultJson<T> success(String message,T content){
        return ResultJson.getInstance(ResultCode.SUCCESS,message,content);

    }
    public static  <T> ResultJson<T> success(T content){
        return  success(null,content);
    }
    public static <T> ResultJson<T> failed(String message,T content){
        return ResultJson.getInstance(ResultCode.FAILED,message,content);

    }
    public static <T> ResultJson<T> failed(String message){

        return ResultJson.failed(message,null);
    }
    public static  <T> ResultJson<T> unlogin(String message){
        return  ResultJson.getInstance(ResultCode.UNLOGIN,message,null);
    }
    public static  <T> ResultJson<T> forbid(String message){
        return  ResultJson.getInstance(ResultCode.FORBID,"该用户无此操作权限",null);
    }
}

