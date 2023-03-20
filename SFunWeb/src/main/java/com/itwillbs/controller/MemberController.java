package com.itwillbs.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itwillbs.domain.MemberDTO;
import com.itwillbs.service.MemberService;

@Controller
public class MemberController {
	
	// 부모 인터페이스 멤버변수선언 => 자동으로 객체생성
	@Inject
	private MemberService memberService;
	
	// 가상주소 http://localhost:8080/SFunWeb/member/insert
	@RequestMapping(value = "/member/insert", method = RequestMethod.GET)
	public String insert() {
//		주소줄 변경없이 이동
//		/WEB-INF/views/파일이름.jsp
//		/WEB-INF/views/member/join.jsp
		return "member/join";
	}
	
	@RequestMapping(value = "/member/insertPro", method = RequestMethod.POST)
	public String insertPro(MemberDTO memberDTO) {
		System.out.println("MemberController insertPro");
		// 회원가입 처리  부모인터페이스  MemberService,
		// 			  자식클래스 	 MemberServiceImpl	
		// 리턴할형 없음 insertMember(MemberDTO memberDTO) 메서드 정의
		// 메서드 호출
		memberService.insertMember(memberDTO);
		
		// 주소줄 변경없이 이동
		// /WEB-INF/views/파일이름.jsp
		// /WEB-INF/views/member/login.jsp
		return "redirect:/member/login";
	}
	
	// 가상주소 http://localhost:8080/SFunWeb/member/login
	@RequestMapping(value = "/member/login", method = RequestMethod.GET)
	public String login() {
		System.out.println("MemberController login()");
		
//		주소줄 변경없이 이동
//		/WEB-INF/views/파일이름.jsp
//		/WEB-INF/views/member/login.jsp
		return "member/login";
	}
	
	@RequestMapping(value = "/member/loginPro", method = RequestMethod.POST)
	public String loginPro( HttpSession session,MemberDTO memberDTO) {
		System.out.println("MemberController loginPro");
		// 리턴할형 MemberDTO userCheck(MemberDTO memberDTO) 메서드 정의
		// 메서드 호출
		MemberDTO memberDTO2 = memberService.userCheck(memberDTO);
		if(memberDTO2 != null) {
			// 아이디 비밀번호 일치 => 세션값 생성 "id",값
			System.out.println("아이디 비밀번호 일치");
			// 
			session.setAttribute("id", memberDTO.getId());
			
			return "redirect:/main/main";
		} else {
			// 아이디 비밀번호 틀림 => 뒤로이동 member/msg
			return "member/msg";
		}
	}
}
