package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    //URL 요청받기
    @GetMapping("hello")
    public String hello(Model model) {
        //model 통해 화면단으로 데이터 전달
        model.addAttribute("data", "hello333");

        //view 경로 html 파일 매핑
        return "hello";
    }
}