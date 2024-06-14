package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDao dao;

	@Override
	public Member login(Member m) {
		// SqlSession 생성.
		// 결과값을 반환받은후, close, commit/rollback
		// 컨트롤러에게 반환

		// SqlSession에 대한 관리를 "스프링 컨테이너가 주관" 하므로 직접 생성하지 않아도 된다.

		return dao.login(m);
	}

}
