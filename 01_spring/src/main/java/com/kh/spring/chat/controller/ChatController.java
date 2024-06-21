package com.kh.spring.chat.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.chat.movel.service.ChatService;
import com.kh.spring.chat.movel.vo.ChatMessage;
import com.kh.spring.chat.movel.vo.ChatRoom;
import com.kh.spring.chat.movel.vo.ChatRoomJoin;
import com.kh.spring.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/chat")
@SessionAttributes({ "loginUser", "chatRoomNo" })
@RequiredArgsConstructor
public class ChatController {
	
	private final ChatService chatService;
	
	@GetMapping("/chatRoomList")
	public String selectChatRoomList(Model model) {

		// 업무로직
		List<ChatRoom> list = chatService.selectChatRoomList();

		model.addAttribute("list", list);

		return "chat/chatRoomList";
	}

	@PostMapping("/openChatRoom")
	public String openChatRoom(
			ChatRoom room,
			RedirectAttributes ra,
			@ModelAttribute("loginUser") Member loginUser) {
		log.debug("ChatRoom {}", room);
		log.debug(" loginUser {}", loginUser);

		room.setUserNo(loginUser.getUserNo());

		int result = chatService.openChatRoom(room);

		String url = "redirect:/chat/";
		if (result > 0) {
			ra.addFlashAttribute("alertMsg", "채팅방 생성 성공. 채팅방으로 입장합니다.");
			url += "room/" + room.getChatRoomNo(); // chat/room/3
			// selectKey 기능으로 생성된 채팅방 번호를 반환
		} else {
			ra.addFlashAttribute("alertMsg", "채팅방 생성 실패");
			url += "chatRoomList";
		}
		
		return url;
	}
	
	@GetMapping("/room/{chatRoomNo}")
	public String joinChatRoom(
			@PathVariable("chatRoomNo") int chatRoomNo,
			Model model,
			RedirectAttributes ra, @ModelAttribute("loginUser") Member loginUser, ChatRoomJoin chatRoomJoin
			) {
		// 채팅방에 참여(CHAT_ROOM_JOIN에 삽입)
		// 채팅방에 내용 조회(CHAT_MESSAGE)
		
		log.debug("charRoomJoin : {}", chatRoomJoin); // chatRoomNo = 1, userNo = 0
		chatRoomJoin.setUserNo(loginUser.getUserNo()); // chatRoomNo = 1, userNo = "로그인한 사용자 userNo"

		List<ChatMessage> list = chatService.joinChatRoom(chatRoomJoin);

		if (list != null) {
			model.addAttribute("list", list);
			model.addAttribute("chatRoomNo", chatRoomNo);
			return "chat/chatRoom";
		} else {
			ra.addFlashAttribute("alertMsg", "채팅방에 입장할 수 없습니다.");
			return "redirect:/chat/chatRoomList";
		}

	}

	@GetMapping("/chatRoom/{chatRoomNo}/exit")
	public String exitChatRoom(@PathVariable("chatRoomNo") int chatRoomNo, 
			@ModelAttribute("loginUser") Member loginUser,
			ChatRoomJoin join, // ChatRoomJoin 객체에 PathVariable로 정의된 변수도 매핑된다.
			RedirectAttributes ra
			) {
		// 업무로직
		// 1) chatRoomNo로 DELETE 실행 - (ChatRoomJoin)
		join.setUserNo(loginUser.getUserNo()); // 로그인된 사용자의 번호를 설정(매개변수에서 정의안됨)
		int result = chatService.exitChatRoom(join);
		
		// 2) 현재 채팅방에 참여하고있는 인원정보 확인 SELECT
		// 3) 내가 마지막 인원이라면 채팅방 정보를 DELETE - (ChatRoom)
		
		// 응답 화면 
		// chatRoomList로 redirect 
		ra.addFlashAttribute("alertMsg", "채팅방을 나갔습니다.");
		return "redirect:/chat/chatRoomList";
		
		
	}
}
