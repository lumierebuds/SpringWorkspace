package com.kh.spring.member.model.dao;

import com.kh.spring.member.model.vo.Member;

public interface MemberDao {

	Member login(Member m);

	int insertMember(Member m);

	int updateMember(Member m);

	int idCheck(String userId);

}
