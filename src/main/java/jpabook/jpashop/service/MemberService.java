package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) //데이터 변경 수행은 한 트랜젝션에서 이뤄지게 적용, 조회만있으면 readOnly=true 작성
@RequiredArgsConstructor //final 붙은 객체 대상 DI 지원
public class MemberService {

    // 하단 생성자 주입까지 적용된것
    private final MemberRepository memberRepository;

//    @Autowired //생성자 주입방식 : 생성된 이후로 변경못하므로 안전성 권장
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional //변경작업으로 readOnly=true 외에 별도 작성
    public Long join(Member member) {
        //중복회원체크
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name); // JPA 변경감지로 인한 UPDATE 수행 지원
    }
}
