package com.kh.study.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int no;
	private String title;
	private String subTitle;
	private String content;
	private String createDate; // TO_CHAR로 바껴서 매핑될것
}
