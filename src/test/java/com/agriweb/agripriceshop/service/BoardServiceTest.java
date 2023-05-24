package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Board;
import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.BoardDto;
import com.agriweb.agripriceshop.dto.MemberDto;
import com.agriweb.agripriceshop.repository.BoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@WebAppConfiguration
@Transactional
public class BoardServiceTest {

    @Autowired BoardService boardService;
    @Autowired MemberService memberService;

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 게시글_작성() throws Exception {
        // given
        MemberDto dto = new MemberDto();
        dto.setUserName("kim");
        dto.setPw("1234");
        dto.setLoginId("kim1234");
        dto.setBirthdate(LocalDate.ofEpochDay(19850904));
        dto.setGender("남");
        dto.setEmail("kim1234@naver.com");
        dto.setTel("01012341234");
        Member member = Member.createMember(dto);
        Member member2 = memberService.join(member);

        BoardDto bDto = new BoardDto();
        bDto.setBoardTitle("게시글 제목");
        bDto.setBoardContent("게시글 내용");
        bDto.setMemberId(dto.getId());
        bDto.setRegdate(LocalDateTime.now());
        Board board = Board.createBoard(bDto, member2);

        // when
        Board board2 = boardService.create(board);

        // then
        Assertions.assertEquals(board, boardRepository.findOne(board2.getId()));
    }

    @Test
    public void 게시글_수정() throws Exception {
        // given
        MemberDto dto = new MemberDto();
        dto.setUserName("kim");
        dto.setPw("1234");
        dto.setLoginId("kim1234");
        dto.setBirthdate(LocalDate.ofEpochDay(19850904));
        dto.setGender("남");
        dto.setEmail("kim1234@naver.com");
        dto.setTel("01012341234");
        Member member = Member.createMember(dto);
        Member member2 = memberService.join(member);

        BoardDto bDto = new BoardDto();
        bDto.setBoardTitle("게시글 제목");
        bDto.setBoardContent("게시글 내용");
        bDto.setMemberId(dto.getId());
        bDto.setRegdate(LocalDateTime.now());
        Board board = Board.createBoard(bDto, member2);
        Board board2 = boardService.create(board);

        // when
        BoardDto updateDto = new BoardDto();
        updateDto.setBoardId("게시글 제목 수정");
        updateDto.setBoardContent("게시글 내용 수정");
        updateDto.setUpdate(LocalDateTime.now());
        board2 = boardService.update(member2.getId(), updateDto);

        // then
        Assertions.assertEquals(board2, boardRepository.findOne(board2.getId()));
    }




}
