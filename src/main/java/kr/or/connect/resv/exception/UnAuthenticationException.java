package kr.or.connect.resv.exception;

public class UnAuthenticationException extends Exception {

	private static final long serialVersionUID = 1L;
	final static private String message = "권한없음";

	@Override
	public String getMessage() {
		return message;
	}

}
