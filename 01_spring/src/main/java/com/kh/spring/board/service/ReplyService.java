package com.kh.spring.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.ReplyDao;
import com.kh.spring.board.model.vo.Reply;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {

	private final ReplyDao dao;

	public List<Reply> selectReplyList(int boardNo) {
		return dao.selectReplyList(boardNo);
	}

	public int insertReply(Reply reply) {

		return dao.insertReply(reply);
	}

	public int updateReply(Reply reply) {
		// TODO Auto-generated method stub
		return dao.updateReply(reply);
	}

}
