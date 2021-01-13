package kr.or.connect.resv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.connect.resv.util.Keywords;

@Component
public class EmailInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String reservationEmail = (String) request.getSession().getAttribute(Keywords.ATTRIBUTE_NAME);

		logger.debug("==> BEFORE");
		logger.debug("==> 요청 URL {}", request.getRequestURL());
		logger.debug("==>" + reservationEmail);

		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String reservationEmail = (String) request.getSession().getAttribute(Keywords.ATTRIBUTE_NAME);

		logger.debug("==> AFTER");
		logger.debug("==> 요청 URL {}", request.getRequestURL());
		logger.debug("==>" + reservationEmail);

		if (reservationEmail != null) {
			modelAndView.addObject(Keywords.ATTRIBUTE_NAME, reservationEmail);
		}
	}
}
