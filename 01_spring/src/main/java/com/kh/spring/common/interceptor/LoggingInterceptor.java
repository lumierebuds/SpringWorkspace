package com.kh.spring.common.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

	// 사용자가 사용하고 있는 핸드폰 종류
	static String logMp[] = { "iphone", "ipod", "android" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 접속한 사용자의 장비가 pc인지 모바일인지 확인
		String currentDevice = "Web"; // 기본값 pc
		String ua = request.getHeader("user-agent").toLowerCase(); // 사용자가 접속한 기기의 정보
		for (String device : logMp) {
			if (ua.indexOf(device) > -1) {
				currentDevice = "mobile";
				break;
			}
		}

		// 접속서버 url, 서버정보 추가
		HttpSession session = request.getSession();

		// 사용자의 도메인
		String currentDomain = request.getServerName();

		// 사용자의 포트번호
		int currentPort = request.getServerPort();

		// 사용자의 아이디 (로그인하지 않은 기본값은 빈문자열)
		String userId = "";
		Member loginUser = (Member) session.getAttribute("loginUser"); // 세션 속성값으로 찾아옴
		if (loginUser != null) userId = loginUser.getUserId();

		// url 자원정보

		String uri = request.getRequestURI();

		// 쿼리문자열

		String queryString = "";
		if (request.getMethod().equals("GET")) {
			queryString = request.getQueryString();
		}
		else {
			// ** 순수 서블릿 방식으로 값을 뽑아서 출력 **
			Map<String, String[]> map = request.getParameterMap();
			Object[] keys = map.keySet().toArray();

			// key=value&key=value
			for (int i = 0; i < keys.length; i++) {
				if (i > 0) {
					queryString += "&";
				}
				String[] values = map.get(keys[i]);
				queryString += keys[i] + "=";
					
				int count =0; 
				for(String value : values) {
					if(count >0 ) queryString += ",";
					queryString += value;
					count++;
				}
			}

		}
		
		// 사용자의 ip 주소
		String ip = getIp(request);

		String msg = ip + " : " + currentDevice + " : " + userId + " " + (request.isSecure() ? "https" : "http") + "://"
				+ currentDomain + ":" + currentPort + uri + (queryString != null ? "?" + queryString : "");
		log.info("{}", msg);

		return true;

	}

	// ip값 얻어오는 메서드
	private String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
