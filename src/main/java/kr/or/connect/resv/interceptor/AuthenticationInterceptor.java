package kr.or.connect.resv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.connect.resv.util.Keywords;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String sessionReservationEmail = (String) request.getSession().getAttribute(Keywords.ATTRIBUTE_NAME);
		if (sessionReservationEmail == null) {
			response.sendRedirect("/");
			return false;
		}

		return super.preHandle(request, response, handler);
	}

}
