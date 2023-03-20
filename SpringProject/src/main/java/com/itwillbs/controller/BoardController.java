package com.itwillbs.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.domain.BoardDTO;
import com.itwillbs.domain.PageDTO;
import com.itwillbs.service.BoardService;

@Controller
public class BoardController {
	
	//멤버변수 부모 인터페이스 정의 => 자동으로 자식 클래스 객체생성
	// 스프링 객체생성 방식 => 의존관계주입(DI : Dependency Injection)
	@Inject
	private BoardService boardService;
	
	//xml 업로드 경로이름("uploadPath") 자동으로 불러오기
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public String write() {
		
		// 주소변경 없이 이동
		// /WEB-INF/views/board/writeForm.jsp
		return "board/writeForm";
	}
	
	@RequestMapping(value = "/board/writePro", method = RequestMethod.POST)
	public String writePro(BoardDTO boardDTO) {
		System.out.println("BoardController writePro()");
		//글쓰기 작업 메서드 호출
		boardService.insertBoard(boardDTO);
		// 주소변경 하면서 이동
		return "redirect:/board/list";
	}
	
	//  http://localhost:8080/myweb/board/list
	//  http://localhost:8080/myweb/board/list?pageNum=2
	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) {
		System.out.println("BoardController list()");
		// 한 화면에 보여줄 글 개수 설정
		int pageSize=10;
		// 현페이지 번호 가져오기
		String pageNum=request.getParameter("pageNum");
		if(pageNum==null) {
			//pageNum 없으면 1페이지 설정
			pageNum="1";
		}
		// 페이지번호를 => 정수형 변경
		int currentPage=Integer.parseInt(pageNum);
		
		PageDTO pageDTO=new PageDTO();
		pageDTO.setPageSize(pageSize);
		pageDTO.setPageNum(pageNum);
		pageDTO.setCurrentPage(currentPage);
		
		List<BoardDTO> boardList=boardService.getBoardList(pageDTO);
		
		//페이징 처리
		int count = boardService.getBoardCount();
		int pageBlock=10;
		int startPage=(currentPage-1)/pageBlock*pageBlock+1;
		int endPage=startPage+pageBlock-1;
		int pageCount=count/pageSize+(count%pageSize==0?0:1);
		if(endPage > pageCount){
			endPage = pageCount;
		}
		
		pageDTO.setCount(count);
		pageDTO.setPageBlock(pageBlock);
		pageDTO.setStartPage(startPage);
		pageDTO.setEndPage(endPage);
		pageDTO.setPageCount(pageCount);
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageDTO", pageDTO);
		
		// 주소변경 없이 이동
		// /WEB-INF/views/board/list.jsp
		return "board/list";
	}
	
	@RequestMapping(value = "/board/content", method = RequestMethod.GET)
	public String content(HttpServletRequest request, Model model) {
		int num=Integer.parseInt(request.getParameter("num"));
		
		BoardDTO boardDTO=boardService.getBoard(num);
		
		model.addAttribute("boardDTO", boardDTO);
		
		// 주소변경 없이 이동
		// /WEB-INF/views/board/content.jsp
		return "board/content";
	}
	
	@RequestMapping(value = "/board/update", method = RequestMethod.GET)
	public String update(HttpServletRequest request, Model model) {
		int num=Integer.parseInt(request.getParameter("num"));
		
		BoardDTO boardDTO=boardService.getBoard(num);
		
		model.addAttribute("boardDTO", boardDTO);
		
		// 주소변경 없이 이동
		// /WEB-INF/views/board/updateForm.jsp
		return "board/updateForm";
	}
	
	@RequestMapping(value = "/board/updatePro", method = RequestMethod.POST)
	public String updatePro(BoardDTO boardDTO) {
		
		boardService.updateBoard(boardDTO);
		
		// 주소변경 하면서 이동
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/board/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request) {
		int num=Integer.parseInt(request.getParameter("num"));
		
		boardService.deleteBoard(num);
		
		// 주소변경 하면서 이동
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/board/fwrite", method = RequestMethod.GET)
	public String fwrite() {
		
		// 주소변경 없이 이동
		// /WEB-INF/views/board/fwriteForm.jsp
		return "board/fwriteForm";
	}
	
	@RequestMapping(value = "/board/fwritePro", method = RequestMethod.POST)
	public String fwritePro(HttpServletRequest request, MultipartFile file) throws Exception{
		//  throws Exception => 예외처리를 메서드 호출한 곳에서 함
		// <input type="file" name="file" > 
		//  => 이름일치 MultipartFile file
		System.out.println("BoardController fwritePro()");
		// 첨부파일 있으면 (enctype="multipart/form-data")
		// BoardDTO 자동으로 저장 안됨
		BoardDTO boardDTO=new BoardDTO();
		boardDTO.setName(request.getParameter("name"));
		boardDTO.setSubject(request.getParameter("subject"));
		boardDTO.setContent(request.getParameter("content"));
		// 파일업로드 프로그램설치 =>pom.xml
		//첨부파일 
		// 파일이름 => 랜덤파일이름 => 랜덤문자_파일이름 => 디비저장
		UUID uuid=UUID.randomUUID();
		String filename=uuid.toString()+"_"+file.getOriginalFilename();
		// 원본파일 복사 => 파일위치 / 랜덤문자_파일이름 붙여넣기
//		FileCopyUtils.copy(원본파일, 복사위치/파일이름);
		// 외부에 있는 파일 처리 => 예외처리
		FileCopyUtils.copy(file.getBytes(), new File(uploadPath,filename));
		
		// boardDTO file이름 저장
		boardDTO.setFile(filename);
		
		//글쓰기 작업 메서드 호출
		boardService.insertBoard(boardDTO);
		// 주소변경 하면서 이동
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/board/fupdate", method = RequestMethod.GET)
	public String fupdate(HttpServletRequest request, Model model) {
		int num=Integer.parseInt(request.getParameter("num"));
		
		BoardDTO boardDTO=boardService.getBoard(num);
		
		model.addAttribute("boardDTO", boardDTO);
		
		// 주소변경 없이 이동
		// /WEB-INF/views/board/fupdateForm.jsp
		return "board/fupdateForm";
	}
	
	@RequestMapping(value = "/board/fupdatePro", method = RequestMethod.POST)
	public String fupdatePro(HttpServletRequest request, MultipartFile file) throws Exception{
		//  throws Exception => 예외처리를 메서드 호출한 곳에서 함
		// <input type="file" name="file" > 
		//  => 이름일치 MultipartFile file
		System.out.println("BoardController fupdatePro()");
		// 첨부파일 있으면 (enctype="multipart/form-data")
		// BoardDTO 자동으로 저장 안됨
		BoardDTO boardDTO=new BoardDTO();
		boardDTO.setNum(Integer.parseInt(request.getParameter("num")));
		boardDTO.setName(request.getParameter("name"));
		boardDTO.setSubject(request.getParameter("subject"));
		boardDTO.setContent(request.getParameter("content"));
		// 파일업로드 프로그램설치 =>pom.xml
		if(file.isEmpty()) {
//			첨부파일 없는 경우
			boardDTO.setFile(request.getParameter("oldfile"));
		}else {
			//첨부파일 있는 경우
			// 파일이름 => 랜덤파일이름 => 랜덤문자_파일이름 => 디비저장
			UUID uuid=UUID.randomUUID();
			String filename=uuid.toString()+"_"+file.getOriginalFilename();
			// 원본파일 복사 => 파일위치 / 랜덤문자_파일이름 붙여넣기
//			FileCopyUtils.copy(원본파일, 복사위치/파일이름);
			// 외부에 있는 파일 처리 => 예외처리
			FileCopyUtils.copy(file.getBytes(), new File(uploadPath,filename));
			
			// boardDTO file이름 저장
			boardDTO.setFile(filename);
		}
		
		
		//글쓰기 작업 메서드 호출 => subject,content,file 수정
		//                   => num 조건 
		boardService.fupdateBoard(boardDTO);
		// 주소변경 하면서 이동
		return "redirect:/board/list";
	}
	

}
