package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    //엔티티를 그대로 파라미터 전달하여 API 호출 시 변경하는경우 API 스펙에도 변경반영되어 장애발생될수 있어 사용 X
    // 해결 : API 스펙에 맞춰 별도 DTO를 만들어 전달
    @NotEmpty //값 필수 체크
    private String name;

    @Embedded
    private Address address;

    @JsonIgnore //해당 필드는 API JSON 결과목록에서 제외
    @OneToMany(mappedBy = "member") //order 테이블의 member 매핑
    private List<Order> orders = new ArrayList<>();
}
