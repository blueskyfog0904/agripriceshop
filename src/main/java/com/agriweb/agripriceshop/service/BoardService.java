package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Board;
import com.agriweb.agripriceshop.dto.BoardDto;
import com.agriweb.agripriceshop.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return board;
    }

    // 게시글 전체 조회
    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    // loginId로 게시글 조회
    public List<Board> findByLoginId(String loginId){
        return boardRepository.findByloginId(loginId);

    }


}
