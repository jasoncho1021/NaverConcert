package kr.or.connect.resv.interceptor;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LogInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("{} 를 호출했습니다.", handler.toString());
		logger.debug("클라이언트 IP {}", request.getRemoteAddr());
		logger.debug("요청 URL {}", request.getRequestURL());
		logger.debug("요청 URI {}", request.getRequestURI());
		logger.debug("요청 시간 {}", LocalDateTime.now());

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			logger.debug("종료 {} 가종료되었습니다. {} 를 view로 사용합니다.", handler.toString(), modelAndView.getViewName());
		} else {
			logger.debug("종료 {} 가종료되었습니다. ", handler.toString());
		}
	}
}
