package com.itwillbs.service;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.itwillbs.dao.BoardDAO;
import com.itwillbs.domain.BoardDTO;
import com.itwillbs.domain.PageDTO;

@Service
public class BoardServiceImpl implements BoardService{
	
	// 객체생성 부모 인터페이스 => 자동으로 자식 클래스 객체생성
	@Inject
	private BoardDAO boardDAO;

	@Override
	public void insertBoard(BoardDTO boardDTO) {
		System.out.println("BoardServiceImpl insertBoard()");
		// 폼에서 가져온 값 name subject content
		// num,readcount,date,file => 설정
		// num => 구하기 max(num)+1
		if(boardDAO.getMaxNum() == null) {
			//게시판 글 없음 => 1 설정
			boardDTO.setNum(1);
		}else {
			//게시판 글 있음 => max(num)+1
			boardDTO.setNum(boardDAO.getMaxNum()+1);
		}
		
		boardDTO.setReadcount(0);
		boardDTO.setDate(new Timestamp(System.currentTimeMillis()));
		
		boardDAO.insertBoard(boardDTO);
	}

	@Override
	public List<BoardDTO> getBoardList(PageDTO pageDTO) {
		System.out.println("BoardServiceImpl getBoardList()");
		//시작하는 행번호 구하기
	int startRow=(pageDTO.getCurrentPage()-1)*pageDTO.getPageSize()+1;
	int endRow = startRow+pageDTO.getPageSize()-1;
		
		pageDTO.setStartRow(startRow);
		pageDTO.setEndRow(endRow);
		
		return boardDAO.getBoardList(pageDTO);
	}

	@Override
	public int getBoardCount() {
		System.out.println("BoardServiceImpl getBoardCount()");
		
		return boardDAO.getBoardCount();
	}

	@Override
	public BoardDTO getBoard(int num) {
		System.out.println("BoardServiceImpl getBoard()");
		
		return boardDAO.getBoard(num);
	}

	@Override
	public void updateBoard(BoardDTO boardDTO) {
		System.out.println("BoardServiceImpl updateBoard()");
		
		boardDAO.updateBoard(boardDTO);
	}

	@Override
	public void deleteBoard(int num) {
		System.out.println("BoardServiceImpl deleteBoard()");
		
		boardDAO.deleteBoard(num);
	}

	@Override
	public void fupdateBoard(BoardDTO boardDTO) {
		System.out.println("BoardServiceImpl fupdateBoard()");
	
		boardDAO.fupdateBoard(boardDTO);
	}

}
