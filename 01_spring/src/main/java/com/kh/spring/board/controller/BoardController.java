package com.kh.spring.board.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.core.io.ResourceLoader;
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
	
	
	// 게시판 
	
	// ■ 게시판 목록
	// /list/P, /list/N, /list/T
	@GetMapping("/list/{boardCode}") // 동적 파라미터 설정 : {변수명} - 실제 회사에선 보드코드를 확인해서 존재하지않다면 오류를 발생시킴
	public String selectList(
			@PathVariable("boardCode") String boardCode, // PathVariable로 등록된 변수는 request에 자동저장
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, // 현재 내가 요청한 페이지
			Model model, // 페이지 렌더링을 위한 모델 객체
			@RequestParam(value="paramMap", required=false) Map<String, Object> paramMap // 요청된 파라미터값을 map에 넣어서 전달한다.
			) {


		
		// 페이징 작업 처리
		// 1) 게시글 갯수 카운팅
		int listCount = boardService.selectListCount();
		int pageLimit = 10; // 웹 페이지에서 보여지는 페이지 개수
		int boardLimit = 10; // 게시글 개수

		PageInfo pi = Pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

		// 게시글 데이터 조회
		List<Board> list = boardService.selectList(pi); // 페이지 정보로 조회

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
				){
		
		// 업무로직
		
		// 1) 웹서버에 클라이언트가 전달한 FILE 저장
		if (upfile != null && !upfile.getOriginalFilename().equals("")) {
			// upfile 객체가 null이 아니고, 전달된 파일이 빈문자열이 아닐때
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
			
		}
		
		// 2) 저장 완료시 파일이 저장된 경로를 BOARD_IMG에 등록후 테이블에 추가
		// -> 1) BOARD INSERT
		// -> 2) BOARD_IMG INSERT -> 클라이언트가 upfile에 데이터를 작성했을때만. 
		

		// 3) 반환값을 통해 메시지 등록
		

		// 4) 응답 페이지 설정
		
		return "redirect:/board/list/" + boardCode;
		
	}
		
}
