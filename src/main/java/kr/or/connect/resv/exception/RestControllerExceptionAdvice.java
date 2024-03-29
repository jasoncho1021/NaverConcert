package kr.or.connect.resv.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllException(Exception e) {
		log.debug("--> exception happened! {}", e.getMessage());
		e.printStackTrace();
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/*
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
		String name = ex.getParameterName();
		log.debug(name + " parameter is missing");
		return new ResponseEntity<>(name + " parameter is missing", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.debug(" parameter is missing");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	*/

}
