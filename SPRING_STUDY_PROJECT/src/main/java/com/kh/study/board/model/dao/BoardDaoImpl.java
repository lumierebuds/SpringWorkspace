package com.kh.study.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.study.board.model.vo.Board;
import com.kh.study.board.model.vo.BoardImg;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardDaoImpl implements BoardDao {

	private final SqlSession sqlSession;

	@Override
	public List<Board> selectList() {

		return sqlSession.selectList("study.selectList");
	}

	@Override
	public int insertBoard(Board board) {

		return sqlSession.insert("study.insertBoard", board);
	}

	@Override
	public int insertBoardImg(BoardImg bi) {

		return sqlSession.insert("study.insertBoardImg", bi);
	}

	@Override
	public Board selectBoard(int no) {

		return sqlSession.selectOne("study.selectBoard", no);

	}
}
