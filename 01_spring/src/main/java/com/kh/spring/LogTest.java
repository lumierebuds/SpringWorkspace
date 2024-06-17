package com.kh.spring;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogTest {
	/*
	 * Loggin Level 
	 * - fatal : 아주 심각한 에러 -> 지금은 존재하지 않음 
	 * - error : 요청처리중 발생하는 오류에 사용(Exception 클래스에서 사용) 
	 * - warn : 경고성메시지. 실행에는 문제없지만, 향우 오류가 발생할 경우가 있을때 사용
	 * - info : 요청처리중 발생하는 정보성 메세지 출력시 사용
	 * - debug : 개발중 필요한 로그가 있을 경우 사용
	 * - trace : 개발용 debug 범위를 한정해서 출력
	 * 
	 */

	// Logger 객체 생성방식 1
	// Logger log = LoggerFactory.getLogger(LogTest.class);

	// Logger 객체 생성방식 2
	// Logger log = LoggerFactory.getLogger(LogTest.class);

	// Logger 객체 생성방식 3
	// lombok으로 로거객체 얻어오기

	public static void main(String[] args) {
		log.error("error - {}", "에러메세지");
		log.warn("warn - {} ", "경고메세지");
		log.info("info - {} ", "인포메시지");
		log.debug("debug - {}", "디버그");
		log.trace("trace - {} ", "트레이스");

	}

}
