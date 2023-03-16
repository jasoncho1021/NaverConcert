package kr.or.connect.resv.interceptor;

import java.time.LocalDateTime;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.or.connect.resv.util.Keywords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("=> 클라이언트 IP {}", request.getRemoteAddr());
        log.info("=> 요청 URL {}", request.getRequestURL());
        log.info("=> 요청 URI {}", request.getRequestURI());
        log.info("=> 요청 시간 {}", LocalDateTime.now());

        String reservationEmail = (String) request.getSession().getAttribute(Keywords.AUTHENTICATION_KEY);
        log.info("=> reservationEmail: " + reservationEmail);

        String sessionId = request.getSession().getId();

        Cookie userCookie = WebUtils.getCookie(request, "JSESSIONID");
        if (userCookie != null) {
            log.info("--> JSessionId: " + userCookie.getValue());
        } else {
            log.info("--> JSessionId: null");
        }

        log.info("--> sessionId: " + sessionId);

        log.info("                       ");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
        if (modelAndView != null) {
            log.info("==> 종료 {} 가종료되었습니다. {} 를 view로 사용합니다.", handler, modelAndView.getViewName());
        } else {
            log.info("==> 종료 {} 가종료되었습니다. ", handler);
        }

        String reservationEmail = (String) request.getSession().getAttribute(Keywords.AUTHENTICATION_KEY);
        log.info("==> " + reservationEmail);
        Object userData = request.getSession().getAttribute(Keywords.USER_DATA);
        log.info("==> " + userData);

        log.info("                       ");
    }
}
