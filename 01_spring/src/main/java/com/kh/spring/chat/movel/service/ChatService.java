package com.kh.spring.chat.movel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.spring.chat.movel.dao.ChatDao;
import com.kh.spring.chat.movel.vo.ChatMessage;
import com.kh.spring.chat.movel.vo.ChatRoom;
import com.kh.spring.chat.movel.vo.ChatRoomJoin;
import com.kh.spring.common.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatDao chatDao;

	public List<ChatRoom> selectChatRoomList() {

		return chatDao.selectChatRoomList();
	}

	public int openChatRoom(ChatRoom room) {

		return chatDao.openChatRoom(room);
	}

	public List<ChatMessage> joinChatRoom(ChatRoomJoin join) {

		// 1. 채팅방에 참여
		int result = 1; // 0이 아닌 1로 설정
		List<ChatMessage> list = null;
		try {
			result = chatDao.joinChatRoom(join);
		} catch (Exception e) {

		}

		// 2. 채팅메시지 조회
		if (result > 0) {
			list = chatDao.selectChatMessage(join.getChatRoomNo());
		}

		return list;
	}

	public int insertMessage(ChatMessage chatMessage) {
		// XSS 핸들링
		// 개행처리

		chatMessage.setMessage(Utils.XSSHandling(chatMessage.getMessage()));
		chatMessage.setMessage(Utils.newLineHandler(chatMessage.getMessage()));

		return chatDao.insertMessage(chatMessage);
	}

}
