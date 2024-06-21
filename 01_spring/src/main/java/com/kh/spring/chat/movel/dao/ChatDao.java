package com.kh.spring.chat.movel.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.spring.chat.movel.vo.ChatMessage;
import com.kh.spring.chat.movel.vo.ChatRoom;
import com.kh.spring.chat.movel.vo.ChatRoomJoin;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatDao {

	private final SqlSession sqlSession;

	public List<ChatRoom> selectChatRoomList() {
		return sqlSession.selectList("chat.selectChatRoomList");
	}

	public int openChatRoom(ChatRoom room) {
		return sqlSession.insert("chat.openChatRoom", room);
	}

	public int joinChatRoom(ChatRoomJoin join) { 
		int result = 1;
		try { // 에러코드를 감추기 위한 코드
			result = sqlSession.insert("chat.joinChatRoom", join);
		} catch (Exception e) {
		}
		return result;
	}

	public List<ChatMessage> selectChatMessage(int chatRoomNo) {

		return sqlSession.selectList("chat.selectChatMessage", chatRoomNo);
	}

	public int insertMessage(ChatMessage chatMessage) {

		return sqlSession.insert("chat.insertMessage", chatMessage);
	}

	public int exitChatRoom(ChatRoomJoin join) {

		return sqlSession.delete("chat.exitChatRoom", join);
	}

	public int countChatRoomMember(int chatRoomNo) {

		return sqlSession.selectOne("chat.countChatRoomMember", chatRoomNo);
	}

	public int closeChatRoom(int chatRoomNo) {

		return sqlSession.update("chat.closeChatRoom", chatRoomNo);
	}

}
