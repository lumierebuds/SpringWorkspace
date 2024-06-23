package com.kh.study.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardImg {
	private int boardImgNo;
	private String uploadPath;
	private String originName;
	private String changeName;
	private int refNo;

}
