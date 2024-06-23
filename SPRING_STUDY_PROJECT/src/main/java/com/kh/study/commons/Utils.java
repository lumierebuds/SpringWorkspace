package com.kh.study.commons;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

	// 파일 저장 함수
	// 파일을 저장시키면서 파일명을 함께 수정한 후, 수정된 파일명을 반환

	public static String saveFile(MultipartFile upfile, String path) {

		// 랜덤 파일명 생성하기
		String originName = upfile.getOriginalFilename();
		String currentTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		int random = (int) (Math.random() * 90000 + 10000); // 10000 ~ 99999(5자리 랜덤값)
		String ext = originName.substring(originName.indexOf(".")); // test.jpg -> .jpg

		String changeName = currentTime + random + ext; // 임의의 랜덤파일명생성
		System.out.println(path + changeName);
		try {
			upfile.transferTo(new File(path + changeName)); // 파일도 함께 저장한다.
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		return changeName;

	}

	// XSS 크로스사이트 스크립트 공격을 방지하기 위한 메소드
	public static String XSSHandling(String content) {
		if (content != null) {
			content = content.replaceAll("&", "&amp;");
			content = content.replaceAll("<", "&lt;");
			content = content.replaceAll(">", "&gt;");
			content = content.replaceAll("\"", "&quot;");
		}
		return content;
	}

	// 개행처리
	// textArea -> \n, p -> <br>
	// "데이터를 저장시"
	public static String newLineHandler(String content) {
		return content.replaceAll("(\r\n|\n|\r|\n\r)", "<br>"); // 정규표현식으로 해당 문자들을 <br> 태그로 변경
	}

	// 개행처리 해제
	public static String newLineClear(String content) {
		return content.replaceAll("<br>", "\n");
	}

}
