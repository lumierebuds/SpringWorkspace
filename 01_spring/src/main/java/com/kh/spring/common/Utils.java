package com.kh.spring.common;

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
}
