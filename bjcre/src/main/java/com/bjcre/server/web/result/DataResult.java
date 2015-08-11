package com.bjcre.server.web.result;


/**
 * 
 * 类Result.java的实现描述：结算系统返回结果
 *  
 * @author zhangyiming8 2014年12月23日 上午9:16:18
 */
public class DataResult extends BaseResult {
	private Object data;

	@Override
    public String toString() {
        return "DataResult [getStatus()=" + getStatus() + ", getMessage()="
                + getMessage() + ", data=" + data + "]";
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
