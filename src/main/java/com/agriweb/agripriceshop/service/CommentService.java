package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Board;
import com.agriweb.agripriceshop.domain.Comment;
import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.CommentDto;
import com.agriweb.agripriceshop.repository.BoardRepository;
import com.agriweb.agripriceshop.repository.CommentRepository;
import com.agriweb.agripriceshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 댓글 생성
    public CommentDto create(Long boardId, String loginId, CommentDto dto) {

        // 게시글 조회 및 예외 발생
        Board board = boardRepository.findOne(boardId);
        // 멤버 조회
        Member member = memberRepository.findOneByLoginId(loginId);

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, board, member);

        // 댓글 엔티티를 DB로 저장
        commentRepository.save(comment);
        Comment created = commentRepository.findOne(comment.getId());

        // DTO로 변경한여 반환
        return CommentDto.createCommentDto(created);

    }



}
