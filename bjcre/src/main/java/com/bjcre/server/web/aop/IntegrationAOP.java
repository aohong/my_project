package com.bjcre.server.web.aop;

import java.text.SimpleDateFormat;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.springframework.validation.BindingResult;

import com.bjcre.server.web.exception.BaseBizException;
import com.bjcre.server.web.exception.BaseReturnStatus;
import com.bjcre.server.web.result.BaseResult;
import com.bjcre.server.web.result.ValidateResultHandler;

/**
 * IntegrationAOP.java的实现描述:统一AOP， 1.登陆轨迹日志 2.方法性能：时间消耗
 * 
 * @author aohong 14-12-24 下午3:06
 */
@Aspect
public class IntegrationAOP {
	private static final Logger logger = Logger.getLogger(IntegrationAOP.class);


	@SuppressWarnings("unused")
	@Pointcut("execution(* com.bjcre.resource.TestResouce.*(..))")
	private void anyMethod() {
	}

	@Around("anyMethod()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        Object result = "";
		try {
			// 打印开始日志，包括参数
			Object[] args = joinPoint.getArgs();
			StringBuilder methodSB = new StringBuilder();
			BindingResult bindingResult = null;
			if (args != null) {
				for (Object obj : args) {
					if (obj != null) {
						if (obj instanceof BindingResult) { // 结果验证参数,不需要打印
							bindingResult = (BindingResult) obj;
                        } else if (obj instanceof HttpServletRequestWrapper) {
                            HttpServletRequestWrapper requestWrapper = (HttpServletRequestWrapper) obj;
                            methodSB.append(
                                    this.getRequestLog(requestWrapper
                                    .getRequest()));
                        } else if (obj instanceof Request) {
                            methodSB.append(this.getRequestLog((Request) obj));
						} else if (!(obj instanceof Response)) {
							// org.mortbay.jetty.Response不打印，因为都是 HTTP/1.1 200
							methodSB.append(obj.toString().trim()).append("\n");
						}
					}
				}
			}
			logger.info("Begin method : " + methodName + "(). args:\n"
					+ methodSB.toString().trim());
			if (bindingResult != null)
				ValidateResultHandler.doCheck(bindingResult);
			result = joinPoint.proceed();
			return result;

        } catch (Exception e) {
			// logger.error(e);
			// throw e;
			BaseResult errorResult = new BaseResult();
            if (e instanceof BaseBizException) {
                errorResult.setStatus(((BaseBizException) e).getResultCode());
				errorResult.setMessage(e.getMessage());
				logger.warn("occur business exception:" + e.getMessage());
            } else {
                errorResult.setStatus(BaseReturnStatus.SERVER_ERROR.getValue());
                errorResult.setMessage(BaseReturnStatus.SERVER_ERROR.getDesc());
				logger.error("occur unknown exception:", e);
			}
			return errorResult;
		} finally {
            long end = System.currentTimeMillis();
            long expendTime = end - start;
			SimpleDateFormat df = new SimpleDateFormat("mm:ss.SSS");
			String resultString = result.toString();
			if (resultString != null && resultString.length() > 1000) {
				resultString = resultString.substring(0, 1000) + "...";
			}
			logger.info("End   method : " + methodName + "(). consuming time: "
					+ expendTime + " ms, formart " + df.format(expendTime)
					+ " result:\n" + resultString);
		}
    }

    private String getRequestLog(ServletRequest request) {
        StringBuilder methodSB = new StringBuilder();
        methodSB.append("RemoteHost: ").append(request.getRemoteHost())
                .append(",").append(request.getRemoteAddr()).append(":")
                .append(request.getRemotePort()).append(" do ").append(request)
                .append("\n");
        return methodSB.toString();
    }

}
