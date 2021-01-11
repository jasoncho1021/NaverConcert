package kr.or.connect.resv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.connect.resv.util.Keywords;

@Component
public class SignInInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		HttpSession session = request.getSession();
		ModelMap modelMap = modelAndView.getModelMap();
		Object userVO = modelMap.get("userVO");

		if (userVO != null) {
			session.setAttribute(Keywords.ATTRIBUTE_NAME, modelMap.get("reservationEmail"));
		} else {
			response.sendRedirect("bookinglogin");
		}

		super.postHandle(request, response, handler, modelAndView);
	}

}
