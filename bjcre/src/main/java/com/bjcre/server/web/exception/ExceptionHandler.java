package com.bjcre.server.web.exception;

/**
 * 类ExceptionHandler.java的实现描述：统一异常处理
 * 
 * @author aohong 14-12-24 下午3:06
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.bjcre.server.web.result.BaseResult;

@Component("exceptionHandler")
public class ExceptionHandler implements
        HandlerExceptionResolver {
    private static final Logger logger = Logger
            .getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(
			HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mav = new ModelAndView();

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		BaseResult result = new BaseResult();
        if (e instanceof BaseBizException) {
            result.setStatus(((BaseBizException) e).getResultCode());
			result.setMessage(e.getMessage());
			logger.warn("occur business exception:" + e.getMessage());
        } else {
            result.setStatus(BaseReturnStatus.SERVER_ERROR.getValue());
            result.setMessage(BaseReturnStatus.SERVER_ERROR.getDesc());

            StringBuilder requestString = new StringBuilder();
			if (httpServletRequest instanceof Request) {
				Request jettyRequest = (Request) httpServletRequest;
                requestString.append("request=[").append(jettyRequest.getMethod()).append(jettyRequest.getUri());
            } else {
                requestString.append(httpServletRequest.toString());
            }
            logger.error("occur unknown exception:" + requestString.toString(), e);
        }
        jsonView.setExtractValueFromSingleKeyModel(true);
        mav.addObject(result);
        mav.setView(jsonView);
        return mav;
    }

}
