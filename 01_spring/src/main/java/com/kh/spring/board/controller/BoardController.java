package com.kh.spring.board.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardExt;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.board.service.BoardService;
import com.kh.spring.common.Utils;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagenation;
import com.kh.spring.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@SessionAttributes({ "loginUser" })
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
		
	private final BoardService boardService;
	private final ServletContext application;
	private final ResourceLoader resourceLoader;
	// Spring framework에서 제공하는 자원을 로드(입력)하는데 사용되는 인터페이스
	// resourceLoader를 통해 파일시스템, 클래스패스, URL자원 등을 모두 하나의 클래스로 로드가 가능하다.
	// classpath:/mapper/**/..xml
	
	
	// 게시판 
	
	// ■ 게시판 목록
	// /list/P, /list/N, /list/T
	@GetMapping("/list/{boardCode}") // 동적 파라미터 설정 : {변수명} - 실제 회사에선 보드코드를 확인해서 존재하지않다면 오류를 발생시킴
	public String selectList(
			@PathVariable("boardCode") String boardCode, // PathVariable로 등록된 변수는 request에 자동저장
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, // 현재 내가 요청한 페이지
			Model model, // 페이지 렌더링을 위한 모델 객체
			@RequestParam Map<String, Object> paramMap
			) {

		log.info("paramMap?? {}" + paramMap);
		paramMap.put("boardCode", boardCode);
		
		// 페이징 작업 처리
		// 1) 게시글 갯수 카운팅
		int listCount = boardService.selectListCount(paramMap);
		int pageLimit = 10; // 웹 페이지에서 보여지는 페이지 개수
		int boardLimit = 10; // 게시글 개수

		PageInfo pi = Pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

		// 게시글 데이터 조회
		List<Board> list = boardService.selectList(pi, paramMap); // 페이지 정보로 조회

		// 응답View 페이지에 게시글 데이터 삽입 
		model.addAttribute("list", list);
		model.addAttribute("pi", pi);
		
		// viewName 반환 
		return "board/boardListView";

		// ** boardCode가 존재하지 않을때 오류 발생(추가해보기) ** 

	}
	
	@GetMapping("/insert/{boardCode}")
	public String enrollBoard(@PathVariable("boardCode") String boardCode) {

		return "board/boardEnrollForm";

	}
	
	@PostMapping("/insert/{boardCode}")
	public String insertBoard(
					@PathVariable("boardCode") String boardCode, 
					Board board, 
					@ModelAttribute("loginUser") Member loginUser, 
					// "모델 객체에 등록된 loginUser 객체를 가져오겠다는 의미"
					// 로그인시 Model 객체로 등록되어, sesion 스코프로 이관되었고 Model로 가져올 수
				    // 있다(@SessionAttribute가 있어야함)
					Model model,
					RedirectAttributes ra, // alertMsg
					// 첨부파일
					@RequestParam(value="upfile", required=false) MultipartFile upfile // 항상 첨부파일이 없을 수 있으니, "required=false"
	) {
		
		// 업무로직
		
		// 1) 웹서버에 클라이언트가 전달한 FILE 저장
		BoardImg bi = null; 
		if (upfile != null && !upfile.getOriginalFilename().equals("")) {
			// upfile 객체가 null이 아니고, 전달된 파일명이 빈문자열이 아닐때
			// 첨부파일, 이미지등을 저장할 저장경로를 얻어오기. - 보통은 어플리케이션 컨텍스트의 값으로 하지만 지금은 "문자열로 설정"
			String webPath = "/resources/images/board/" + boardCode + "/"; // 게시판 "유형별"로 이미지 저장 (N, P)
			String serverFolderPath = application.getRealPath(webPath);

			// 디렉토리가 존재하지 않는다면 생성하는 코드 추가
			File dir = new File(serverFolderPath);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			// 사용자가 등록한 첨부파일의 이름을 수정
			String changeName = Utils.saveFile(upfile, serverFolderPath);
			
			bi = new BoardImg();
			bi.setChangeName(changeName);
			bi.setOriginName(upfile.getOriginalFilename()); 
			bi.setImgLevel(0); // 0: 첨부파일 , 1 : 썸네일, 그외는 상세보기용 이미지
		}
		

		// 2) 저장 완료시 파일이 저장된 경로를 BOARD_IMG에 등록후 테이블에 추가
		// -> 1) BOARD INSERT
		// -> 2) BOARD_IMG INSERT -> 클라이언트가 upfile에 데이터를 작성했을때만. 
		
		// boardWriter, boardCd 추가
		board.setBoardCd(boardCode); // pathVariable 추가
		board.setBoardWriter(String.valueOf(loginUser.getUserNo())); // 로그인된 사용자의 "PK 아이디(정수)" 를 String으로 변환해 setter 함수 호출
		
		int result = 0;
		try {
			result = boardService.insertBoard(board, bi);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3) 반환값을 통해 메시지 등록
		String url = "";
		if (result > 0) {
			ra.addAttribute("alertMsg", "글 작성 성공");
			url = "redirect:/board/detail/" + boardCode + "/" + board.getBoardNo(); // 게시글 상세보기 페이지로 이동
		} else {
			model.addAttribute("errorMsg", "게시글 작성 실패");
			url = "common/errorPage";
		}
		

		// 4) 응답 페이지 설정
		
		return url;
		
	}

	// ■ 게시글 상세정보
	@GetMapping("/detail/{boardCode}/{boardNo}")
	public String selectBoard(
			@PathVariable("boardCode") String boardCode,
			@PathVariable("boardNo") int boardNo,
			Model model,
			HttpServletRequest req,
			HttpServletResponse res,
			@ModelAttribute("loginUser") Member loginUser // 세션 스코프에 존재하는 "로그인한 사용자 정보"
	) {

		// 업무로직
		// 보드코드랑 보드no를 이용해 게시글 정보를 조회

		Board board = boardService.selectBoard(boardNo);

		// (조회수 증가서비스 호출예정)
		// 1. 조회수 중복 증가 방지 -> 쿠키를 이용
		// 	  쿠키 안에 클라이언트의 유저번호와, 게시글번호 목록을 함께 저장
		
		if (board != null) { // "게시글이 존재할때"

			Cookie cookie = null; // 쿠키객체 null 로 초기화

			Cookie[] cookies = req.getCookies(); // 배열의 형태로 사용자의 쿠키정보들 반환
			
			if (cookies != null && cookies.length > 0) { // 쿠키배열이 존재하고, 배열 길이가 0보다 크면
				for (Cookie c : cookies) {
					// readBoardNo라는 이름의 쿠키를 찾기
					if ("readBoardNo".equals(c.getName())) { // 반복을 진행중인 쿠키배열중 쿠키의 이름이 "readBoardNo"이면?
						cookie = c; // 찾았다면 cookie에 대입
						break;
					}
				}
			}

			int result = 0; // 조회수 증가서비스 호출후 결과값을 담아줄 변수

			if (cookie == null) {
				// readBoardNo쿠키 생성
				cookie = new Cookie("readBoardNo", boardNo + "");
				// 조회수 증가서비스 호출
				result = boardService.increaseCount(boardNo); // update 쿼리문 실행할것ㄴ
			} else {
				// 기존 쿠키값중 중복되는 게시글번호가 없는 경우 조회수 증가 서비스 호출
				// readBoardNo에 현재 게시글번호 추가
				String[] arr = cookie.getValue().split("/"); // 1, 2, 5, 11 -> [1,2,5,11]
				List<String> list = Arrays.asList(arr); // 메서드 사용을 위한 변환
				if (list.indexOf(boardNo + "") == -1) { // 조회결과가 없다면
					// 조회수 증가서비스 호출
					result = boardService.increaseCount(boardNo);
					// 쿠키값에 현재 게시글번호 추가
					cookie.setValue(cookie.getValue() + "/" + boardNo);

				}

				// 중복되는 게시글번호가 이미 존재하는 경우 종료
			}

			if (result > 0) {
				board.setCount(board.getCount() + 1); // 1 증가 시키기

				cookie.setPath(req.getContextPath());
				cookie.setMaxAge(1 * 60 * 60); // 1시간동안 유지 -> 쿠키가 클라이언트에서 유지될 수 있는 기간
				res.addCookie(cookie); // 사용자에게 쿠기 추가
			}
		} else { // "조회된 데이터가 null인 경우"
			model.addAttribute("errorMsg", "존재하지 않는 게시글입니다..");
			return "common/errorPage";
		}
		

		// 조회된 데이터를 model에 담아서 view로 이동
		log.debug("board : {}" + board);
		model.addAttribute("board", board);
		return "board/boardDetailView";
	}

	@GetMapping("/fileDownload/{boardNo}")
	public ResponseEntity<Resource> fileDownload(
			@PathVariable("boardNo") int boardNo
			){
		
		ResponseEntity<Resource> responseEntity = null;
		// db에서 첨부파일의 정보를 조회 
		BoardExt board = (BoardExt) boardService.selectBoard(boardNo);
		
		if (!(board.getAttachment() != null && board.getAttachment().getChangeName() != null)) { // 게시글에서 "첨부파일이 없고 파일명이
																									// 없을때"
			return ResponseEntity.notFound().build(); // 찾고자 하는 값이 없다면 "404"에러를 응답값으로 사용 
		}
		

		// Resource 객체로 파일 얻어오기 
		String saveDir = application.getRealPath("/resources/images/board/" + board.getBoardCd() + "/"); // 저장된 주소
		File downFile = new File(saveDir, board.getAttachment().getChangeName());
		Resource resource = resourceLoader.getResource("file:" + downFile); // 파일 프로토콜로 "저장함"
		
		
		try {
			String filename = new String(board.getAttachment().getOriginName().getBytes("utf-8"), "iso-8859-1");
			// 파일을 클라이언트가 저장할땐 "원본이름(originalName)"으로 저장 
			// "iso-8859-1" 표준으로 저장
			responseEntity = ResponseEntity
					.ok()
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+filename)
					.body(resource);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		return responseEntity;
	}
	
	// ■ 게시글 수정 기능
	// 게시글 수정 페이지
	@GetMapping("/update/{boardCode}/{boardNo}")
	public String updateBoard(
			@PathVariable("boardCode") String boardCode,
			@PathVariable("boardNo") int boardNo, Model model) {

		// 작성했던 게시글 정보가 보이게 한후, 모델에 담아서 포워딩
		BoardExt board = (BoardExt) boardService.selectBoard(boardNo);
		// 게시글 본문내용 변경
		board.setBoardContent(Utils.newLineClear(board.getBoardContent())); // <br> 태그를 다시 "개행문자로 되돌리기"

		model.addAttribute("board", board);

		return "board/boardUpdateForm";
	}
	
	// 게시글 수정 요청처리
	@PostMapping("/update/{boardCode}/{boardNo}")
	public String updateBoard2(
			@PathVariable("boardCode") String boardCode,
			@PathVariable("boardNo") int boardNo,
			Model model,
			Board board, // 저장할 게시판 데이터
			RedirectAttributes ra,
			// 첨부파일
			@RequestParam(value = "upfile", required = false) MultipartFile upfile, int boardImgNo) {

		// 업무로직

		// BOARD테이블 수정하고
		log.debug("board ? {} boardImgNo ? {}", board, boardImgNo); // {} 안에 내용이 순서대로 치환된다.

		int result = 0;

		result = boardService.updateBoard(board, upfile, boardImgNo);

		if (result > 0) {
			ra.addFlashAttribute("alertMsg", "게시글 수정 성공");
			// 작업 성공시 리다이렉트
			return "redirect:/board/detail/" + boardCode + "/" + boardNo;
		} else {
			model.addAttribute("errorMsg", "게시글 수정 실패");
			return "common/errorPage";
		}

	}
	
	
}
