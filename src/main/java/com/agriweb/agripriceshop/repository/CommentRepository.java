package com.agriweb.agripriceshop.repository;

import com.agriweb.agripriceshop.domain.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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
    public List<Comment> findByBoardId(Long boardId) {
        String query = "select c from Comment c inner join c.board b "
                + "where b.id = :boardId";

        return em.createQuery(query, Comment.class)
                .setParameter("boardId", boardId)
                .getResultList();

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
