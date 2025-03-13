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

    @NotEmpty //값 필수 체크
    private String name;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member") //order 테이블의 member 매핑
    private List<Order> orders = new ArrayList<>();
}
