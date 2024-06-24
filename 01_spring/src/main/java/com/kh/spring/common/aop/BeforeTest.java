package com.kh.spring.common.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class BeforeTest {

	@Before("CommonPointcut.implPointcut()")
	public void beforeService(JoinPoint jp) {
		// joinpoint -> pointcut이 들어갈 수 있는 메서드

		// JoinPoint : advice가 실제로 적용되는 TargetObject에 접근할 수 있는 메소드를 제공하는 인터페이스.
		// TargetObject의 클래스 정보, 전달되는 매개변수, 메서드명, 반환값, 예외처리등등... 얻어올 수 있음

		// 모든 advice 메서드의 첫번째 매개변수는 JoinPoint 고정

		StringBuilder sb = new StringBuilder();
		sb.append("=======================================================================\n");
		sb.append("start : " + jp.getTarget().getClass().getSimpleName() + " - "); // BoardServiceImpl : 간단한 클래스 이
		sb.append(jp.getSignature().getName()); // getSignature() => 메서드 객체
		sb.append("(" + Arrays.toString(jp.getArgs()) + ")"); // jp.getArgs() => 객체 배열

		log.debug(sb.toString());
	}
	
}
