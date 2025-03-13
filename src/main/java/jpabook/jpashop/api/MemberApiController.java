package jpabook.jpashop.api;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //Controller + ResponseBody(데이터를 바로 JSON 형식 전송)
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    //조회 API(개선 전)
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    //조회 API(개선 후)
    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();

        List<MemberDTO> collect = findMembers.stream()
            .map(m -> new MemberDTO(m.getName()))
            .collect(Collectors.toList());

        //DTO 설정한 필드만 JSON 응답결과에 반영 (API 스펙 변경방지)
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDTO {
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    // @Valid : @NotEmpty 붙은 필드 대상으로 수행
    // API 파라미터는 엔티티를 절대 셋팅하여 노출시키면 안된다
    // 파라미터는 별도 DTO를 만들어 넘겨주면 된다
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // 파리미터를 엔티티 아닌 별도 DTO 생성 하여 셋팅
    // 결론 : 요청/응답 DTO 별도생성하여 파라미터를 셋팅해라, 절대로 엔티티로 넘기면 안된다!!
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName()); // DTO를 셋팅함으로써
        // 엔티티를 변경해도 영향없는 장점

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor //모든필드 생성자
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    //수정 API
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id); //변경 작업 후 id 기준 조회
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }
}
