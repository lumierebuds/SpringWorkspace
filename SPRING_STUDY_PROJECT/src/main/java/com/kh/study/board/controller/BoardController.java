package com.kh.study.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.study.board.model.service.BoardService;
import com.kh.study.board.model.vo.Board;
import com.kh.study.board.model.vo.BoardImg;
import com.kh.study.commons.Utils;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final ServletContext application;
	private final BoardService service;

	// 게시글 목록 페이지 조회
	@GetMapping("/list")
	public String selectList(Model model) {
		
		List<Board> list = service.selectList();
		model.addAttribute("list", list);
		
		return "border/list";
	}
	
	// 게시글 등록 페이지 조회 
	@GetMapping("/insert")
	public String enrollBoard() {
		
		return "border/insert";
	}
	
	// 게시글 등록기능 
	@PostMapping("/insert")
	public String insertBoard(Board board, @RequestParam(value = "images", required = false) List<MultipartFile> images,
			RedirectAttributes ra) {

		BoardImg bi = null;
		List<BoardImg> biArr = new ArrayList<>();

		// 1) 웹서버에 클라이언트가 전달한 file 저장(전달한 파일이 있다면)
		for (MultipartFile img : images) {
			// 전달된 파일이 null이 아니고, 빈 문자열이 아닐때
			if (img != null && !img.getOriginalFilename().equals("")) {
				String webPath = "/resources/images/board/";
				String serverFolderPath = application.getRealPath(webPath);

				// 디렉토리가 존재하지 않으면 생성하는 코드 추가
				File dir = new File(serverFolderPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				// 사용자가 등록한 첨부파일의 이름을 수정
				String changeName = Utils.saveFile(img, serverFolderPath);

				bi = new BoardImg();
				bi.setChangeName(changeName);
				bi.setOriginName(img.getOriginalFilename());
				bi.setUploadPath(serverFolderPath + changeName);
				biArr.add(bi); // 게시글 이미지 리스트로 담아두기
			}
		}


		// 2) 저장 완료시 파일이 저장된 경로를 BOARD_IMG에 등록후 테이블에 추가
		// -> 1) BOARD INSERT
		// -> 2) BOARD_IMG INSERT -> 클라이언트가 upfile에 데이터를 작성했을때만.

		int result = 0; 
		try {
			result = service.insertBoard(board, biArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String url = "";

		if (result > 0) {
			url = "redirect:/detail/"+board.getNo();
		} else {
			ra.addAttribute("alertMsg", "게시글 등록에 실패했습니다.");
			url = "common/errorPage";
		}

		return url;
	}
	
	// 게시글 상세페이지 조회
	@GetMapping("/detail/{no}")
	public String selectBoard(@PathVariable("no") int no, Model model) {

		Board board = service.selectBoard(no);
		model.addAttribute("board", board);
		return "border/detail";

	}
	
}
