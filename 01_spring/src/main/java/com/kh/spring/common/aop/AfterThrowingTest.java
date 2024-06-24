package com.kh.spring.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class AfterThrowingTest {
	
	@AfterThrowing(pointcut = "CommonPointcut.implPointcut()", throwing = "exceptionObj")
	public void serviceReturnException(JoinPoint jp, Exception exceptionObj) {
		log.error("에러 메세지 : {}", exceptionObj.getMessage() + "\n");
		
	}
}
