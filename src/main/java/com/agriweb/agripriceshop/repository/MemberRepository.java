package com.agriweb.agripriceshop.repository;

import com.agriweb.agripriceshop.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    // 회원가입시 중복ID 확인
    public List<Member> findByLoginId(String loginId) {
        return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }


    // 로그인시 해당되는 Member class 값 가져오기
    public Member findOneByLoginId(String loginId) {
        return em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getSingleResult();
    }

}
