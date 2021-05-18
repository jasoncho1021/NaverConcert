package kr.or.connect.resv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.connect.resv.util.Keywords;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		logger.debug("--> {} 를 호출했습니다.", handler.toString());
		String sessionReservationEmail = (String) request.getSession().getAttribute(Keywords.AUTHENTICATION_KEY);
		if (sessionReservationEmail == null) {
			response.sendRedirect("/");
			return false;
		}

		return super.preHandle(request, response, handler);
	}

}
