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

import java.util.ArrayList;
import java.util.List;

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

        // DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);

    }

    // 게시글 댓글 가져오기
    public List<CommentDto> comments(Long boardId) {
        // 댓글 목록 조회
        List<Comment> comments = commentRepository.findByBoardId(boardId);

        // 엔티티 -> DTO로 변환
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for(int i =0; i < comments.size(); i++) {
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);
        }
        return dtos;
    }

    // 댓글 수정
    public CommentDto update(Long commentId, CommentDto dto) {
        // 댓글 조회
        Comment target = commentRepository.findOne(commentId);

        // 댓글 수정
        target.patch(dto);

        // 댓글 엔티티를 DB에 저장
        commentRepository.save(target);
        Comment updated = commentRepository.findOne(target.getId());

        // Dto로 변환하여 변환
        return CommentDto.createCommentDto(updated);

    }

    // 댓글 삭제
    public CommentDto delete(Long commentId) {
        // 댓글 조회
        Comment target = commentRepository.findOne(commentId);

        // 댓글 DB에서 삭제
        commentRepository.deleteOne(target);

        // 삭제 댓글을 Dto로 반환
        return CommentDto.createCommentDto(target);
    }



}
