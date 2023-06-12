package com.agriweb.agripriceshop.repository;

import com.agriweb.agripriceshop.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
//    public List<Board> findAll() {
//        return em.createQuery("select b from Board b", Board.class).getResultList();
//    }

    // 게시판 글 전부 가져오기(페이징 처리)
//    public Page<Board> findAll(Pageable pageable) {
//
//        return em.createQuery("select b from Board b order by b.id desc", Board.class)
//                .setFirstResult((int) pageable.getOffset())
//                .setMaxResults(pageable.getPageSize())
//                .getResultList();
//    }

    public Page<Board> findAll(Pageable pageable) {

        TypedQuery<Board> query = em.createQuery("select b from Board b order by b.id desc", Board.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Board> boards = query.getResultList();
        long total = getTotalCount();

        return new PageImpl<>(boards, pageable, total);
    }
    private long getTotalCount() {
        TypedQuery<Long> countQuery = em.createQuery("select count(b) from Board b", Long.class);
        return countQuery.getSingleResult();
    }

    // 게시판 ID로 검색시(페이징)
    public Page<Board> findByloginId(String loginId, Pageable pageable) {
        TypedQuery<Board> query = em.createQuery("select b from Board b inner join b.member m where m.loginId = :loginId order by b.id desc", Board.class);
        query.setParameter("loginId", loginId);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Board> boards = query.getResultList();
        long total = getTotalCountByLoginId(loginId);

        return new PageImpl<>(boards, pageable, total);

    }
    private long getTotalCountByLoginId(String loginId) {
        TypedQuery<Long> countQuery = em.createQuery("select count(b) from Board b inner join b.member m where m.loginId = :loginId", Long.class);
        countQuery.setParameter("loginId", loginId);
        return countQuery.getSingleResult();
    }

//    // 게시판 ID로 검색시
//    public List<Board> findByloginId(String loginId) {
//        String query = "select b from Board b inner join b.member m "
//        + "where m.loginId = :loginId";
//
//        return em.createQuery(query, Board.class)
//                .setParameter("loginId", loginId)
//                .getResultList();
//    }

    // 게시글 수정시 게시글 조회
    public Board findOne(Long id) {
        return em.find(Board.class, id);
    }

    // 게시글 삭제
    public void deleteOne(Board board) {
        em.remove(board);
    }



}
