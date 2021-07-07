package kr.or.connect.resv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.connect.resv.util.Keywords;

@Component
public class SignInInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		/*		Cookie userCookie = WebUtils.getCookie(request, Keywords.COOKIE);
				String sessionId = "";
				if (userCookie != null) {
					sessionId = userCookie.getValue();
				}
		
				logger.debug("--> sessionId: " + sessionId);*/

		HttpSession session = request.getSession();
		ModelMap modelMap = modelAndView.getModelMap();
		Object userVO = modelMap.get(Keywords.USER_DATA);

		logger.debug("--> " + modelMap.get(Keywords.AUTHENTICATION_KEY));

		if (userVO != null) {
			session.setAttribute(Keywords.AUTHENTICATION_KEY, modelMap.get(Keywords.AUTHENTICATION_KEY));
			session.setAttribute(Keywords.USER_DATA, userVO);

/*			Cookie cookie = new Cookie(Keywords.COOKIE, session.getId());
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(cookie);*/
		} else {
			response.sendRedirect("bookinglogin");
		}

	}

}
