package kr.or.connect.resv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.or.connect.resv.util.Keywords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class SignInInterceptor implements HandlerInterceptor {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		HttpSession session = request.getSession();
		ModelMap modelMap = modelAndView.getModelMap();
		Object userVO = modelMap.get(Keywords.USER_DATA);

		log.info("--?> " + modelMap.get(Keywords.AUTHENTICATION_KEY));

		if (userVO != null) {
			session.setAttribute(Keywords.AUTHENTICATION_KEY, modelMap.get(Keywords.AUTHENTICATION_KEY));
			session.setAttribute(Keywords.USER_DATA, userVO);
		} else {
			response.sendRedirect("bookinglogin");
		}

	}

}
