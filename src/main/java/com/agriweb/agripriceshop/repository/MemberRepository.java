package com.agriweb.agripriceshop.repository;

import com.agriweb.agripriceshop.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }
    public void deleteOne(Member member) {
        em.remove(member);
    }

    // 회원 전체 리스트 가져오기
    public Page<Member> findAll(Pageable pageable) {
        TypedQuery<Member> query = em.createQuery("select m from Member m order by m.id desc", Member.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Member> members = query.getResultList();
        long total = getTotalCount();

        return new PageImpl<>(members, pageable, total);

    };

    private long getTotalCount() {
        TypedQuery<Long> countQuery = em.createQuery("select count(m) from Member m", Long.class);
        return countQuery.getSingleResult();
    }

    // 회원가입시 중복ID 확인
    public List<Member> findByLoginId(String loginId) {
        return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }


    // 로그인시 해당되는 Member class 값 가져오기
    public Member findOneByLoginId(String loginId) {
//        try {
//            return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
//                    .setParameter("loginId", loginId)
//                    .getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
         Member result = em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getSingleResult();
        System.out.println(result);
        return result;
    }

}
