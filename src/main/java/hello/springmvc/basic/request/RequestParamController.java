package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
   Author : 박강훈
   Date :   2023/06/05
   Content : 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술(HTTP 요청 - 기본, 헤더 조회)
*/

@Slf4j
@Controller
public class RequestParamController {

    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */

    @RequestMapping("/request-param-v1")
    //request와 response를 받아옵니다.
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //username 세팅
        String username = request.getParameter("username");
        //age 세팅, request.getParameter는 String으로 받아오기 때문에 int형으로 파싱해줍니다.
        int age = Integer.parseInt(request.getParameter("age"));
        // username과 age를 로그로 불러와 콘솔창에 출력해줍니다.
        log.info("username={}", username);
        log.info("age={}", age);

        //response를 작성해줍니다. return 값은 원래 void이지만 response를 작성해서 OK라는 문자가 뷰에 렌더링 됩니다.
        response.getWriter().write("OK");
    }
}
