package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Board;
import com.agriweb.agripriceshop.dto.BoardDto;
import com.agriweb.agripriceshop.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 작성
    @Transactional
    public Board create(Board board) {
        boardRepository.save(board);
        Board created = boardRepository.findOne(board.getId());
        return created;
    }

    // 게시글 전체 조회
    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    // loginId로 게시글 조회
    public List<Board> findByLoginId(String loginId){
        return boardRepository.findByloginId(loginId);

    }

    // 게시판Id로 게시글 조회
    public Board findOne(Long boardId) {
        return boardRepository.findOne(boardId);
    }

    // 게시글 정보 수정
    @Transactional
    public Board update(Long id, BoardDto dto) {

        // 1. 대상 엔티티 찾기
        Board target = boardRepository.findOne(id);

        // 2. 잘못된 요청 처리
        if (target == null || target.getId() != id) {
            return null;
        }

        // 3. 업데이트
        if (dto.getBoardTitle() != null) {
            target.setBoardTitle(dto.getBoardTitle());
        }
        if (dto.getBoardContent() != null){
            target.setBoardContent(dto.getBoardContent());
        }
        target.setUpdate(LocalDateTime.now());
        boardRepository.save(target);
        Board updated = boardRepository.findOne(target.getId());
        return updated;

    }

    // 게시글 삭제
    @Transactional
    public void delete(Long id) {
        Board target = boardRepository.findOne(id);

        // 삭제
        boardRepository.deleteOne(target);
    }


}
