package org.silly.rats.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {
	@ExceptionHandler
	public ExceptionMessage exceptionHandler(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		return ExceptionMessage.builder()
				.message(ex.getMessage())
				.uri(request.getRequestURI())
				.method(request.getMethod())
				.status(response.getStatus())
				.timestamp(System.nanoTime())
				.build();
	}
}
