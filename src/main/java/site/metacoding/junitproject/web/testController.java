package site.metacoding.junitproject.web;

import org.springframework.stereotype.Controller;

@Controller
public class testController {


    public void 생성에따른비교() {
        String str1 = "hello";
        String str2 = new String("hello");
        System.out.println("str1 : " + str1);
        System.out.println("str2 : " + str2);
    }
}
