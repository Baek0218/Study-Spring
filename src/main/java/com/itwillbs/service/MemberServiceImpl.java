package com.itwillbs.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.itwillbs.dao.MemberDAO;
import com.itwillbs.dao.MemberDAOImpl;
import com.itwillbs.domain.MemberDTO;
//@Service 자동으로 자식클래스 찾도록 함.
//@Inject
//private MemberService memberService;  이게 객체생성 한 것.

@Service // 꼭 붙여줘야한다!!
public class MemberServiceImpl implements MemberService {
	// 처리작업
	// 부모 인터페이스 틀 상속

	// 멤버변수 부모=자식 객체생성
	// MemberDAO 부모 = MemberDAOImpl 자식 객체생성
//	MemberDAO memberDAO=new MemberDAOImpl();

	// set메서드 멤버변수-데이터은닉-객체생성
	@Inject
	private MemberDAO memberDAO;

	// 멤버변수 값을 전달 생성자, set메서드 통해서 전달
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	@Override
	public void insertMember(MemberDTO memberDTO) {
		System.out.println("MemberServiceImpl insertMember()");
		// MemberDAO 부모 = MemberDAOImpl 자식 객체생성
//		MemberDAO memberDAO=new MemberDAOImpl();

		// 메서드 호출
		memberDAO.insertMember(memberDTO);
	}

	@Override
	public MemberDTO userCheck(MemberDTO memberDTO) {
		System.out.println("MemberServiceImpl userCheck()");
		// MemberDAO 부모 = MemberDAOImpl 자식 객체생성
//		MemberDAO memberDAO=new MemberDAOImpl();

		// 리턴할형 MemberDTO userCheck(MemberDTO memberDTO ) 메서드 정의
		// MemberDTO memberDTO2 = userCheck(memberDTO) 메서드 호출
		// MemberDTO memberDTO2 =memberDAO.userCheck(memberDTO);

		return memberDAO.userCheck(memberDTO);
		// return memberDAO.userCheck(memberDTO);

	}

	@Override
	// info에서 id값으로 member정보 조회하기
	public MemberDTO getMember(String id) {
		System.out.println("MemberServiceImpl getMember(String id)");

		return memberDAO.getMember(id);
	}

	@Override
	public void updateMember(MemberDTO memberDTO) {
		System.out.println("MemberServiceImpl updateMember(MemberDTO memberDTO)");

		// 메서드 호출
		memberDAO.updateMember(memberDTO);
	}

	@Override
	public void deleteMember(MemberDTO memberDTO) {
		System.out.println("MemberServiceImpl deleteMember(MemberDTO memberDTO)");

		// 메서드 호출
		memberDAO.deleteMember(memberDTO);
	}

}
