package com.kh.spring.common.interceptor;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.BoardType;

public class BoardTypeInterceptor implements HandlerInterceptor {
	
	@Autowired
	private BoardDao boardDao;

	@Autowired
	private ServletContext application; // 언제 컨테이너에 등록되었지? 다양한 내장 객체들이 "등록이 되었다."

	// 전처리 함수 : 컨트롤러가 서블릿의 요청을 처리하기 전에 먼저 실행되는 함수
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 항상 저장시키는게 아니라 처음한번만 저장 - null일때 만든다. 이후의 요청부턴 안만듬
		if (application.getAttribute("boardTypeList") == null) {

			// DB에서 boardTypeList 조회후 applicationContext에 저장
			List<BoardType> list = boardDao.selectBoardTypeList();
			application.setAttribute("boardTypeList", list); // "boardTypeList" - 이제 이 값으로 접근할 수 있다.
			application.setAttribute("contextPath", request.getContextPath());

		}

		return true;

	}

}
