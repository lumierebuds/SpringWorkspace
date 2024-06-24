package com.kh.spring.common.scheduling;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kh.spring.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileDeleteScheduler {
	// 정적 파일들의 경로를 얻어오기 위한 변수
	private final ServletContext application;
	private final BoardService boardService;
	
	
	@Scheduled(cron = "1 * * * * *") // 매분 1초 간격으로 실행
	public void deleteFile() {
		log.debug("파일삭제 스케줄러 시작");
		// 1) BOARD_IMG 안에 있는 모든 파일목록 조회
		List<String> list = boardService.selectFileList();
		log.debug("list {}", list); // /resources/images.../xxx.jpg

		File path = new File(application.getRealPath("/resources/images/board/N/"));
		File[] files = path.listFiles(); // 현재 디렉토리 안에 존재하는 모든 파일을 배열 형태로 반환해주는 메소드
		List<File> fileList = Arrays.asList(files); // 배열이 컬렉션으로 변환됨

		if (!list.isEmpty()) {
			for (File serverFile : fileList) {
				String fileName = serverFile.getName(); // 파일이름반환
				fileName = "/resources/images/board/N/" + fileName; // 수정된 이름 앞에 경로를 추가

				// 실제 웹서버상에는 존재하나, db에는 존재하지 않는 파일인 경우
				if (list.indexOf(fileName) == -1) {
					
					log.debug(fileName + "을 삭제합니다.");
					serverFile.delete(); // 웹서버에서 파일 삭제
				}
			}
		}

		log.debug("파일삭제 스케줄러 끝");
	}
}
