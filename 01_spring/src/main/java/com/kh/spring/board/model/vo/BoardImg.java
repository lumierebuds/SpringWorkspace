package com.kh.spring.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 매개변수 없는 기본 생성자 추가 : Mybatis에서 selectList를 해줄때 기본 생성자를 만들어서 사용해야한다.
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class BoardImg {

	private int boardImgNo;
	private String originName;
	private String changeName;
	private int refBno;
	private int imgLevel;

}
