package com.kh.spring.chat.movel.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

	private int cmNo;
	private String message;
	private String createDate;
	private int chatRoomNo;
	private int userNo;

	private String userName;
}
