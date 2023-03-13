package com.itwillbs.dao;

import java.sql.Timestamp;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.itwillbs.domain.MemberDTO;

// @Service 자동으로 자식클래스 찾도록 함.(꼭 붙여야한다)

//@Inject
//private MemberDAO memberDAO; (부모인터페이스 변수)
// -> 자동으로 @Repository 정의된 자식클래스 찾아서 객체생성 

@Repository
public class MemberDAOImpl implements MemberDAO {

	// Mybatis 디비연결 객체생성
	// 데이터 은닉 멤버변수 ->root-context 의 bean id = sqlSession 을 자동으로 찾게끔 어노테이션 inject
	// @Inject => root-context.xml 파일에 객체생성된 "sqlSession" 찾아서 자동으로 가져옴.

	@Inject
	private SqlSession sqlSession;

	// 디비연결 객체생성

	// 은닉 멤버변수
	// private DataSource dataSource;
	// ↑스프링에서 제공하는 db연동 프로그램을 사용할 거다.(나중에는 안쓸건데 알아두는것)
	// private SimpleJdbcTemplate template;

	// set메서드
//	@Inject
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//		template = new SimpleJdbcTemplate(dataSource); // 스프링에서는 SimpleJdbcTemplate(dataSource)를 쓴다.
//	}

	private static final String namespace = "com.itwillbs.mappers.memberMapper";

	// ↑ 여기에서만 쓰고, 변수명을 수정하지못하게 끔 private static final 설정.
//	String sql = "insert into members(id,pass,name,date) values(?,?,?,?)";
	@Override
	public void insertMember(MemberDTO memberDTO) {
		// 디비작업
		System.out.println("MemberDAOImpl insertMember()");

		// 날짜는 처리작업에서 하지만 우선은 디비작업에서 한다.
		// import java.sql.Timestamp;
		memberDTO.setDate(new Timestamp(System.currentTimeMillis()));

		// MyBatis sql구문 호출해서 사용.(src/main/resousces - mappers -memberMapper.xml)
		// 파일이름말고 namespace이름을 불러서 사용한다.
		// namespace="com.itwillbs.mappers.memberMapper 이름을 불러서 사용
		// MyBatis sql구문 호출해서 사용
		// sqlSession.insert(sql구문이름, ?에 입력될 값);
		sqlSession.insert(namespace + ".insertMember", memberDTO);
		// "com.itwillbs.mappers.memberMapper" -> 반복되니까 변수명으로 따로 떼서 작업.
		// memberDTO를 적으면 멤버dto에 들어있는 멤버변수가 자동으로 인식된다.
		// sqlSession.insert(변수명+ ".쿼리이름",변수값); "."은 변수명

//		//template.update(sql구문, 입력될 값);	// insert작업.템플릿을 받아와서 함 (== execute.update())
//		template.update(sql,memberDTO.getId(),memberDTO.getPass(),memberDTO.getName(),memberDTO.getDate());

	}

	@Override
	public MemberDTO userCheck(MemberDTO memberDTO) {
		System.out.println("MemberDAOImpl userCheck()");

		// selectOne = 리턴값이 MemberDTO 하나일 때 사용
		return sqlSession.selectOne(namespace + ".userCheck", memberDTO);
		// (이름+".전체이름",변수);
		
	}
	
	@Override
	public MemberDTO getMember(String id) {
		// 디비작업
		System.out.println("MemberDAOImpl getMember()");

		// MyBatis sql구문 호출해서 사용
		// selectOne = 리턴값이 MemberDTO 하나일 때 사용
		return sqlSession.selectOne(namespace + ".getMember", id);
	}
	
	@Override
	public void updateMember(MemberDTO memberDTO) {
		// 디비작업
		System.out.println("MemberDAOImpl updateMember()");
		
		// MyBatis sql구문 호출해서 사용
		// sqlSession.update(sql구문이름, ?에 입력될 값);
		sqlSession.update(namespace+".updateMember", memberDTO);
		
	}

	@Override
	public void deleteMember(MemberDTO memberDTO) {
		// 디비작업
		System.out.println("MemberDAOImpl deleteMember()");
		
		// MyBatis sql구문 호출해서 사용
		// sqlSession.update(sql구문이름, ?에 입력될 값);
		sqlSession.update(namespace+".deleteMember", memberDTO);
		
	}
	
	
}
