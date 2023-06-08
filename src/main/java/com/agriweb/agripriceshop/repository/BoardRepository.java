package com.agriweb.agripriceshop.repository;

import com.agriweb.agripriceshop.domain.Board;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    // 게시판 글 저장시키기
    public void save(Board board) {
        em.persist(board);
    }

    // 게시판 글 전부 가져오기
    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    // 게시판 ID로 검색시
    public List<Board> findByloginId(String loginId) {
        String query = "select b from Board b inner join b.member m "
        + "where m.loginId = :loginId";

        return em.createQuery(query, Board.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }

    // 게시글 수정시 게시글 조회
    public Board findOne(Long id) {
        return em.find(Board.class, id);
    }

    // 게시글 삭제
    public void deleteOne(Board board) {
        em.remove(board);
    }



}
