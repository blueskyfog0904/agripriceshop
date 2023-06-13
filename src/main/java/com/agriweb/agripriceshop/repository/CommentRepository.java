package com.agriweb.agripriceshop.repository;

import com.agriweb.agripriceshop.domain.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    // 댓글 작성
    public void save(Comment c) {
        em.persist(c);
    }

    // 게시글의 댓글 조회
    public Page<Comment> findByBoardId(Long boardId, Pageable pageable) {
        String sql = "select c from Comment c inner join c.board b "
                + "where b.id = :boardId order by c.id desc";
        TypedQuery<Comment> query = em.createQuery(sql, Comment.class);
        query.setParameter("boardId", boardId);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Comment> comments = query.getResultList();
        long total = getTotalCount(boardId);

        return new PageImpl<>(comments, pageable, total);


    }
    private long getTotalCount(Long boardId) {
        String sql = "select count(c) from Comment c inner join c.board b "
                + "where b.id = :boardId";
        TypedQuery<Long> countQuery = em.createQuery(sql, Long.class);
        countQuery.setParameter("boardId", boardId);
        return countQuery.getSingleResult();
    }



    // 댓글 수정시 댓글 조회
    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }


    // 댓글 삭제
    public void deleteOne(Comment c) {
        em.remove(c);
    }





}
