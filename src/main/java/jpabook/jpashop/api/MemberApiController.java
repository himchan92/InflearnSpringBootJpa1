package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //Controller + ResponseBody(데이터를 바로 JSON 형식 전송)
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

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
}
