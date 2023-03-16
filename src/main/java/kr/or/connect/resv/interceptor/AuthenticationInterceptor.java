package kr.or.connect.resv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.or.connect.resv.util.Keywords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		log.info("--> {} 를 호출했습니다.", handler.toString());
		String sessionReservationEmail = (String) request.getSession().getAttribute(Keywords.AUTHENTICATION_KEY);
		if (sessionReservationEmail == null) {
			response.sendRedirect("/");
			return false;
		}

		return true;
	}

}
