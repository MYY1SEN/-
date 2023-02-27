package com.myq.internalcommon.dto;

import com.myq.internalcommon.constatnt.CommonStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;

    /**
     * 成功的响应方法
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult(CommonStatusEnum.SUCCESS.getCode(), CommonStatusEnum.SUCCESS.getValue(), data);
    }

    public static <T> ResponseResult<T> success(){
       return new ResponseResult<T>().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue());
    }

    /**
     * 失败返回结果
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseResult fail(int code, String message) {
        return new ResponseResult().setCode(code).setMessage(message);
    }

    public static <T> ResponseResult fail(int code, String message, String data) {
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

    /**
     * 统一的失败
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult fail(T data) {
        return new ResponseResult().setData(data);
    }
}
