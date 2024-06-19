package com.kh.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.board.model.vo.BoardType;
import com.kh.spring.common.model.vo.PageInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardDaoImpl implements BoardDao {

	@Autowired
	private final SqlSession sqlSession; // 객체의 불변성을 유지하기 위해 "final"을 붙여준다 (안넣어줘도 되긴함)

	// @RequiredArgsConstructor로 아래코드를 대체
	// public BoardDaoImpl(SqlSession sqlSession) {
	// this.sqlSession = sqlSession;
	// }

	@Override
	public List<BoardType> selectBoardTypeList() {

		return sqlSession.selectList("board.selectBoardTypeList");
	}
	
	// RNUM 방식 
	//	@Override
	//	public List<Board> selectList(PageInfo pi) {
	//
	//		// rowNum 방식의 페이징 처리
	//		// startRow : (currentPage - 1) * pageLimit + 1
	//		// endRow : startRow + pageLimit - 1
	//
	//		int startRow = (pi.getCurrentPage() - 1) * pi.getPageLimit() + 1;
	//		int endRow = startRow + pi.getPageLimit() - 1;
	//
	//		// 쿼리문에 전달할 map
	//		Map<String, Object> param = new HashMap<>();
	//		param.put("startRow", startRow);
	//		param.put("endRow", endRow);
	//
	//		return sqlSession.selectList("board.selectList", param);
	//	}
	
	
	// rowBounds 방식(Mybatis)
	public List<Board> selectList(PageInfo pi, Map<String, Object> param) {
		// MyBatis의 RowBounds객체를 이용한 페이징 처리

		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit(); // ** pi.getPageLimit() -> pi.getBoardLimit() **
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);

		return sqlSession.selectList("board.selectList", param, rowBounds);

	}

	@Override
	public int selectListCount(Map<String, Object> param) {

		return sqlSession.selectOne("board.selectListCount", param);
	}

	@Override
	public int insertBoard(Board board) {

		return sqlSession.insert("board.insertBoard", board);
	}

	@Override
	public int insertBoardImg(BoardImg bi) {

		return sqlSession.insert("board.insertBoardImg", bi);
	}

	@Override
	public Board selectBoard(int boardNo) {

		return sqlSession.selectOne("board.selectBoard", boardNo);
	}

	@Override
	public int increaseCount(int boardNo) {

		return sqlSession.update("board.increaseCount", boardNo);
	}

	@Override
	public int updateBoardImg(BoardImg bi) {

		return sqlSession.update("board.updateBoardImg", bi);
	}

	@Override
	public int updateBoard(Board board) {
		return sqlSession.update("board.updateBoard", board);
	}

}
