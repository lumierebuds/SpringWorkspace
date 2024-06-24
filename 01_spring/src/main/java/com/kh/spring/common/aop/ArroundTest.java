package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
@Order(2) // return value : Member(userNo.. ) 와 === service end === 사이에 끼워 놓음(우선순위)
public class ArroundTest {

	@Around("CommonPointcut.implPointcut()")
	public Object checkRunningTime(ProceedingJoinPoint jp) throws Throwable {
		// ProceedingJoinPoint ? Before/After 처리기능을 함께 제공. 리턴값을 얻어올 수 있는 메서드도 함께 제공

		// before 코드
		long start = System.currentTimeMillis();

		Object obj = jp.proceed();
		// 메서드 실행.(실제 핵심 업무)
		// 원본 객체의 메서드가 실행되어야 할 메서드를 대신 해준다(한번만)
		
		log.info("obj : {}", obj);
		// after 코드
		long end = System.currentTimeMillis();

		log.debug("Running Time : {} ms", (end - start));

		return obj;
	}

}
