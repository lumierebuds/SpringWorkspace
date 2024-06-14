package com.kh.spring.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller // 빈스캐너에의해 빈객체로 등록.
@RequestMapping("/member") // "/member" 로 요청이 들어올때에 해당 요청을 처리 - 프론트 컨트롤러(공통주소를 처리)
@SessionAttributes({ "loginUser", "ddd", "hhh" })
// Model에 추가된 값의 key값과 일치하는 값이 나도오록 설정 - 모델의 키값중 일치하면 "세션"으로 이동/ 불일치하면 "리퀘스트"로 이동
public class MemberController {

	/*
	 * 기존 객체 생성 방식
	 * 
	 * private MemberService mService = new MemberServiceImpl();
	 * 
	 * Spring의 DI(Dependency Injection) : 객체를 개발자가 생성하는게 아니라, 스프링이 생성한 객체를 주입받아서
	 * 사용하는 방식
	 * 
	 * @Autowired
	 */

	// @Autowired // 스프링 컨테이너가 생성한 memberService가 들어간다.(필드방식 의존성 주입. 권장방식은 X)
	private MemberService mService; // new MemberServiceImpl();
	private BCryptPasswordEncoder encoder;

	// 생성자 주입
	@Autowired // 생략 가능하다.
	public MemberController(MemberService mService, BCryptPasswordEncoder encoder) {
		this.mService = mService;
		this.encoder = encoder;
	}
	
	/*
	 * @RequestMapping ? 
	 *  - 클라이언트 요청 url에 맞는 클래스/메서드를 연결해주는 어노테이션
	 *    해당 어노테이션이 붙은 클래스/메서드는 HandlerMapping으로 등록된다. 
	 *    * HandlerMapping? 사용자가 지정한 url 정보들을 보관하는 저장소
	 * 
	 */
	
	// 스프링에서 클라이언트의 요청값을 뽑아내는 방법
	// 1. HttpServletRequest로 뽑아내는 방법

	// @RequestMapping(value = "/login", method = RequestMethod.POST)
	// public String login(HttpServletRequest request) {
	// System.out.println("userId : " + request.getParameter("userId"));
	// return "main";
	// }

	// 2. @RequestParam 어노테이션 사용
	// 넘어온 값이 없다면 기본값 설정이 가능
	// @RequestMapping(value="/login", method=RequestMethod.POST)
	// public String login(
	// @RequestParam(value = "userId", defaultValue = "mmm") String userId,
	// @RequestParam(value="userPwd") String userPwd
	// ) {
	// System.out.println("userId : "+ userId);
	// System.out.println("userPwd : "+ userPwd);
	// return "main";
	// }

	// 3. @RequestParam 제거 가능
	// @RequestMapping(value="/login", method=RequestMethod.POST)
	// public String login(
	// String userId,
	// String userPwd
	// ) {
	// System.out.println("userId : "+ userId);
	// System.out.println("userPwd : "+ userPwd);
	// return "main";
	// }

	// 4. 커맨드 객체 방식
	/*
	 * 1) 스프링 컨테이너에서 매개변수로 지정한 VO 클래스의 기본생성자를 호출하여 객체를 생성. 
	 * 2) request로 전달받은 파라미터의 key값과 일치하는 필드의 setter 메서드를 찾아서 호출
	 * 
	 */

	// @RequestMapping(value = "/login", method = RequestMethod.POST)
	// public ModelAndView login(@ModelAttribute Member m,
	// HttpSession session,
	// Model model,
	// ModelAndView mv) {
	// System.out.println("userId : " + m.getUserId());
	// System.out.println("userPwd : " + m.getUserPwd());
	// // 비지니스 로직 끝
	//
	// /*
	// * 로그인 요청처리 완료후 "응답데이터를" 담아서 응답페이지로 url 재요청을 할때
	// *
	// * 1) Model 객체를 이용
	// * 포워딩할 응답뷰페이지로 전달하고자 하는 데이터를 맵형식으로 담아줌.
	// * -> request, session 스코프 두개 가지고 있음.
	// * -> 기본 scope : request.
	// * session scope로 저장하고자 한다면 클래스 위쪽에 @SessionAttribute을 추가해야한다.
	// *
	// * 2) ModelAndView 객체 이용
	// * ModelAndView에서 model은 데이터를 key-value 형태로 담을 수 있는 Model 객체와 동일.
	// * View는 응답뷰에 대한 정보를 담을 수 있다.
	// *
	// *
	// */
	//
	// model.addAttribute("errorMsg", "오류발생"); // request.setAttribute("errorMsg",
	// "오류발생");
	//
	// mv.addObject("errorMsg", "로그인 실패");
	// mv.setViewName("common/errorPage");
	//
	// return mv;
	// }

	@PostMapping("/login") // 보통 @RequestMapping은 공통 경로를쓸때 PostMapping (Post는 JDBC의 애노테이션)
	public String login(Member m, Model model, // 응답데이터를 담아줄 객체(로그인한 회원정보, 로그인성공/실패 메세지)
			RedirectAttributes ra) {

		// 암호화전 로그인 요청처리 작업
		// 업무로직

		// Member loginUser = mService.login(m);
		// String viewName = "";
		// if (loginUser == null) {
		// model.addAttribute("errorMsg", "로그인 실패!"); // SessionAttributes와 일치하는 값이 없으므로
		// request이관
		// viewName = "common/errorPage";
		// } else {
		// model.addAttribute("loginUser", loginUser);
		// viewName = "redirect:/"; // 현재 어플리케이션 경로 "/spring"으로 리다이렉트 - 애플리케이션 경로에 해당
		// }
		//
		// return viewName;

		// 암호화후 로그인 업무로직
		Member loginUser = mService.login(m); // 아이디 기준으로 사용자 정보 조회
		// loginUser의 비밀번호에는 암호화된 비밀번호가 담겨있음
		// m에는 암호화되지 않은 평문형태의 비밀번호가 담겨있음 
		// (필터에서 수행하는 작업을 컨트롤러에서 진행) 
		
		// matches(평문형태의 비밀번호, 암호화된 비밀번호) : 내부적으로 두값이 일치하는지 검사를 한후 true/false 반환
		// (DB에는 암호화 처리된 패스워드가 있음)
		

		String viewName = "";
		if (!(loginUser != null && encoder.matches(m.getUserPwd(), loginUser.getUserPwd()))) {
			model.addAttribute("errorMsg", "로그인 실패!"); // SessionAttributes와 일치하는 값이 없으므로 request이관
			viewName = "common/errorPage";
		} else {
			model.addAttribute("loginUser", loginUser); // session scope로 이관
			viewName = "redirect:/"; // 현재 어플리케이션 경로 "/spring"으로 리다이렉트 - 애플리케이션 경로에 해당
		}

		return viewName;
	}

}
