package com.kh.spring.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Reply;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ReplyDao {

	private final SqlSession sqlSession;

	public List<Reply> selectReplyList(int boardNo) {
		return sqlSession.selectList("reply.selectReplyList", boardNo);
	}

	public int insertReply(Reply reply) {
		return sqlSession.insert("reply.insertReply", reply);
	}

	public int updateReply(Reply reply) {
		return sqlSession.update("reply.updateReply", reply);
	}

}
