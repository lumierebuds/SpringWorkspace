package com.kh.spring.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageInfo {

	private int listCount; // 게시글 갯수
	private int currentPage; // 요청 페이지
	private int pageLimit; // 페이징바 갯수
	private int boardLimit; // 게시글 갯수(한페이지 내부)

	private int maxPage; // 최대 페이지 갯수
	private int startPage; // 시작페이지
	private int endPage; // 끝페이지
}
