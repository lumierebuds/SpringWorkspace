package com.kh.study.board.model.dao;

import java.util.List;

import com.kh.study.board.model.vo.Board;
import com.kh.study.board.model.vo.BoardImg;

public interface BoardDao {

	List<Board> selectList();

	int insertBoard(Board board);

	int insertBoardImg(BoardImg bi);

	Board selectBoard(int no);

}
