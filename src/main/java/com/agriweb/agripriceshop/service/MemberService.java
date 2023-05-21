package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.MemberDto;
import com.agriweb.agripriceshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */

    @Transactional
    public Member join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member;
    }

    // 회원 정보 수정
    @Transactional
    public Member update(Long id, MemberDto dto) {
        // 1. 수정용 엔티티 생성
        Member member = dto.toEntity();

        // 2. 대상 엔티티 찾기
        Member target = memberRepository.findOne(id);

        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우
        if (target == null || id != member.getId()) {
            log.info("잘못된 요청! id: {}, member: {}", id, member.toString());
            return null;
        }

        // 4. 업데이트
        target.

    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByLoginId(member.getLoginId());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 1명의 정보 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }





}
