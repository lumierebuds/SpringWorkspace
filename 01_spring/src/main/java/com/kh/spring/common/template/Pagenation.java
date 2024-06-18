package com.kh.spring.common.template;

import com.kh.spring.common.model.vo.PageInfo;

public class Pagenation {

	public static PageInfo getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit) {
		int maxPage = (int) (Math.ceil(((double) listCount / boardLimit))); // 가장 마지막 페이지
		/* 
		 * listCount와 boardLimit에 영향을 받음
		 * 
		 * 공식구하기
		 * boardLimit값은 10이라는 가정하에 규칙을 세울예정.
		 * 총 갯수 		  boardLimit 			maxPage
		 * 100 		          10                  10번
		 * 101                10                  11번 
		 * 110                10                  11번 
		 * 111                10                  12번
		 * 
		 * 111.0 / 10.0 -> 11.1 -> 올림처리 -> 12.0 -> 소수점빼면 -> 12
		 * */
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1; // 시작 페이지(페이징바)
		/*
		 * currentPage와 pageLimit에 영향을 받음 
		 * 
		 * - 공식구하기 단, pageLimit은 10이라는 가정으로 규칙찾기 
		 * 
		 * startPage : 1, 11, 21, 31 ... -> n * 10 +1
		 * 10은 pageLimit값이므로 n * pageLimit + 1 
		 * 
		 * currentPage 		startPage
		 *      1 				1  -> n * pageLimit + 1 => n = 0
		 *     10				1  -> n * pageLimit + 1 => n = 0
		 * 	   11              11  -> n * pageLimit + 1 => n = 1
		 *     20              11  -> n * pageLimit + 1 => n = 1
		 *     
		 *     1~10 -> n=0
		 *     11~20 -> n=1
		 *     21~30 -> n=2
		 *     		  n = (currentPage - 1) / pageLimit 연산후 소수점 제거시
		 *     
		 *     (currentPage - 1) / pageLimit * pageLimit + 1 
		 * */
		
		
		int endPage = startPage + pageLimit - 1; // 마지막 페이지(페이징바)

		if (endPage > maxPage) {
			endPage = maxPage;
		}

		PageInfo pageInfo = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		return pageInfo;
	}

}
