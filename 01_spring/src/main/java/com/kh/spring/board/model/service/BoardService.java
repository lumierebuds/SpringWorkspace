package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {

	List<Board> selectList(PageInfo pi, Map<String, Object> param);

	int selectListCount(Map<String, Object> param);

	int insertBoard(Board board, BoardImg bi) throws Exception;

	Board selectBoard(int boardNo);

	int increaseCount(int boardNo);

	int updateBoard(Board board, MultipartFile upfile, int boardImgNo, String deleteList);

	List<String> selectFileList();

}
