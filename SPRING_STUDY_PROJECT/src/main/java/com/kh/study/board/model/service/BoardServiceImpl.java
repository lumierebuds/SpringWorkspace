package com.kh.study.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.study.board.model.dao.BoardDao;
import com.kh.study.board.model.vo.Board;
import com.kh.study.board.model.vo.BoardImg;
import com.kh.study.commons.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardDao dao;
	@Override
	public List<Board> selectList() {

		return dao.selectList();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int insertBoard(Board board, List<BoardImg> biArr) throws Exception {

		// 0) 게시글 삽입전,xss 파싱처리, 개행처리 수행
		String title = board.getTitle();
		String content = board.getContent();
		title = Utils.XSSHandling(title);
		content = Utils.XSSHandling(content);
		content = Utils.newLineHandler(content);
		board.setTitle(title);
		board.setContent(content);

		// 1) INSERT BOARD 후
		int result = dao.insertBoard(board);

		// 2) INSERT BOARD_IMG biArr 반복하여 등록하기(null인값이 등록되지 않게 막는다)
		int refBno = board.getNo();
		for (BoardImg bi : biArr) {
			if (bi != null) {
				bi.setRefNo(refBno);
				result *= dao.insertBoardImg(bi);
				// 곱셉연산으로 데이터 삽입이잘 이루어 졌는지 확인
			}
		}
		
		if(result == 0) {
			throw new Exception("예외 발생");
		}
		
		// 1-2) 수행결과는 항상 동일하게 처리해야함
		// 처리결과값 반환
		return result;
		
	}

	@Override
	public Board selectBoard(int no) {

		return dao.selectBoard(no);
	}

}
