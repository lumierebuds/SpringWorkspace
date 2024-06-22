package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring.member.model.vo.Member;

// 로그인체크 인터셉터 : 로그인을 하지 않은 상태로 허용하지 않는 url이용시 메인페이지로 리다이렉트 
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 요청정보 url 정보 확인

		// /spring/board/list/T -> /board/list/T
		String requestUrl = request.getRequestURI().substring(request.getContextPath().length());

		// 로그인한 사용자 정보 조회
		HttpSession session = request.getSession();
		Member loginUser = (Member) session.getAttribute("loginUser");
		
		if (loginUser != null) {
			return true;
		} else {

			session.setAttribute("alertMsg", "로그인 후 이용할 수 있습니다.");

			// 로그인 완료후 이동할 url을 session에 저장
			String queryString = request.getQueryString();
			String nextUrl = requestUrl + "?" + queryString;

			session.setAttribute("nextUrl", nextUrl); // 로그인 요청후 성공시, 지정된 nextUrl 주소로 이동.

			response.sendRedirect(request.getContextPath());
			return false;
		}

	}
}
