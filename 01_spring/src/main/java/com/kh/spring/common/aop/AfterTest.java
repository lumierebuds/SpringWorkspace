package com.kh.spring.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
@Order(1) // after 관련 어노테이션들의 순서를 매기는 역할
public class AfterTest {
	
	@After("CommonPointcut.implPointcut()")
	public void serviceEnd(JoinPoint jp) {
		log.debug("================================== service end ==================================\n");

	}
	
}
