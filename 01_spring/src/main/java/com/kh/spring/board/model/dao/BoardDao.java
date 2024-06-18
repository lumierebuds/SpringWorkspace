package com.kh.spring.board.model.dao;

import java.util.List;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardType;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardDao {

	List<BoardType> selectBoardTypeList();

	List<Board> selectList(PageInfo pi);

	int selectListCount();

}
