package kr.or.connect.resv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.connect.resv.util.Keywords;

@Component
public class EmailInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		String reservationEmail = (String) request.getSession().getAttribute(Keywords.ATTRIBUTE_NAME);

		if (reservationEmail != null) {
			modelAndView.addObject("reservationEmail", reservationEmail);
		}
	}
}
