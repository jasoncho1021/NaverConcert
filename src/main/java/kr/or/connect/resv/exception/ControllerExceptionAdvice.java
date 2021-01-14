package kr.or.connect.resv.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionAdvice {
	private static final Logger log = LoggerFactory.getLogger(ControllerExceptionAdvice.class);

	@ExceptionHandler(UnAuthenticationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public String unAuthentication() {
		log.debug("UnAuthenticationException is happened!");
		return "/user/login";
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String handleMissingParams(MissingServletRequestParameterException ex) {
		String name = ex.getParameterName();
		log.debug(name + " parameter is missing");
		return "mainpage";
	}
}
