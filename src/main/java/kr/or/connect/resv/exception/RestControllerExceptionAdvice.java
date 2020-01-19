package kr.or.connect.resv.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionAdvice {
	private static final Logger log = LoggerFactory.getLogger(RestControllerExceptionAdvice.class);

	@ExceptionHandler(UnAuthenticationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ErrorMessage unAuthentication(UnAuthenticationException e) {
		log.debug("JSON API UnAuthenticationException is happened! {}", e.getMessage());
		return new ErrorMessage(e.getMessage());
	}

}
