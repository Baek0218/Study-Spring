package com.itwillbs.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.MemberDTO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	// 마이바티스 멤버변수 선언 -> 자동객체생성 
	@Inject
	private SqlSession sqlSession;
	
	// sql구문의 전체이름 
	private static final String namespace="com.itwillbs.mappers.memberMapper";
	
	@Override
	public void insertMember(MemberDTO memberDTO) {
		System.out.println("MemberDAOImpl insertMember()");
		// 메서드 호출
//		sqlSession.insert(sql구문이름, memberDTO)
		sqlSession.insert(namespace+".insertMember", memberDTO);
	}

}
