package com.kh.spring.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;

	@Override
	public List<Board> selectList(PageInfo pi) {
		return boardDao.selectList(pi);
	}

	@Override
	public int selectListCount() {
		return boardDao.selectListCount();
	}

}
