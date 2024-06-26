package com.kh.spring.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // 어플리케이션 전역에 에러를 잡아준다.
public class ExceptionController {

	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {
		e.printStackTrace();
		model.addAttribute("errorMsg", "서비스 이용중 문제가 발생했습니다. 관리자에게 문의해주세요");

		return "common/errorPage";

	}

	// @ExceptionHandle를 붙인 여러 에러를 처리해줄 수 있다.
}
