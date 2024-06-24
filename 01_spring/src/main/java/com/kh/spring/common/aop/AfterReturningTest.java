package com.kh.spring.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
@Order(3)
public class AfterReturningTest {
	
	// 메서드 실행 이후 "반환값"을 얻어오는 기능의 어노테이션
	@AfterReturning(pointcut = "CommonPointcut.implPointcut()", returning = "returnObj")
	public void returnValue(JoinPoint jp, Object returnObj) {
		if (returnObj instanceof Member) {
			Member m = (Member) returnObj;
			m.setUserName("짱구");

		}
		log.debug("return value : {}", returnObj);
	}
	
	/*
	 * Object returnObj = targetObject.메서드실행();
	 * 
	 * 
	 * 
	 */
}
