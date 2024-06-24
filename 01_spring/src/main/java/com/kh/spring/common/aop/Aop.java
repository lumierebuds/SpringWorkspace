package com.kh.spring.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect // Advice + PointCut -> 실제로 실행시킬 공통코드(advice)와 적용할 위치(pointCut)이 적용된 클래스임을 명시
public class Aop {

	// @Pointcut(advice가 끼어들어서 수행될 클래스나 메서드의 위치)
	/*
	 * joinPoint : advice가 적용될 수 있는 모든 지점. 모든 비지니스 메서드가 여기에 포함된다.
	 * 
	 * pointCut : joinPoint들 중에서 실제로 advice가 실행될 지점.
	 * 
	 */

	/*
	 * Pointcut 작성방법
	 * 
	 * @Pointcut("execution([접근제한자] 반환명  풀클래스명.메소드명([매개변수]))")
	 * 
	 * Pointcut 표현식 
	 *  * : 모든 | 아무값 
	 * .. : 하위 | 아래 | 0개 이상의 매개변수 
	 * 
	 * 
	 * 
	 */
	
	// com.kh.spring.board 패키지 "아래"에서 클래스들중 "Impl"로 끝나는 클래스 내부에 존재하는 모든 메서드가 
	// 호출되는 시점을 pointCut으로 지정

	// @Before("testPointCut()") // advice 적용 위치
	public void start() {
		log.debug("=================== sertvice start ===================");
	}

	// @After("testPointCut()") // advice 적용 위치
	public void end() {
		log.debug("=================== sertvice end ===================");
	}
	
	// @Pointcut : pointcut을 정의해두는 역할. 
	@Pointcut("execution(* com.kh.spring.board..*Impl.*(..))")
	public void testPointCut() {

	}
}


