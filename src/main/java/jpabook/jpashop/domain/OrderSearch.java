package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String memberName; //회원명
    private OrderStatus orderStatus; //ORDER, CANCEL (주문상태)

}
