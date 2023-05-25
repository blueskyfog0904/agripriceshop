package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Board;
import com.agriweb.agripriceshop.domain.Comment;
import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.BoardDto;
import com.agriweb.agripriceshop.dto.CommentDto;
import com.agriweb.agripriceshop.dto.MemberDto;
import com.agriweb.agripriceshop.repository.CommentRepository;
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
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    BoardService boardService;

    @Autowired
    MemberService memberService;

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void 댓글_작성() throws Exception {
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

        CommentDto cDto = new CommentDto();
        cDto.setCmContent("댓글 내용입니다.");
        cDto.setBoardId(board2.getId());
        cDto.setMemberId(member2.getId());
        cDto.setRegdate(LocalDateTime.now());

        // when
        CommentDto create = commentService.create(cDto.getBoardId(), member2.getLoginId(), cDto);
        Comment find = commentRepository.findOne(create.getId());

        // then
        Assertions.assertEquals(create, CommentDto.createCommentDto(find));

    }

}
