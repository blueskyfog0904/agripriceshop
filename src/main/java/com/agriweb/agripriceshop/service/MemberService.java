package com.agriweb.agripriceshop.service;

import com.agriweb.agripriceshop.domain.Member;
import com.agriweb.agripriceshop.dto.MemberDto;
import com.agriweb.agripriceshop.jwt.JwtTokenProvider;
import com.agriweb.agripriceshop.jwt.TokenInfo;
import com.agriweb.agripriceshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     */

    @Transactional
    public Member join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member;
    }

    @Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 1명의 정보 조회(id)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    // 회원 1명의 정보 조회(loginId)
    @Transactional
    public Member findOnebyLoginId(String loginId) {
        return memberRepository.findOneByLoginId(loginId);
    }


    // 회원 정보 수정
    @Transactional
    public Member update(Long id, MemberDto dto) {

        // 1. 대상 엔티티 찾기
        Member target = memberRepository.findOne(id);

        // 2. 잘못된 요청 처리
        if(target == null || target.getId() != id) {
            return null;
        }

        // 3. 업데이트
        if (dto.getPw() != null) {
            target.setPw(dto.getPw());
        }
        if (dto.getTel() != null) {
            target.setTel(dto.getTel());
        }
        if (dto.getAddr() != null) {
            target.setAddr(dto.getAddr());
        }
        if (dto.getEmail() != null) {
            target.setEmail(dto.getEmail());
        }
        memberRepository.save(target);
        return target;
    }

    // 회원 정보 삭제
    @Transactional
    public void delete(Long id) {
        Member target = memberRepository.findOne(id);

        // 삭제
        memberRepository.deleteOne(target);
    }



    // 회원 중복 검사 메서드
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByLoginId(member.getLoginId());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }






}
