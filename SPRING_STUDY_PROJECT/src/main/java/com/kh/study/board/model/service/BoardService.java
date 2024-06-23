package com.kh.study.board.model.service;

import java.util.List;

import com.kh.study.board.model.vo.Board;
import com.kh.study.board.model.vo.BoardImg;

public interface BoardService {

	List<Board> selectList();

	int insertBoard(Board board, List<BoardImg> biArr) throws Exception;

	Board selectBoard(int no);

}
