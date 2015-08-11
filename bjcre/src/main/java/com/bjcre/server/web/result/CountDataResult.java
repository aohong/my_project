package com.bjcre.server.web.result;


/**
 * 
 * 类Result.java的实现描述：结算系统返回结果
 *  
 * @author zhangyiming8 2014年12月23日 上午9:16:18
 */
public class CountDataResult extends DataResult {
	private int totalCount = 0;

	@Override
    public String toString() {
        return "CountDataResult [totalCount=" + totalCount + ", getStatus()="
                + getStatus() + ", getMessage()=" + getMessage()
                + ", getData()=" + getData() + "]";
    }

	public int getTotalCount() {
        return totalCount;
    }

	public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
