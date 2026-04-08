package com.ktdsuniversity.edu.exceptions.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ktdsuniversity.edu.exceptions.HelloSpringException;

// Spring Application에서 던지고 catch되지 않은 예외들을 처리하는 클래스
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(HelloSpringException.class);

	// helloSpringException이 실행되면
	// viewErrorPage가 실행되면
	// 결과는 ModelAndView가 된다
	// @return 사용자에게 보여줄 페이지
	@ExceptionHandler(HelloSpringException.class)
	public String viewErrorPage(HelloSpringException hse, Model model) {
		logger.error(hse.getMessage(), hse);

		String message = hse.getMessage();
		model.addAttribute("errorMessage", message);

		String errorPage = hse.getErrorPage();

		Object modelData = hse.getObject();
		if (modelData != null) {
			model.addAttribute("", modelData);
		}

		return errorPage;
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String viewSystem(RuntimeException re) {
		logger.error(re.getMessage(), re);
		return "errors/500";
	}
}
