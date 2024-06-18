package com.kh.spring.board.service;

import java.util.List;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {

	List<Board> selectList(PageInfo pi);

	int selectListCount();

}
