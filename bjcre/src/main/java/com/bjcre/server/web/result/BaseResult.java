package com.bjcre.server.web.result;


/**
 * 
 * 类Result.java的实现描述：结算系统返回结果
 *  
 * @author zhangyiming8 2014年12月23日 上午9:16:18
 */
public class BaseResult {
    private int status = 200;
    private String message = "成功";

	@Override
	public String toString() {
		return "Result [status=" + status + ", message=" + message + "]";
	}

	public int getStatus() {
        return status;
    }

	public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
