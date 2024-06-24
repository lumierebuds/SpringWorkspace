package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.common.Utils;
import com.kh.spring.common.model.vo.PageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;
	private final ServletContext application;

	@Override
	public List<Board> selectList(PageInfo pi, Map<String, Object> param) {
		return boardDao.selectList(pi, param);
	}

	@Override
	public int selectListCount(Map<String, Object> param) {
		return boardDao.selectListCount(param);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }) // "어떤 예외가 발생하든 모두 롤백처리(Rollback)를 한다."
	public int insertBoard(Board board, BoardImg bi) throws Exception {
		// 0) 게시글 삽입전, xss 파싱처리, 개행처리 수행 (보통은 다른 클래스에서 처리함. 지금은 서비스에서 처리)
		String boardTitle = board.getBoardTitle();
		String boardContent = board.getBoardContent();
		boardTitle = Utils.XSSHandling(boardTitle);
		boardContent = Utils.XSSHandling(boardContent);
		boardContent = Utils.newLineHandler(boardContent);
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		
		// 1) INSERT BOARD 후
		int result = boardDao.insertBoard(board); // 수행완료시 board에는 boardNo 저장 -> selectKey 확인
		
		// 2) INSERT BOARD_IMG -> bi != null 등록
		int boardNo = board.getBoardNo();
		if (bi != null) {
			bi.setRefBno(boardNo);
			result *= boardDao.insertBoardImg(bi); // 곱셉연산을 이용해서 이미지 저장 결과가 0, 1이든 이전 result(게시글 저장후)가 0이면 0 * 0, 0 * 1로
												   // 0이 나온다.
		}

		if (result == 0) {
			throw new Exception("예외 발생"); // 예외가 발생하면 rollback이 되어서 DB에 저장이 되지 않는다.
		}
		
		// 1-2)의 수행결과는 항상 동일하게 처리해야함
		// 처리결과값 반환
		return result;

	}

	@Override
	public Board selectBoard(int boardNo) {

		return boardDao.selectBoard(boardNo);
	}

	@Override
	public int increaseCount(int boardNo) {

		return boardDao.increaseCount(boardNo);
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public int updateBoard(Board board, MultipartFile upfile, int boardImgNo, String deleteList)
			throws RuntimeException {
		// upfile에 전달된 값이 있으면 이미지 테이블 수정, 추가


		// 원래 사진이 없었고, 추가된 것도 없는 경우 -> 아무것도 안함.

		// 0) 데이터 파싱 -> 생략(insert시와 동일함)

		// 1) Board 테이블 업데이트
		int result = boardDao.updateBoard(board);

		// 2) BOARD_IMG 테이블에 INSERT, UPDATE, DELETE 작업 수행

		if (result == 0) {
			throw new RuntimeException("수정 실패"); // 오류발생으로 트랜잭션 rollback
		}
		
		BoardImg bi = new BoardImg();
		String webPath = "/resources/images/board/N/";
		String serverFolderPath = application.getRealPath(webPath);

		// 사진이 없던곳에서 새롭게 추가된 경우 -> INSERT
		if (boardImgNo == 0 && upfile != null && !upfile.isEmpty()) {
			bi.setRefBno(board.getBoardNo());
			bi.setImgLevel(0);
			
			
			String changeName = Utils.saveFile(upfile, serverFolderPath);
			bi.setChangeName(changeName);
			bi.setOriginName(upfile.getOriginalFilename());

			result *= boardDao.insertBoardImg(bi);
		} 
		// 사진이 있던곳에서 새롭게 추가된 경우 -> UPDATE
		else if (boardImgNo != 0 && upfile != null && !upfile.isEmpty()) {
			bi.setBoardImgNo(boardImgNo);

			String changeName = Utils.saveFile(upfile, serverFolderPath);
			bi.setChangeName(changeName);
			bi.setOriginName(upfile.getOriginalFilename());

			result *= boardDao.updateBoardImg(bi);
		}
		// 사진이 있던곳에서 삭제가 된경우 -> DELETE
		else if (boardImgNo != 0 && upfile.isEmpty() && !deleteList.equals("")) {
			System.out.println(deleteList);
			log.debug("boardImgNo {}, upfile {}", boardImgNo, upfile);
			result *= boardDao.deleteBoardImg(deleteList);
		}
		
		return result;
	}

	@Override
	public List<String> selectFileList() {
		// TODO Auto-generated method stub
		return boardDao.selectFileList();
	}

}
