package kr.or.connect.resv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class EmailInterceptor extends HandlerInterceptorAdapter {

	private static final String ATTRIBUTE_NAME = "RESERVATION_EMAIL";

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		String reservationEmail = (String) request.getSession().getAttribute(ATTRIBUTE_NAME);

		if (reservationEmail != null) {
			modelAndView.addObject("reservationEmail", reservationEmail);
		}
	}
}
