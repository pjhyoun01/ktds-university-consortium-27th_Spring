package com.ktdsuniversity.edu.exceptions.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.exceptions.TMDBApiException;
import com.ktdsuniversity.edu.exceptions.TMDBException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(TMDBException.class)
	public String viewErrorPage(TMDBException hse, Model model) {
		logger.error(hse.getMessage(), hse);

		String message = hse.getMessage();
		model.addAttribute("errorMessage", message);

		String errorPage = hse.getErrorPage();

		Object modelData = hse.getObject();
		if (modelData != null) {
			model.addAttribute("errorData", modelData);
		}

		return errorPage;
	}

	@ResponseBody
	@ExceptionHandler(TMDBApiException.class)
	public Map<String, Object> returnErrorJson(TMDBApiException hsae) {
		logger.error(hsae.getMessage(), hsae);

		int status = hsae.getErrorStatus();
		Object errorObjet = hsae.getError();

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("status", status);
		responseData.put("error", errorObjet);

		return responseData;
	}

	@ExceptionHandler(RuntimeException.class)
	public String viewSystemErrorPage(RuntimeException re) {
		logger.error(re.getMessage(), re);

		return "errors/500";
	}

}
