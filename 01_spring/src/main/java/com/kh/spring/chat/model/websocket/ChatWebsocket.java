package com.kh.spring.chat.model.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring.chat.movel.service.ChatService;
import com.kh.spring.chat.movel.vo.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // 스프링의 빈 객체로 등록하기 // bean id = "chatWebsocket"
@RequiredArgsConstructor
public class ChatWebsocket extends TextWebSocketHandler {

	private final ChatService chatService;
	/*
	 * WebSocket? 
	 * - 웹소캣은 하나의 TCP접속에 이중으로 통신채널을 만들어서 제공하는 HTTP기반 통신 프로토콜이다.
	 * - 실시간으로 제공해야하는 서비스를 만들때 사용되는 클래스이다.
	 *   EX) 실시간 채팅, 주식거래사이트, 실시간 알람
	 * 
	 * 
	 * 
	 * TextWebSocketHandler
	 * - 웹소캣을 위한 메소드를 지원하는 핸들러
	 * - 문자열과 관련된 기능을 다룰때 사용한다. 
	 * 
	 */


	// synchronizedSet : 동기화처리된 set 반환해주는 메소드. 
	// 멀티스레드환경에서 하나의 공유자원에 여러스레드가 동시에 접근하면 경쟁상태에 들어가고, 문제가 발생할 수 있으므로 동기화처리가 필수.
	private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<WebSocketSession>()); // 동기화 처리가 완료된 set 객체 

	@Override
	// 클라이언트와 서버간에 웹소캣 연결이 완료된 이후, 즉, 통신할 준비가 되었을때 실행되는 함수
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 매개변수로 전달받은 WebSocketSession은 클라이언트와 연결된 세션
		log.info("session ?? {} ", session.getId());
		sessions.add(session);
		
		super.afterConnectionEstablished(session);
	}

	@Override
	// 클라이언트가 메세지를 보냈을때 실행되는 함수
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 업무로직

		// 1) 전달받은 메시지를 db에 Insert
		// 2) 서버에서 세션에 접속중인 사용자들에게 메시지 전달
		// - 같은 채팅방에 참여중인 사용자에게만 전달
		log.debug("session ? {}", session.getId());
		log.debug("message ? {}", message); // payload : 전송되는 데이터가 담겨있는 필드

		// Jack라이브러리 : JSON형태로 넘어온 데이터를 VO 클래스로 자동 매핑 <-> VO클래스를 JSON형태로 자동 매핑
		ObjectMapper objectMapper = new ObjectMapper();
		//                        파싱할 문자열, 파싱할 vo클래스 -> 반환은 해당 vo클래스 타입 
		ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

		// 전달받은 메시지를 db에서 추가
		int result = chatService.insertMessage(chatMessage);

		// 메시지를 db에 성공적으로 저장했다면, 전달받은 메세지를
		// "같은방"에 "접속"중인 클라이언트들에게 전달

		if (result > 0) {
			for (WebSocketSession s : sessions) {
				// 현재 반복중인 session안에 담겨있는 방번호 빼오기
				int chatRoomNo = (int) s.getAttributes().get("chatRoomNo");

				// 현재 세션의 채팅방 번호와 전달받은 메세지안의 채팅방번호를 비교하여 일치하는 세션에만
				// 메세지 전달
				if (chatRoomNo == chatMessage.getChatRoomNo()) {
															// VO -> JSON
					s.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
				}
			}
		}

	}

	@Override
	// 클라이언트와의 웹소캣 연결이 종료되면 실행되는 함수
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}
}

