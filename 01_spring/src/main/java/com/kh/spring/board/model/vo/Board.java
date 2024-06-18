package com.kh.spring.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 매개변수 없는 기본 생성자 추가 : Mybatis에서 selectList를 해줄때 기본 생성자를 만들어서 사용해야한다.
@AllArgsConstructor
@Builder
public class Board {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriter; // id, userNo, name
	private int count;
	private Date createDate; // java.sql.Date
	private String status;
	private String boardCd;
}
